package com.example.loginhomework;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class User implements Serializable {
    private String name, password;

    public User() {
        super();
    }

    public User(String name, String password) {
        super();
        this.name = name;
        this.password = password;
    }

    @NonNull
    @Override
    public String toString() {
        String text = "name: " + name + "\npassword: " + password;
        return text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
