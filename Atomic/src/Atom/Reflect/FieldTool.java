package Atom.Reflect;

import Atom.Utility.Random;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static Atom.Reflect.Reflect.findDeclaredField;

public class FieldTool {

    public static void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, newValue);
    }
    
    public static String getFieldDetails(Object o, boolean all){
        return getFieldDetails(o, o.getClass(), all, 150);
    }
    public static String getFieldDetails(Object o){
        return getFieldDetails(o, o.getClass());
    }
    
    public static String getFieldDetails(Object o,  Class<?> clazz){
        return getFieldDetails(o, clazz, true, 150);
    }
    public static String getFieldDetails(Object o, Class<?> clazz, boolean all, int maxDat){
        StringBuilder sb = new StringBuilder();
        HashMap<String, Field> fe = new HashMap<>();
        for(Field f : clazz.getDeclaredFields())
            fe.put(f.getName(), f);
        if(all)
        for(Field f : clazz.getFields())
            fe.put(f.getName(), f);
        for(Map.Entry<String, Field> fg : fe.entrySet()){
            Field f = fg.getValue();
            try {
                f.setAccessible(true);
            }catch (Throwable ignored){}
            try{
                String dat = f.get(o).toString();
                if (dat.length() > maxDat) dat = dat.substring(0, maxDat) + "....";
                sb.append(f.getName()).append("=").append(dat).append("\n");
            }catch (Throwable ignored){}
        }
        return sb.toString();
    }
    
    public static Field getField(Class<?> clazz, String name, Object object) {
        ArrayList<Field> result = findDeclaredField(clazz, f -> f.getName().equals(name), object);
        Field e = null;
        if (result == null) return null;
        for (Field f : result) {
            try {
                e = f;
            }catch (Throwable ignored) {
            }
        }
        return e;
    }

    public static void assignRandom(Field[] fields, Object o) {
        for (Field f : fields) {
            try {
                assignRandom(f, o);
            }catch (IllegalAccessException e) {
                //yeet
            }
        }
    }

    public static void assignRandom(Field field, Object o) throws IllegalAccessException {
        switch (field.getType().getTypeName()) {
            case ("java.lang.String"):
                field.set(o, Random.getString());
                break;
            case ("int"):
                field.setInt(o, Random.getInt());
                break;
            case ("boolean"):
                field.setBoolean(o, Random.getBool());
                break;
            case ("long"):
                field.setLong(o, Random.getInt());
                break;
            case ("char"):
                field.setChar(o, Random.getString().charAt(0));
                break;
            case ("byte"):
                field.setByte(o, (byte) Random.getInt());
            default:
        }
    }


}
