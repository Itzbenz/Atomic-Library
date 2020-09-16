package Atom.Runtime;

import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;
import javassist.compiler.CompileError;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

public class RuntimeSources {
    private final ArrayList<String> imports = new ArrayList<>();
    public String packages, name, code;
    public RuntimeClass compiled = null;


    public RuntimeSources(File f) {
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

    public RuntimeSources(String source) {
        AssignSourceCode(source);
    }

    public RuntimeSources(String packages, String name) {
        this(packages, name, ("\n  public " + name + "(){\n\n  }\n"));
    }

    public RuntimeSources() {
        this("Test", "Tests", ("\n  public " + "Tests" + "(){\n\n  }\n"));
    }

    public RuntimeSources(String packages, String name, String code) {
        this.packages = packages;
        this.name = name;
        this.code = code;
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
        Compiler compiler = new Compiler();
        compiler.addSource(this.toString(), packages, name);
        if (!compiler.compile(err)) throw new CompileError("Failed to compile");
        return compiled = new RuntimeClass(compiler.getCompiledClass(name, packages));
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