package com.dasun.soulfit;

public class Model {
    String id,food,cal;

    public Model(){

    }

    public Model(String id, String food, String cal) {
        this.id = id;
        this.food = food;
        this.cal = cal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getCal() {
        return cal;
    }

    public void setCal(String cal) {
        this.cal = cal;
    }
}
