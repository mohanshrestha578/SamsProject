package com.example.samsproject.Models;

public class User {

    public User(){}

    public User(String id, String uuid, String name, String email) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.email = email;
    }

    private String id;
    private String uuid;
    private String name;
    private String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
