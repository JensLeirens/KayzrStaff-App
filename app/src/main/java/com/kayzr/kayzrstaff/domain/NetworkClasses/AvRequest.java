package com.kayzr.kayzrstaff.domain.NetworkClasses;

import java.util.List;

public class AvRequest {
    private String key;
    private String user;
    private List<String> tournaments;

    public AvRequest() {
    }

    public AvRequest(String key, String user, List<String> tournaments) {
        this.key = key;
        this.user = user;
        this.tournaments = tournaments;
    }

    public String getKey() {
        return key;
    }

    public AvRequest setKey(String key) {
        this.key = key;
        return this;
    }

    public String getUser() {
        return user;
    }

    public AvRequest setUser(String user) {
        this.user = user;
        return this;
    }

    public List<String> getTournaments() {
        return tournaments;
    }

    public AvRequest setTournaments(List<String> tournaments) {
        this.tournaments = tournaments;
        return this;
    }
}