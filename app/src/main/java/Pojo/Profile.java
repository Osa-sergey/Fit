package Pojo;

import java.util.Objects;

public class Profile {
    private String fullName;
    private float weight;
    private String power;
    private String experience;
    private int age;
    private String email;
    private String phone;
    private String med;

    public Profile(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
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

    public String getMed() {
        return med;
    }

    public void setMed(String med) {
        this.med = med;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return age == profile.age &&
                fullName.equals(profile.fullName) &&
                Objects.equals(weight, profile.weight) &&
                Objects.equals(power, profile.power) &&
                Objects.equals(experience, profile.experience) &&
                Objects.equals(email, profile.email) &&
                Objects.equals(phone, profile.phone) &&
                Objects.equals(med, profile.med);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, weight, power, experience, age, email, phone, med);
    }
}