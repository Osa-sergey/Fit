package Pojo;

import java.util.Objects;

public class ChatItem {
    private String url;
    private String fullName;
    private boolean selected;
    private String lastMsg;
    private long date;

    public ChatItem(String url, String fullName, boolean selected, String lastMsg, long date) {
        this.url = url;
        this.fullName = fullName;
        this.selected = selected;
        this.lastMsg = lastMsg;
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatItem chatItem = (ChatItem) o;
        return selected == chatItem.selected &&
                date == chatItem.date &&
                Objects.equals(url, chatItem.url) &&
                fullName.equals(chatItem.fullName) &&
                Objects.equals(lastMsg, chatItem.lastMsg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, fullName, selected, lastMsg, date);
    }
}
