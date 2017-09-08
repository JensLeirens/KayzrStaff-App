package com.kayzr.kayzrstaff.domain;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;


public class KayzrApp extends Application {

    private List<Tournament> thisWeek = new ArrayList<>();
    private List<Tournament> nextWeek = new ArrayList<>();
    private List<User> kayzrTeam = new ArrayList<>();
    private User currentUser ;

    public List<Tournament> getThisWeek() {
        return thisWeek;
    }

    public void setThisWeek(List<Tournament> thisWeek) {
        this.thisWeek = thisWeek;
    }

    public List<Tournament> getNextWeek() {
        return nextWeek;
    }

    public void setNextWeek(List<Tournament> nextWeek) {
        this.nextWeek = nextWeek;
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