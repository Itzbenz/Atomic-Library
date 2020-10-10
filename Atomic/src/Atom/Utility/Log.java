package Atom.Utility;

public abstract class Log {
    protected String[] sLevel = new String[]{"[Debug]", "[Info]", "[Warn]", "[Error]"};


    protected void output(Object raw, int level) {
        output(sLevel[level] + " " + raw);
    }

    protected void output(String tag, Object raw, int level) {
        output(sLevel[level] + " " + tag + ": " + raw);
    }

    protected void output(Object raw) {
        System.out.println(raw);
    }

    public void debug(Object s) {
        output(s, 0);
    }

    public void info(Object s) {
        output(s, 1);
    }

    public void warn(Object s) {
        output(s, 2);
    }

    public void err(Object s) {
        output(s, 3);
    }

    public void debug(String tag, Object s) {
        output(tag, s, 0);
    }

    public void info(String tag, Object s) {
        output(tag, s, 1);
    }

    public void warn(String tag, Object s) {
        output(tag, s, 2);
    }

    public void err(String tag, Object s) {
        output(tag, s, 3);
    }
}
