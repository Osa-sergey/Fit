package Pojo;

import java.util.List;
import java.util.Objects;

public class ExerciseItem {
    private int starsCount;
    private String src;
    private String title;
    private String description;
    private List<String> tags;

    public ExerciseItem(int starsCount, String src, String title, String description, List<String> tags) {
        this.starsCount = starsCount;
        this.src = src;
        this.title = title;
        this.description = description;
        this.tags = tags;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public int getStarsCount() {
        return starsCount;
    }

    public void setStarsCount(int starsCount) {
        this.starsCount = starsCount;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExerciseItem item = (ExerciseItem) o;
        return starsCount == item.starsCount &&
                src.equals(item.src) &&
                title.equals(item.title) &&
                description.equals(item.description) &&
                Objects.equals(tags, item.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(starsCount, src, title, description, tags);
    }
}
