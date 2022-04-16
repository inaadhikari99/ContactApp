package com.secret.contactapp.model;

public class ModelCall {
    private String number, duration, date, name;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.date = name;
    }

    public ModelCall(String number, String duration, String date, String name) {
        this.number = number;
        this.duration = duration;
        this.date = date;
        this.name = name;
    }


}

