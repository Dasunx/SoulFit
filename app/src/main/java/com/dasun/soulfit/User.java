package com.dasun.soulfit;

public class User {


    private String Birthday;
    private String male;
    private String female;
    private String height;
    private String weight;

    public User(String birthday, String male, String female, String height, String weight) {
        Birthday = birthday;
        this.male = male;
        this.female = female;
        this.height = height;
        this.weight = weight;
    }

    public User() {

    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public String getMale() {
        return male;
    }

    public void setMale(String male) {
        this.male = male;
    }

    public String getFemale() {
        return female;
    }

    public void setFemale(String female) {
        this.female = female;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}