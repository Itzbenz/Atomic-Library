package Atom.Struct;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.function.Consumer;

public class Stream {

    public static Thread readInputAsync(InputStream i, Consumer<String> handler, char delimiter) {
        return new Thread(() -> {
            try {
                readInputSync(i, handler, delimiter);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void readInputSync(InputStream stream, Consumer<String> handler, char delimiter) throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        StringBuilder out = new StringBuilder();
        InputStreamReader in = new InputStreamReader(stream, Charset.forName("UTF-8"));
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
        Reader in = new InputStreamReader(stream, Charset.forName("UTF-8"));
        int charsRead;
        while ((charsRead = in.read(buffer, 0, buffer.length)) > 0) {
            out.append(buffer, 0, charsRead);
        }
        return out.toString();
    }
}
