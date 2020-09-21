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
import java.util.*;

public class Reflect {

    public static <E> Set<Class<? extends E>> getExtendedClass(String packageName, Class<E> e) {
        Reflections reflections = new Reflections(packageName, SubTypesScanner.class);
        return reflections.getSubTypesOf(e);
    }

    public static File getCurrentJar(Class<?> clazz) {
        //fool proof
        return new File(clazz.getProtectionDomain().getCodeSource().getLocation().getPath());
    }

    public static <E> E getField(Class<?> clazz, String name, Object object) {
        ArrayList<Field> result = findDeclaredField(clazz, f -> f.getName().equals(name));
        E e = null;
        for (Field f : result) {
            try {
                e = (E) f.get(object);
            } catch (Throwable ignored) {
            }
        }
        return e;
    }

    public static Method getMethod(Class<?> clazz, String name, Object object) {
        ArrayList<Method> f = findMethod(clazz, s -> s.getName().equals(name), object);
        if (f == null || f.isEmpty())
            return null;
        else
            return f.get(0);
    }

    public static ArrayList<Method> findMethod(Class<?> clazz, Filter<Method> filter, Object object) {
        ArrayList<Method> result = new ArrayList<>();
        try {
            Method[] methods = object.getClass().getDeclaredMethods();
            for (Method m : methods) {
                if (filter.accept(m))
                    result.add(m);
            }
            for (Method m : methods) {
                try {
                    m.setAccessible(true);
                } catch (Throwable ignored) {
                }
            }
            return result;
        } catch (Throwable ignored) {
        }
        try {
            Method[] methods = clazz.getDeclaredMethods();
            for (Method m : methods) {
                if (filter.accept(m))
                    result.add(m);
            }
            for (Method m : methods) {
                try {
                    m.setAccessible(true);
                } catch (Throwable ignored) {
                }
            }

        } catch (Throwable ignored) {
        }
        if (result.isEmpty())
            return null;
        else
            return result;
    }

    public static ArrayList<Field> findDeclaredField(Class<?> clazz, Filter<Field> filter) {
        return findDeclaredField(clazz, filter, null);
    }

    public static ArrayList<Field> findDeclaredField(Class<?> clazz, Filter<Field> filter, Object object) {
        ArrayList<Field> result = new ArrayList<>();
        try {
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field f : fields) {
                if (filter.accept(f))
                    result.add(f);
            }
            for (Field f : result) {
                try {
                    f.setAccessible(true);
                } catch (Throwable ignored) {
                }
            }
            return result;
        } catch (Throwable ignored) {
        }
        try {
            Field[] fields = clazz.getDeclaredFields();
            for (Field f : fields) {
                if (filter.accept(f))
                    result.add(f);
            }
            for (Field f : result) {
                try {
                    f.setAccessible(true);
                } catch (Throwable ignored) {
                }
            }
        } catch (Throwable ignored) {
        }
        if (result.isEmpty())
            return null;
        else
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
