package Atom;

import Atom.Classloader.SystemURLClassLoader;
import arc.util.Log;
import mindustry.mod.Mod;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLClassLoader;

public class Aindustry extends Mod {
    @Override
    public void init() {
        try {
            SystemURLClassLoader.loadJar((URLClassLoader) this.getClass().getClassLoader(), getConfig().parent().file());
            Log.infoTag("Atom", "Loaded");
        } catch (IOException | ClassNotFoundException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
            Log.err(e);
        }

    }

}
