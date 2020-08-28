package Atom.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Consumer;


public class Log {
    protected static final Collection<Consumer<LogData>> outputListener;
    protected static boolean debug;
    protected static LogPrefix prefix;
    public static volatile boolean usePrefix = true;
    static {
        debug = false;
        //Thread safe collection
        outputListener = Collections.synchronizedCollection(new ArrayList<>());
        //Default listener
        outputListener.add(logData -> System.out.println(logData.log));
        //Default prefix
        prefix = new LogPrefix();
    }

    public synchronized static LogPrefix getPrefix() {
        return prefix;
    }

    public synchronized static void setPrefix(LogPrefix prefixs) {
        prefix = prefixs;
    }

    //Can disable Log too
    public synchronized static void clearOutputListener() {
        outputListener.clear();
    }

    //Reset all listener and only output to this listener
    public synchronized static void setOutputListener(Consumer<LogData> consumer) {
        clearOutputListener();
        addOutputListener(consumer);
    }

    //Add listener if doesn't exists
    public synchronized static void addOutputListener(Consumer<LogData> consumer) {
        if (!outputListener.contains(consumer))
            outputListener.add(consumer);
    }


    //Thread safe toggle
    public synchronized static void setDebug(boolean bool) {
        debug = bool;
    }

    //Directly to listener without additional process
    private static void output(LogData lg) {
        if (lg.level == 0 && !debug)
            return;
        for (Consumer<LogData> s : outputListener) {
            s.accept(lg);
        }

    }

    //Directly to listener with additional process
    private static void output(Object raw, int level) {
        if(usePrefix)
            output(new LogData(raw, prefix.cover(raw, level), level));
        else
            output(new LogData(raw, (String) raw, level));
    }

    public static void debug(Object s) {
        output(s, 0);
    }

    public static void info(Object s) {
        output(s, 1);
    }

    public static void warn(Object s) {
        output(s, 2);
    }

    public static void err(Object s) {
        output(s, 3);
    }

}
