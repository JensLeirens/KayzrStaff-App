package com.kayzr.kayzrstaff.domain.NetworkClasses;

// this is the response super class, this will handle the wrapper
public class Response {
    private boolean success;
    private String message;

    public Response() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
