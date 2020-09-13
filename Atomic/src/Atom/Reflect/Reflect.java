package Atom.Reflect;

import Atom.Classloader.JarClassLoader;
import Atom.Classloader.SystemURLClassLoader;
import Atom.Random;
import Atom.Struct.Filter;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.*;

public class Reflect {

    public static <E> Set<Class<? extends E>> getExtendedClass(String packageName, Class<E> e) {
        Reflections reflections = new Reflections(packageName, SubTypesScanner.class);
        return reflections.getSubTypesOf(e);
    }

    public static File getCurrentJar(Class<?> clazz){
        try {
            return new File(clazz.getClass().getProtectionDomain().getCodeSource().getLocation()
                    .toURI());
        } catch (Throwable e) {
            return null;
        }
    }

    public static <E> E getField(Class<?> clazz, String name, Object object){
        ArrayList<Field> result = findDeclaredField(clazz, f -> f.getName().equals(name));
        E e = null;
        for (Field f : result){
            try {
                e = (E) f.get(object);
            } catch (Throwable ignored) { }
        }
        return e;
    }

    public static ArrayList<Field> findDeclaredField(Class<?> clazz, Filter<Field> filter){
        ArrayList<Field> result = new ArrayList<>();
        try{
            Field[] fields = clazz.getDeclaredFields();
            for (Field f : fields){
                if(filter.accept(f))
                    result.add(f);
            }
            for(Field f : result){
                f.setAccessible(true);
            }
        }catch (Throwable ignored){ }
        return result;
    }

    public static ArrayList<Method> findDeclaredMethods(Class<?> clazz, Filter<Method> filter){
        ArrayList<Method> result = new ArrayList<>();
        try{
          Method[] methods =  clazz.getDeclaredMethods();
          for(Method m : methods){
             if(filter.accept(m))
                 result.add(m);
          }
          for(Method m : result){
              m.setAccessible(true);
          }
        }catch (Throwable ignored){

        }
        return result;
    }

    public static Set<Class<?>> getAllExtendedOrImplementedTypesRecursively(Class<?> clazz) {
        List<Class<?>> res = new ArrayList<>();

        do {
            res.add(clazz);

            // First, add all the interfaces implemented by this class
            Class<?>[] interfaces = clazz.getInterfaces();
            if (interfaces.length > 0) {
                res.addAll(Arrays.asList(interfaces));

                for (Class<?> interfaze : interfaces) {
                    res.addAll(getAllExtendedOrImplementedTypesRecursively(interfaze));
                }
            }

            // Add the super class
            Class<?> superClass = clazz.getSuperclass();

            // Interfaces does not have java,lang.Object as superclass, they have null, so break the cycle and return
            if (superClass == null) {
                break;
            }

            // Now inspect the superclass
            clazz = superClass;
        } while (!"java.lang.Object".equals(clazz.getCanonicalName()));

        return new HashSet<>(res);
    }

    //work for color need improvement
    public static <E> E getRandomField(Class<E> e) {
        return (E) Random.getRandom(e.getDeclaredFields());
    }

    public static ArrayList<Package> findPackages(Filter<Package> filter) {
        ArrayList<Package> a = new ArrayList<>();
        try {
            if (!(SystemURLClassLoader.getURLSystemCl() instanceof JarClassLoader)) {
                for (Package p : Package.getPackages())
                    if (filter.accept(p)) a.add(p);

            } else {
                for (Package P : ((JarClassLoader) SystemURLClassLoader.getURLSystemCl()).getPackages())
                    if (filter.accept(P)) a.add(P);
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return a;
    }

}
