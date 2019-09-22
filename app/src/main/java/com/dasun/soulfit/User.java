package com.dasun.soulfit;

public class User {


     String Birthday;
     String gender;
     String CuserId;
     String height;
     String weight;

    public User() {
    }

    public User(String birthday, String gender, String cuserId, String height, String weight) {
        Birthday = birthday;
        this.gender = gender;
        CuserId = cuserId;
        this.height = height;
        this.weight = weight;
    }

    public String getBirthday() {
        return Birthday;
    }

    public String getGender() {
        return gender;
    }

    public String getCuserId() {
        return CuserId;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }
}