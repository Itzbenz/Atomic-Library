package Atom;

import arc.util.Log;
import mindustry.mod.Mod;

public class Aindustry extends Mod {

    @Override
    public void init() {
        try {
            Log.infoTag("Atom", "Signature: " + Manifest.signature());
            if(!Manifest.checkIntegrity())
                Log.infoTag("Atom", "Nope");
            else
                Log.infoTag("Atom", "Loaded");
        } catch (Throwable e) {
            e.printStackTrace();
            Log.err(e);
        }
    }

}
