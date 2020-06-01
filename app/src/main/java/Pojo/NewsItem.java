package Pojo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Entity (tableName = "news")
public class NewsItem {

    //Хранятся в базе
    @PrimaryKey
    private int id;
    @NonNull
    private String title;
    @ColumnInfo (name = "text_news")
    @NonNull
    private String text;

    //Игнорируются
    @Ignore
    private String imgUrl;
    @Ignore
    private boolean expanded;

    public NewsItem(int id, @NotNull String title, @NotNull String text) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.expanded = false;
        //TODO вписать название сайта
        imgUrl = "news_photo_prev_"+id;
    }

    //TODO удалить тестовый конструктор
    public NewsItem(int id, @NotNull String title, @NotNull String text, String imgUrl) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.expanded = false;
        this.imgUrl = imgUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
