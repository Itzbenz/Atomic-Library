package Atom.Runtime;

import Atom.Manifest;
import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;
import javassist.compiler.CompileError;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

public class RuntimeSource {
    public static File workingDir = new File(Manifest.workingDir, "/AtomicCompilerCache/");
    private final ArrayList<String> imports = new ArrayList<>();
    public String packages, name, code;
    public File sourceFile, compiledClassFile;
    public RuntimeClass compiled = null;


    public RuntimeSource(File f) {
        String s = "";
        try {
            s = Files.readAllLines(f.toPath()).toString();
        } catch (IOException ignored) {
        }
        if (s.isEmpty()) {
            this.packages = "Test";
            this.name = "Tests";
            this.code = ("\n  public " + "Tests" + "(){\n\n  }\n");
        } else AssignSourceCode(s);
    }

    public RuntimeSource(String source) {
        AssignSourceCode(source);
    }

    public RuntimeSource(String packages, String name) {
        this(packages, name, ("\n  public " + name + "(){\n\n  }\n"));
    }

    public RuntimeSource() {
        this("Test", "Tests", ("\n  public " + "Tests" + "(){\n\n  }\n"));
    }

    public RuntimeSource(String packages, String name, String code) {
        this.packages = packages;
        this.name = name;
        this.code = code;
    }

    public static void runLine(String line, OutputStream err) throws IOException, InstantiationException, NoSuchMethodException, ClassNotFoundException, CompileError {
        line = "public test(){" + line + "}";
        RuntimeSource rc = new RuntimeSource("Atom.Test", "Test", line);
        rc.compile(err);

    }

    public void AssignSourceCode(String source) {
        ArrayList<String> total = new ArrayList<>(Arrays.asList(source.replace("\n", " ").split(" ")));
        //get Packages name
        packages = total.get(total.indexOf("package") + 1).replace(";", "");
        //get Import List
        while (total.contains("import")) {
            int index = total.indexOf("import");
            total.remove("import");
            imports.add(total.remove(index).replace(";", ""));
        }
        //get Class Name
        name = total.get(total.indexOf("class") + 1).replace("{", "");

        //Get all method
        ArrayList<Character> c = new ArrayList<>();
        for (char ch : source.toCharArray()) {
            c.add(ch);
        }
        StringBuilder sb = new StringBuilder();
        int codeStartIndex = c.indexOf('{') + 1;
        int codeEndIndex = c.size() - 1;
        for (int i = codeStartIndex; i < c.size(); i++)
            if (c.get(i) == '}')
                codeEndIndex = i;
        for (int i = codeStartIndex; i < codeEndIndex; i++)
            sb.append(c.get(i));
        code = sb.toString();
    }

    public RuntimeClass compile(OutputStream err) throws IOException, CompileError {
        sourceFile.delete();
        saveCode();
        // Compile source file.
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) throw new NullPointerException("No compiler exists, use JDK not JRE");
        if (compiler.run(null, null, err, sourceFile.getAbsolutePath()) != 0)
            throw new CompileError("Failed to compile: " + sourceFile.getAbsolutePath());
        compiledClassFile = new File(workingDir, this.packages.replace(".", "/") + "/" + this.name + ".class");
        return compiled = new RuntimeClass(sourceFile);
    }

    public void loadCode() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, MalformedURLException {
        if (compiledClassFile == null || !compiledClassFile.exists() || !compiledClassFile.getName().endsWith(".class")) {
            throw new RuntimeException("Not yet compiled nothing to load");
        }
        // Load and instantiate compiled class.
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{compiledClassFile.toURI().toURL()});
        Class<?> clazz = Class.forName(this.packages + "." + this.name, true, classLoader); // Init pre-class
        clazz.getDeclaredConstructor().newInstance(); // Init class
        compiledClassFile.delete();

    }

    public void save(File save) throws IOException {
        Files.write(save.toPath(), this.toString().getBytes(StandardCharsets.UTF_8));
    }

    private File saveCode() throws IOException {
        // Write code to file
        sourceFile = new File(workingDir, this.packages.replace(".", "/") + "/" + this.name + ".java");
        sourceFile.getParentFile().mkdirs();
        Files.write(sourceFile.toPath(), this.toString().getBytes(StandardCharsets.UTF_8));
        return sourceFile;
    }


    public String getPackages() {
        return "package " + packages + ";";
    }


    public String getString() throws FormatterException {
        StringBuilder sb = new StringBuilder();
        sb.append("package ").append(packages).append(";\n");
        for (String s : imports)
            sb.append("import ").append(s).append(";\n");
        sb.append("\n").append("public").append(" class ").append(name).append("{");
        sb.append(code);
        sb.append("}");

        return new Formatter().formatSource(sb.toString());
    }

}