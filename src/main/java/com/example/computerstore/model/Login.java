package com.example.computerstore.model;

import jakarta.persistence.*;


public class Login {

    private int logInId;

    private String email;

    private String password;

    private boolean remember = false;

    public Login(int logInId, String email, String password, boolean remember) {
        this.logInId = logInId;
        this.email = email;
        this.password = password;
        this.remember = remember;
    }

    public Login() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRemember() {
        return remember;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
    }

    public int getLogInId() {
        return logInId;
    }

    public void setLogInId(int logInId) {
        this.logInId = logInId;
    }
}
