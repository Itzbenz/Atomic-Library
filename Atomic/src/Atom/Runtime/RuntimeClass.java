package Atom.Runtime;

import Atom.Reflect.Reflect;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class RuntimeClass {
    private File clas;
    private Object claszObject;
    private Class<?> loadC;
    private URLClassLoader classLoader;

    public RuntimeClass(File clazz) throws IOException {
        clas = clazz;
        if (!clazz.getName().endsWith(".jar"))
            throw new IOException("i doubt this is class file: " + clazz.getAbsolutePath());
        classLoader = new URLClassLoader(new URL[]{clazz.toURI().toURL()});
    }

    public void load(String clazzName) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        loadC = classLoader.loadClass(clazzName);
        claszObject = loadC.getDeclaredConstructor().newInstance();
    }

    public void invokeMethod(String name) throws InvocationTargetException, IllegalAccessException {
        getMethod(name).invoke(claszObject);
    }

    public Method getMethod(String name) {
        return Reflect.getMethod(loadC, name, claszObject);
    }

}
