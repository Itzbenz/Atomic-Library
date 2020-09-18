package Atom.Reflect;

import Atom.Random;

import java.lang.reflect.Field;

public class FieldTool {


    public static void assignRandom(Field[] fields) {
        for (Field f : fields) {
            try {
                assignRandom(f);
            } catch (IllegalAccessException e) {
                //yeet
            }
        }
    }

    public static void assignRandom(Field field) throws IllegalAccessException {
        switch (field.getType().getTypeName()) {
            case ("java.lang.String"):
                field.set(null, Random.getString());
            case ("int"):
                field.set(null, Random.getInt());
            case ("boolean"):
                field.set(null, Random.getBool());
            case ("long"):
                field.set(null, Random.getInt());
            default:
        }
    }


}
