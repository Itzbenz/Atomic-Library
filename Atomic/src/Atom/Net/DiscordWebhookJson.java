package Atom.Net;

public class DiscordWebhookJson {
    private final String url;
    public String username;
    public String avatar_url;
    public String content;

    public DiscordWebhookJson(String url) {
        this.url = url;
    }

    public DiscordWebhookJson(String username, String content, String url) {
        this.username = username;
        this.content = content;
        this.url = url;
    }

    public DiscordWebhookJson(String content, String url) {
        this.content = content;
        this.url = url;
    }

    public DiscordWebhookJson(String username, String avatar_url, String content, String url) {
        this.username = username;
        this.avatar_url = avatar_url;
        this.content = content;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
