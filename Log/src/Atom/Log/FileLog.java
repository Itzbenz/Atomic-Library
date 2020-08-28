package Atom.Log;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.function.Consumer;

public class FileLog {
    public File logF;

    public FileLog(File logs) throws IOException {
        logF = logs;
        if(logs.exists())
            Files.delete(logs.toPath());
        Files.write(logs.toPath(), "-------------------\n".getBytes());
    }

    public void log(String log){
        try {
            Files.write(logF.toPath(), log.getBytes(), StandardOpenOption.APPEND);
            Files.write(logF.toPath(), "\n".getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e) {

        }
    }

    public static Consumer<LogData> getLogger() throws IOException {
        FileLog f = new FileLog(new File("E:/Log.log"));
        return logData -> f.log(logData.log);
    }
}
