package com.kayzr.kayzrstaff.domain;

/**
 * Created by Mafken on 7/09/2017.
 */

public class User {

    private int id ;
    private boolean loggedOn ;
    private String username;
    private String password;
    private Rank rank;
    private String gsm;
    private String fullName;

    public User(int id, boolean loggedOn, String username, String password, Rank rank, String gsm, String fullName) {
        this.id = id;
        this.loggedOn = loggedOn;
        this.username = username;
        this.password = password;
        this.rank = rank;
        this.gsm = gsm;
        this.fullName = fullName;
    }


    public User() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isLoggedOn() {
        return loggedOn;
    }

    public void setLoggedOn(boolean loggedOn) {
        this.loggedOn = loggedOn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public String getGsm() {
        return gsm;
    }

    public void setGsm(String gsm) {
        this.gsm = gsm;
    }
}
