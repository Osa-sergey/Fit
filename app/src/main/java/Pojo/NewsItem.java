package Pojo;

import java.util.Objects;

public class NewsItem {
    private String title;
    private String text;
    private String imgUrl;
    private boolean expanded;

    public NewsItem(String title, String text, String imgUrl) {
        this.title = title;
        this.text = text;
        this.imgUrl = imgUrl;
        this.expanded = false;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsItem newsItem = (NewsItem) o;
        return title.equals(newsItem.title) &&
                text.equals(newsItem.text) &&
                Objects.equals(imgUrl, newsItem.imgUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, text, imgUrl);
    }
}
