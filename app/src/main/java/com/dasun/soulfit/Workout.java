package com.dasun.soulfit;

public class Workout {
    private String workoutId;
    private String name;
    private Double minute;
    private Double calorie;

    public Workout() {

    }

    public Workout(String workoutId, String name, Double minute, Double calorie) {
        this.workoutId = workoutId;
        this.name = name;
        this.minute = minute;
        this.calorie = calorie;
    }

    public Workout(String name, Double minute, Double calorie) {
        this.name = name;
        this.minute = minute;
        this.calorie = calorie;
    }

    public String getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(String workoutId) {
        this.workoutId = workoutId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMinute() {
        return minute;
    }

    public void setMinute(Double minute) {
        this.minute = minute;
    }

    public Double getCalorie() {
        return calorie;
    }

    public void setCalorie(Double calorie) {
        this.calorie = calorie;
    }
}
