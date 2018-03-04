package com.kayzr.kayzrstaff.domain;

public class JsonResponse {
    private boolean error;
    private String message;

    public JsonResponse() {
    }

    public JsonResponse(boolean error, String message) {
        this.error = error;
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
