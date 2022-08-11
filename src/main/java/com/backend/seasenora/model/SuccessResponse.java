package com.backend.seasenora.model;

public class SuccessResponse {
    private String successMessage;

    public SuccessResponse(String successMessage) {
        super();
        this.successMessage = successMessage;
    }

    public SuccessResponse() {
        super();
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

}
