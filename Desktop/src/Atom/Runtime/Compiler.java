package Atom.Runtime;

import Atom.Manifest;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class Compiler {
	
	public static final String template = "package Dummy.Test;\n" + "\n" + "public class Test{\n" + "    \n" + "    public Test(){\n" + "        \n" + "    }\n" + "    \n" + "    public void init(){\n" + "        \n" + "    }\n" + "}\n";
	public static CompilationUnit parsedTemplate = null;
	
	//literally every exception
	public static void runLine(String line, OutputStream out) throws Exception {
		if (line.isEmpty()) throw new IllegalArgumentException("Empty input");
		CompilationUnit c = getTestTemplate();
		Statement statement = StaticJavaParser.parseStatement(line);
		try {
			c.findFirst(ClassOrInterfaceDeclaration.class).get().findAll(BlockStmt.class).get(1).addStatement(statement);
		}catch (Throwable t) {
			throw new RuntimeException("Flawed template", t);
		}
		RuntimeClass rc = compile(out, Manifest.workingDir, c);
		rc.load();
		rc.invokeMethod("init");
	}
	
	public static CompilationUnit getTestTemplate() {
		if (parsedTemplate != null) return parsedTemplate;
		return parsedTemplate = StaticJavaParser.parse(template);
	}
	
	public static RuntimeClass compile(OutputStream out, File workingDir, CompilationUnit compilationUnit) throws IOException {
		String classpath = getClasspath(compilationUnit);
		File source = new File(workingDir, classpath.replace(".", "/") + ".java");
		File compiled = new File(workingDir, classpath.replace(".", "/") + ".class");
		source.getParentFile().mkdirs();
		source.delete();
		compiled.delete();
		Files.writeString(source.toPath(), compilationUnit.toString());
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		if (compiler.run(null, out, out, source.getAbsolutePath()) != 0) throw new RuntimeException("Compiler Error");
		if (!compiled.exists()) throw new FileNotFoundException(compiled.getAbsolutePath() + " not found");
		return new RuntimeClass(workingDir, classpath);
	}
	
	public static String getClasspath(CompilationUnit compilationUnit) {
		return ((compilationUnit.getPackageDeclaration().isPresent() ? compilationUnit.getPackageDeclaration().get().getName() + "." : "") + compilationUnit.findFirst(ClassOrInterfaceDeclaration.class).get().getNameAsString()).trim();
	}
}
