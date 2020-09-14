package Atom;

import arc.util.Log;
import mindustry.mod.Mod;

public class Aindustry extends Mod {

    @Override
    public void init() {
        try {
            if(!Manifest.checkIntegrity()){
                Log.infoTag("Atom", "Nope");
                return;
            }
            Log.infoTag("Atom", "Loaded");
        } catch (Throwable e) {
            e.printStackTrace();
            Log.err(e);
        }
    }

}
