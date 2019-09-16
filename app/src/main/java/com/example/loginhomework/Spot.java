package com.example.loginhomework;


import java.io.Serializable;
@SuppressWarnings("serial")

public class Spot implements Serializable {
    private int id;
    private String name;
    private String password;
    private String userName;

    public Spot(int id, String name, String password, String userName) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.userName = userName;
    }

    public boolean equals(Object obj) {
        return this.id == ((Spot) obj).id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
