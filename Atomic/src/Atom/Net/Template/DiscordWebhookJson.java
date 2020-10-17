package Atom.Net.Template;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }
}
