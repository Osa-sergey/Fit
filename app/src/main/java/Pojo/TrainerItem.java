package Pojo;

import java.util.Objects;

public class TrainerItem {
    private String url;
    private String trainerName;
    private String comment;
    private int starsCount;
    private float ratingNumber;
    private int reviewsCount;

    public TrainerItem(String url, String trainerName, String comment, int starsCount, float ratingNumber, int reviewsCount) {
        this.url = url;
        this.trainerName = trainerName;
        this.comment = comment;
        this.starsCount = starsCount;
        this.ratingNumber = ratingNumber;
        this.reviewsCount = reviewsCount;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getStarsCount() {
        return starsCount;
    }

    public void setStarsCount(int starsCount) {
        this.starsCount = starsCount;
    }

    public float getRatingNumber() {
        return ratingNumber;
    }

    public void setRatingNumber(float ratingNumber) {
        this.ratingNumber = ratingNumber;
    }

    public int getReviewsCount() {
        return reviewsCount;
    }

    public void setReviewsCount(int reviewsCount) {
        this.reviewsCount = reviewsCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainerItem that = (TrainerItem) o;
        return starsCount == that.starsCount &&
                ratingNumber == that.ratingNumber &&
                reviewsCount == that.reviewsCount &&
                Objects.equals(url, that.url) &&
                trainerName.equals(that.trainerName) &&
                Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, trainerName, comment, starsCount, ratingNumber, reviewsCount);
    }
}
