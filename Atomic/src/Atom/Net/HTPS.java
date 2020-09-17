package Atom.Net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

public class HTPS {
    public static String post(String url, String postData) throws IOException {
        PrintWriter out;
        BufferedReader in;
        StringBuilder result = new StringBuilder();

        URL realUrl = new URL(url);
        // build connection
        URLConnection conn = realUrl.openConnection();
        // set request properties
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
        // enable output and input
        conn.setDoOutput(true);
        conn.setDoInput(true);
        out = new PrintWriter(conn.getOutputStream());
        // send POST DATA
        out.print(postData);
        out.flush();
        in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            result.append("/n").append(line);
        }
        out.close();
        in.close();
        return result.toString();
    }

    public static String get(String url) throws IOException {
        URL urls = new URL(url);
        URLConnection yc = urls.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
        StringBuilder inputLine = new StringBuilder();
        while (in.ready()) inputLine.append(in.readLine());
        in.close();
        return inputLine.toString();
    }

    //Sync request
    public String getPublicIP() {
        String ip = "";
        try {
            URL amazon = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(amazon.openStream()));
            ip = in.readLine();
            return ip;
        } catch (Throwable ignored) {
        }

        try {
            URL ipApi = new URL("http://ip-api.com/line/");
            BufferedReader in = new BufferedReader(new InputStreamReader(ipApi.openStream()));
            while (in.ready())
                ip = in.readLine();
            return ip;
        } catch (Throwable ignored) {
        }
        return ip;
    }
}
