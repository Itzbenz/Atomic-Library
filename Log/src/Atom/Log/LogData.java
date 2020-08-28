package Atom.Log;

public class LogData {

    public final String raw, log;
    public final int level;

    public LogData(Object raw, String log, int level) {
        String raw1;
        try {
            raw1 = String.valueOf(raw);
        }catch (Throwable t){
            raw1 = t.toString();
        }
        this.raw = raw1;
        this.log = log;
        this.level = level;
    }

}
