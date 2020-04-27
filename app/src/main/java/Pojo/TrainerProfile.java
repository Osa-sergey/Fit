package Pojo;

import java.io.Serializable;
import java.util.List;

public class TrainerProfile implements Serializable {
    private int id;
    private String url;
    private String fullName;
    private int experience;
    private int reviewsCount;
    private float rating;
    private int age;
    private String email;
    private String phone;
    private String about;
    private List<ReviewItem> reviews;

    public List<ReviewItem> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewItem> reviews) {
        this.reviews = reviews;
        this.reviewsCount = reviews.size();
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getReviewsCount() {
        return reviewsCount;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setId(int id) {
        this.id = id;
    }
}
