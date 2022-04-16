package com.secret.contactapp.model;

public class ModelContact {
    private String name,number;
    public ModelContact() {

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public ModelContact(String name, String number) {
        this.name = name;
        this.number = number;
    }
}
