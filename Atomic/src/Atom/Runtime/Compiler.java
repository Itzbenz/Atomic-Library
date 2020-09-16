package Atom.Runtime;

import Atom.Manifest;
import javassist.compiler.CompileError;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Compiler {
    public File workingDir, sourceDir, outputDir;
    protected JavaCompiler javac;
    private HashMap<String, File> sourceFiles = new HashMap<>();
    private HashMap<String, File> compiledFiles = new HashMap<>();

    public Compiler() {
        workingDir = new File(Manifest.workingDir, "Compiler");
        workingDir.mkdirs();
        sourceDir = new File(workingDir, "source");
        outputDir.mkdirs();
        outputDir = new File(workingDir, "outputDir");
        outputDir.mkdirs();
        javac = ToolProvider.getSystemJavaCompiler();
        if (javac == null) throw new NullPointerException("System doesn't have JDK");
    }

    public Compiler(File workingDir) {
        this.workingDir = workingDir;
        sourceDir = new File(workingDir, "source");
        outputDir = new File(workingDir, "outputDir");
        javac = ToolProvider.getSystemJavaCompiler();
        if (javac == null) throw new NullPointerException("System doesn't have JDK");
    }

    public static void runLine(String line, OutputStream err) throws IOException, InstantiationException, NoSuchMethodException, ClassNotFoundException, CompileError, InvocationTargetException, IllegalAccessException {
        line = "public test(){" + line + "}";
        RuntimeSource rs = new RuntimeSource("Atom.Test", "Test", line);
        RuntimeClass rc = rs.compile(err);
        rc.load(rs.packages.replace(";", "") + "." + rs.name);
        rc.invokeMethod("test");
    }

    public File getCompiledClass(String name, String packages) {
        return compiledFiles.get(packages.replace(";", "") + "." + name);
    }

    public void addSource(String s, String packages, String name) throws IOException {
        File sourceFile = new File(sourceDir, packages.replace(";", "").replace(".", "/") + "/" + name + ".java");
        sourceFile.getParentFile().mkdirs();
        Files.write(sourceFile.toPath(), s.getBytes(StandardCharsets.UTF_8));
        sourceFiles.put(packages.replace(";", "") + "." + name, sourceFile);
        compiledFiles.put(packages.replace(";", "") + "." + name, new File(outputDir, packages.replace(";", "").replace(".", "/") + "/" + name + ".class"));

    }

    public boolean removeSource(String packages, String name) {
        File sourceFile = new File(workingDir, packages.replace(";", "").replace(".", "/") + "/" + name + ".java");
        return sourceFile.delete();
    }

    public boolean compile() {
        return compile("");
    }

    public boolean compile(String... arg) {
        return compile(System.out, arg);
    }

    public boolean compile(OutputStream err, String... arg) {
        return compile(System.out, err, arg);
    }

    public boolean compile(OutputStream out, OutputStream err, String... arg) {
        return compile(System.in, out, err, arg);
    }

    public boolean compile(InputStream in, OutputStream out, OutputStream err, String... arg) {
        ArrayList<String> args = new ArrayList<>();
        args.add("-d");
        args.add(outputDir.getAbsolutePath());
        args.addAll(Arrays.asList(arg));
        args.add(sourceDir.getAbsolutePath());
        arg = new String[args.size()];
        arg = args.toArray(arg);
        return javac.run(in, out, err, arg) == 0;
    }

}
