package com.kayzr.kayzrstaff.domain;

public class JsonResponse {
    private boolean succes;
    private String message;

    public JsonResponse() {
    }

    public JsonResponse(boolean succes, String message) {
        this.succes = succes;
        this.message = message;
    }

    public boolean isSucces() {
        return succes;
    }

    public void setSucces(boolean succes) {
        this.succes = succes;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
