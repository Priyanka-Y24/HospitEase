package com.example.hms;
public class Accountant {
    private final int id;
    private final String username;
    private final String password;

    public Accountant(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
