package Atom;


import Atom.Reflect.Reflect;

public class Manifest {

    public static boolean checkIntegrity(){
        return Reflect.getExtendedClass("Atom", java.lang.Object.class).size() > 1;
    }
}
