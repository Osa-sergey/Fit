package Pojo;

public class ReviewItem {
    private String url;
    private int starsCount;
    private String fullName;
    private String text;

    public ReviewItem(String url, int starsCount, String fullName, String text) {
        this.url = url;
        this.starsCount = starsCount;
        this.fullName = fullName;
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStarsCount() {
        return starsCount;
    }

    public void setStarsCount(int starsCount) {
        this.starsCount = starsCount;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
