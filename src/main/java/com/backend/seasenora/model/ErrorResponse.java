package com.backend.seasenora.model;

import java.time.LocalDate;

public class ErrorResponse {
    private String errorMessage;
    private int statusCode;
    private LocalDate date;

    public ErrorResponse(String errorMessage, int statusCode, LocalDate date) {
        super();
        this.errorMessage = errorMessage;
        this.statusCode = statusCode;
        this.date = date;
    }

    public ErrorResponse() {
        super();
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
