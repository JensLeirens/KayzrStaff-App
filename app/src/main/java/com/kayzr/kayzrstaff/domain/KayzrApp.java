package com.kayzr.kayzrstaff.domain;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;


public class KayzrApp extends Application {

    private List<Tournament> thisWeek = new ArrayList<>();
    private List<Tournament> nextWeek = new ArrayList<>();
    private List<Availability> availabilities = new ArrayList<>();
    private List<User> kayzrTeam = new ArrayList<>();
    private User currentUser ;
    private EndWeek endOfWeek;


    public List<Tournament> getNextWeek() {
        return nextWeek;
    }

    public void setNextWeek(List<Tournament> nextWeek) {
        this.nextWeek = nextWeek;
    }

    public EndWeek getEndOfWeek() {
        return endOfWeek;
    }

    public void setEndOfWeek(EndWeek endOfWeek) {
        this.endOfWeek = endOfWeek;
    }

    public List<Tournament> getThisWeek() {
        return thisWeek;
    }

    public void setThisWeek(List<Tournament> thisWeek) {
        this.thisWeek = thisWeek;
    }

    public List<Availability> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(List<Availability> availabilities) {
        this.availabilities = availabilities;
    }

    public List<User> getKayzrTeam() {
        return kayzrTeam;
    }

    public void setKayzrTeam(List<User> kayzrTeam) {
        this.kayzrTeam = kayzrTeam;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}