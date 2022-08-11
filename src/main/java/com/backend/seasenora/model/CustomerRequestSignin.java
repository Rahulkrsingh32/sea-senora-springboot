package com.backend.seasenora.model;

public class CustomerRequestSignin {
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public CustomerRequestSignin(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    public CustomerRequestSignin() {
        super();
    }
}
