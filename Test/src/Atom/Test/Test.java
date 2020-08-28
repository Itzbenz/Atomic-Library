package Atom.Test;

import Atom.Annotation.Builder.ClassBuilder;
import Atom.Annotation.Builder.MethodBuilder;
import Atom.Annotation.Builder.VariableBuilder;
import Atom.Log.Log;
import org.checkerframework.checker.units.qual.C;

public class Test {
    MethodBuilder ma = new MethodBuilder("public static void", "name", "", "System.out.println(\"geh\");");
    MethodBuilder mb = new MethodBuilder(" public static String ", "  a", " String name ", "" +
            "System.out.println(\"geh\");" +
            "System.out.println(\"geh\");" +
            "System.out.println(\"geh\");");
    VariableBuilder va = new VariableBuilder(" public ", "String ", "str ", "");
    VariableBuilder vb = new VariableBuilder(" ", " String", "strs ", "Nig");
    @org.junit.jupiter.api.Test
    void name() {
    }

    @org.junit.jupiter.api.Test
    public void VariableBuilderTest() {

        Log.info(va.toString());
        Log.info(vb.toString());
    }
    @org.junit.jupiter.api.Test
    public void MethodBuilderTest(){

        Log.info(ma.toString());
        Log.info(mb.toString());
    }
    @org.junit.jupiter.api.Test
    public void ClassBuilderTest() {
        ClassBuilder cb = new ClassBuilder("public class", "yes");
        cb.methodList.add(ma);
        cb.methodList.add(mb);
        cb.variableList.add(va);
        cb.variableList.add(vb);
        cb.addData("static{}");
        Log.info(cb.toString());
    }
}
