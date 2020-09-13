package Atom;

import Atom.Classloader.SystemURLClassLoader;
import Atom.Reflect.Reflect;
import Atom.Struct.Filter;
import arc.util.Log;
import mindustry.mod.Mod;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLClassLoader;

public class Aindustry extends Mod {
    @Override
    public void init() {
        try {
            Reflect.findPackages(new Filter<Package>() {
                @Override
                public boolean accept(Package aPackage) {
                    return false;
                }
            });
            SystemURLClassLoader.loadJar((URLClassLoader) this.getClass().getClassLoader(), Reflect.getCurrentJar(this.getClass()));
            Log.infoTag("Atom", "Loaded");
        } catch (Throwable e) {
            e.printStackTrace();
            Log.err(e);
        }

    }

}
