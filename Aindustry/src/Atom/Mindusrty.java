package Atom;

import Atom.Utility.Utility;
import arc.util.Log;
import mindustry.mod.Mod;

public class Mindusrty extends Mod {
    @Override
    public void init() {
        Random.getBool();
        Utility.getJavaMajorVersion();
        Log.infoTag("Atom", "Loaded");
        super.init();
    }

    @Override
    public void loadContent() {
        super.loadContent();
    }
}
