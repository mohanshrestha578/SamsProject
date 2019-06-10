package com.example.samsproject;

public class User {

    private int id;
    private int image;
    private String name;
    private String userEmail;
    private String userNumber;


    public User(int id, int image, String name, String userEmail, String userNumber) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.userEmail = userEmail;
        this.userNumber = userNumber;
    }

    public int getId() {
        return id;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserNumber() {
        return userNumber;
    }
}
