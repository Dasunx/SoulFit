package com.dasun.soulfit;

import java.sql.Date;

public class Schedule {
    private String ScheuleId;
    private int workoutsCount;
    private double caloriesBurned;
    private Date day;

    public Schedule(String scheuleId, int workoutsCount, double caloriesBurned, Date day) {
        ScheuleId = scheuleId;
        this.workoutsCount = workoutsCount;
        this.caloriesBurned = caloriesBurned;
        this.day = day;
    }
    public Schedule(){

    }

    public String getScheuleId() {
        return ScheuleId;
    }

    public void setScheuleId(String scheuleId) {
        ScheuleId = scheuleId;
    }

    public int getWorkoutsCount() {
        return workoutsCount;
    }

    public void setWorkoutsCount(int workoutsCount) {
        this.workoutsCount = workoutsCount;
    }

    public double getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(double caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }


}
