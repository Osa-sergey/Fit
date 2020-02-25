package Pojo;

import java.util.Objects;

public class SettingsUserItem {

    private String url;
    private String username;
    private boolean state;

    public SettingsUserItem(String url, String username, boolean state) {
        this.url = url;
        this.username = username;
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SettingsUserItem that = (SettingsUserItem) o;
        return state == that.state &&
                Objects.equals(url, that.url) &&
                username.equals(that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, username, state);
    }
}
