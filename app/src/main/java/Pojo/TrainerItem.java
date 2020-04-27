package Pojo;


public class TrainerItem {

    private int id;
    private String url;
    private String trainerName;
    private String comment;
    private float starsCount;
    private float ratingNumber;
    private int reviewsCount;

    public TrainerItem(String url, String trainerName, String comment, float starsCount, float ratingNumber, int reviewsCount) {
        this.url = url;
        this.trainerName = trainerName;
        this.comment = comment;
        this.starsCount = starsCount;
        this.ratingNumber = ratingNumber;
        this.reviewsCount = reviewsCount;
    }

    public int getId() {
        return id;
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

    public float getStarsCount() {
        return starsCount;
    }

    public void setStarsCount(float starsCount) {
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

    public void setId(int id) {
        this.id = id;
    }
}
