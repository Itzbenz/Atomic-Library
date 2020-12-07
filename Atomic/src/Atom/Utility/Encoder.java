package Atom.Utility;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Encoder {
    private static final int MAX_SKIP_BUFFER_SIZE = 2048;
    private static final int MAX_BUFFER_SIZE = Integer.MAX_VALUE - 8;
    private static final int DEFAULT_BUFFER_SIZE = 8192;

    public static String base64Encode(byte[] bytes) {
        return getString(Base64.getEncoder().encode(bytes));
    }

    public static byte[] base64Decode(String s) {
        return Base64.getDecoder().decode(s);
    }

    public static String getString(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

    //copied from java
    public static byte[] readAllBytes(InputStream is, int len) throws IOException {
        if (len < 0) {
            throw new IllegalArgumentException("len < 0");
        }

        List<byte[]> bufs = null;
        byte[] result = null;
        int total = 0;
        int remaining = len;
        int n;
        do {
            byte[] buf = new byte[Math.min(remaining, DEFAULT_BUFFER_SIZE)];
            int nread = 0;

            // read to EOF which may read more or less than buffer size
            while ((n = is.read(buf, nread,
                    Math.min(buf.length - nread, remaining))) > 0) {
                nread += n;
                remaining -= n;
            }

            if (nread > 0) {
                if (MAX_BUFFER_SIZE - total < nread) {
                    throw new OutOfMemoryError("Required array size too large");
                }
                total += nread;
                if (result == null) {
                    result = buf;
                }else {
                    if (bufs == null) {
                        bufs = new ArrayList<>();
                        bufs.add(result);
                    }
                    bufs.add(buf);
                }
            }
            // if the last call to read returned -1 or the number of bytes
            // requested have been read then break
        }while (n >= 0 && remaining > 0);

        if (bufs == null) {
            if (result == null) {
                return new byte[0];
            }
            return result.length == total ?
                    result : Arrays.copyOf(result, total);
        }

        result = new byte[total];
        int offset = 0;
        remaining = total;
        for (byte[] b : bufs) {
            int count = Math.min(b.length, remaining);
            System.arraycopy(b, 0, result, offset, count);
            offset += count;
            remaining -= count;
        }

        return result;
    }

    public static byte[] readAllBytes(InputStream is) throws IOException {
        return readAllBytes(is, Integer.MAX_VALUE);
    }

    public static String property(Map<String, String> se) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> s : se.entrySet())
            sb.append(s.getKey()).append("=").append(s.getValue()).append("\n");
        return sb.toString();
    }

    public static HashMap<String, String> parseProperty(InputStream is) throws IOException {
        String se = new String(readAllBytes(is));
        HashMap<String, String> te = new HashMap<>();
        for (String s : se.split("\n")) {
            if (s.endsWith("\r"))
                s = s.substring(0, s.length() - 1);
            if (!s.startsWith("#"))
                te.put(s.split("=")[0], s.split("=")[1]);
        }
        return te;
    }
}
