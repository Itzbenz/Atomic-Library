package Atom.Reflect;

import Atom.Utility.Utility;
import org.reflections.Reflections;
import org.reflections.scanners.Scanner;
import org.reflections.scanners.SubTypesScanner;

import java.lang.reflect.Field;
import java.util.Set;

public class Reflect {

    public static <E> Set<Class<? extends E>> getExtendedClass(String packageName, Class<E> e) {
        Reflections reflections = new Reflections(packageName);
        return reflections.getSubTypesOf(e);
    }


}
