package Atom.Reflect;

import Atom.Random;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import static Atom.Reflect.Reflect.findDeclaredField;

public class FieldTool {

    public static void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, newValue);
    }

    public static Field getField(Class<?> clazz, String name, Object object) {
        ArrayList<Field> result = findDeclaredField(clazz, f -> f.getName().equals(name), object);
        Field e = null;
        if (result == null) return null;
        for (Field f : result) {
            try {
                e = f;
            } catch (Throwable ignored) {
            }
        }
        return e;
    }

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
