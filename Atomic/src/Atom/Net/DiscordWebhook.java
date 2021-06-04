package Atom.Net;

import Atom.IO.TimedFlushStream;
import Atom.Utility.Pool;
import Atom.Utility.Utility;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class DiscordWebhook {
    public URL url;
    public static Gson gson = new GsonBuilder().create();
    public Content content = new Content();
    
    public DiscordWebhook(URL url) {
        this.url = url;
    }
    
    public DiscordWebhook(URL url, Content content) {
        this.url = url;
        this.content = content;
    }
    
    public void post(String dat) {
        content.content = dat;
        Pool.submit((Runnable) this::post);
    }
    
    public void post() {
        try {
            // build connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // set request properties
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            // enable output and input
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            String doAnother = "";
            if (content.content.length() > 1920){
                doAnother = content.content.substring(1900);
                content.content = content.content.substring(0, 1900);
            }
            conn.getOutputStream().write(gson.toJson(content).getBytes(StandardCharsets.UTF_8));
            conn.getOutputStream().flush();
            conn.getOutputStream().close();
            conn.getInputStream().close();
            conn.disconnect();
            if (!doAnother.isEmpty()){
                content.content = doAnother;
                post();
            }
        }catch(Exception e){
            e.printStackTrace();
            System.err.println(gson.toJson(content));
        }
    }
    
 
    
    public static class Content{
        public String content = "This", username = "None", avatar_url;
    }
    public PrintStream hookToSystem(boolean stdOut) {
        PrintStream out = System.out;
        System.setOut(new PrintStream(new TimedFlushStream(4000,s->{
            if(stdOut)
                out.println(s);
            post(s);
        }), true));
        System.out.println(Utility.getDate());
        return out;
    }
}
