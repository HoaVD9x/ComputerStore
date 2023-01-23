package com.example.computerstore.Payload;

import jakarta.validation.constraints.NotEmpty;

public class LoginPayload {
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;

    private boolean remember = false;

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
}
