package Atom.Time;


import Atom.Struct.Queue;

import java.util.concurrent.TimeUnit;

public class Countdown {
    private static Queue<Long> lastCount;
    private static TimeUnit timeUnit;
    private static long resultCount;
    static {
        reset();
    }
    public static void start(){
        start(TimeUnit.MICROSECONDS);
    }
    public static void start(TimeUnit t){
        timeUnit = t;
        lastCount.addLast(System.currentTimeMillis());
    }
    public static long stop(){
        if(lastCount.isEmpty())
            return 0L;
        long temp = System.currentTimeMillis();
        temp = timeUnit.convert(temp, TimeUnit.MICROSECONDS);
        return resultCount = temp - lastCount.removeLast() ;
    }
    public static String result(){
        return get() + " Millisecond";
    }
    public static long get(){
        return resultCount;
    }
    public static void reset(){
        lastCount = new Queue<>();
        timeUnit = TimeUnit.SECONDS;
        resultCount = 0L;
    }
}
