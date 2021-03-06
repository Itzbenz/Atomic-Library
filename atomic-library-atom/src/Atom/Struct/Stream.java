package Atom.Struct;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

public class Stream {
    
    public static Thread readInputAsync(InputStream i, Consumer<String> handler, char delimiter) {
        return new Thread(() -> {
            try {
                readInputSync(i, handler, delimiter);
            }catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    
    public static OutputStream getReader(Consumer<String> handler) {
        return new OutputStream() {
            private final StringBuilder string = new StringBuilder();
            private StringBuilder instrumental = new StringBuilder();
            
            @Override
            public void write(int x) {
                if ((char) x == '\n') {
                    handler.accept(instrumental.toString());
                    instrumental = new StringBuilder();
                }else this.instrumental.append((char) x);
                this.string.append((char) x);
            }
            
            public String toString() {
                return this.string.toString();
            }
        };
    }
    
    public static void readInputSync(InputStream stream, Consumer<String> handler, char delimiter) throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        StringBuilder out = new StringBuilder();
        InputStreamReader in = new InputStreamReader(stream, StandardCharsets.UTF_8);
        
        int charsRead;
        while ((charsRead = in.read()) > 0) {
            if (charsRead == delimiter) {
                handler.accept(out.toString());
                out = new StringBuilder();
                continue;
            }
            out.append((char) charsRead);
        }
    }
    
    public static String readInputSync(InputStream stream) throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(stream, StandardCharsets.UTF_8);
        int charsRead;
        while ((charsRead = in.read(buffer, 0, buffer.length)) > 0) {
            out.append(buffer, 0, charsRead);
        }
        return out.toString();
    }
}
