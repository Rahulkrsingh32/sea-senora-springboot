package com.backend.seasenora.model;

import java.util.List;

public class CustomerResponse {
    private String token;
    private List<String> roles;
    private int customerID;

    public CustomerResponse(String token, List<String> roles, int customerID) {
        super();
        this.token = token;
        this.roles = roles;
        this.customerID = customerID;
    }

    public CustomerResponse() {
        super();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
}
