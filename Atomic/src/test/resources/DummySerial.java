import Atom.Reflect.FieldTool;
import Atom.Utility.Random;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.time.Instant;
import java.util.ArrayList;

public class DummySerial implements Serializable {
    public int ia, ib, ic, id;
    public String sa, sb, sc, sd;
    public boolean ba, bb, bc, bd;
    public long la, lb, lc, ld;
    public char ca, cb, cc, cd;
    public byte ta, tb, tc, td;
    public ArrayList<String> stringArrayList;
    public ArrayList<Long> longArrayList;
    public Instant instant;
    public transient int iTransient;

    public static DummySerial random() {
        DummySerial dummySerial = new DummySerial();
        FieldTool.assignRandom(dummySerial.getClass().getFields(), dummySerial);
        dummySerial.iTransient = Random.getInt();
        dummySerial.instant = Instant.now();
        ArrayList<String> s = new ArrayList<>();
        ArrayList<Long> l = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            s.add(Random.getString());
            l.add((long) Random.getInt());
        }
        dummySerial.stringArrayList = s;
        dummySerial.longArrayList = l;
        return dummySerial;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DummySerial))
            return super.equals(obj);
        DummySerial dum = (DummySerial) obj;
        if (dum.ia != ia) return false;
        if (!dum.sa.equals(sa)) return false;
        if (dum.la != la) return false;
        if (dum.ca != ca) return false;
        if (dum.ba != ba) return false;
        if (dum.ta != ta) return false;
        return dum.instant.equals(instant);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Field field : this.getClass().getDeclaredFields()) {
            sb.append(field.getName()).append(": ");
            try {
                switch (field.getType().getTypeName()) {
                    case ("java.lang.String"):
                        sb.append(field.get(this)).append("\n");
                        break;
                    case ("int"):
                        sb.append(field.getInt(this)).append("\n");
                        break;
                    case ("boolean"):
                        sb.append(field.getBoolean(this)).append("\n");
                        break;
                    case ("long"):
                        sb.append(field.getLong(this)).append("\n");
                        break;
                    case ("char"):
                        sb.append(field.getChar(this)).append("\n");
                        break;
                    case ("byte"):
                        sb.append(field.getByte(this)).append("\n");
                        break;
                    default:
                        sb.append(field.get(this).toString()).append("\n");
                }
            } catch (Throwable ig) {
                ig.printStackTrace();
            }
        }
        return sb.toString();
    }
}
