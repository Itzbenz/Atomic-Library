import Atom.File.SerializeData;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class IO {
    public static File data = new File("test.ser");
    public static File arrData = new File("test.arr");
    public static DummySerial d = DummySerial.random();
    public static ArrayList<DummySerial> dummyTogetherDummy = new ArrayList<>();

    public static void main(String[] args) throws Throwable {
        IO io = new IO();
        io.dummyVerify();
        io.serialize();
        io.deserialize();
        io.arraySerialize();
        io.arrayDeserialize();
    }

    public void dummyVerify() {
        data.delete();
        arrData.delete();
        System.out.println(data.getAbsolutePath());
        System.out.println(arrData.getAbsolutePath());
        System.out.println(d.toString());
        System.out.println("Dummy Verify");
    }


    public void serialize() throws IOException {
        SerializeData.dataOut(d, data);
        assert data.exists();
    }


    public void deserialize() throws IOException, ClassNotFoundException {
        DummySerial dumb = SerializeData.dataIn(data);
        System.out.println(dumb.toString());
        System.out.println("Dummy Deserialize");
        assert dumb.equals(d);
    }


    public void arraySerialize() throws IOException {
        for (int i = 0; i < 100; i++) {
            dummyTogetherDummy.add(DummySerial.random());
        }
        System.out.println(dummyTogetherDummy.get(0).toString());
        System.out.println("Dummy Sample Verify");
        SerializeData.dataOut(dummyTogetherDummy, arrData);
        assert arrData.exists();
    }


    public void arrayDeserialize() throws IOException, ClassNotFoundException {
        ArrayList<DummySerial> dumb = SerializeData.dataArrayIn(arrData);
        System.out.println(dumb.get(0).toString());
        System.out.println("Dummy Sample Deserialize");
        for (int i = 0; i < 100; i++) {
            assert dumb.get(i).equals(dummyTogetherDummy.get(i));
        }
    }
}
