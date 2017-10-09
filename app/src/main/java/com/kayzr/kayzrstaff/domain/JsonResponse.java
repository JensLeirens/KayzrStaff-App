package com.kayzr.kayzrstaff.domain;

/**
 * Created by Mafken on 9/10/2017.
 */

public class JsonResponse {
    private String error;

    public JsonResponse(String error) {
        this.error = error;
    }

    public JsonResponse() {
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
