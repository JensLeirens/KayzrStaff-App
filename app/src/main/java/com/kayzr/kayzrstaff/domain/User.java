package com.kayzr.kayzrstaff.domain;

/**
 * Created by Mafken on 7/09/2017.
 */

public class User {

    private int id;
    private boolean loggedOn ;
    private String username;
    private String password;
    private Role role;
    private String gsm;
    private String fullname;

    public User() {
    }

    public User(int id, boolean loggedOn, String username, String password, Role role, String gsm, String fullname) {
        this.id = id;
        this.loggedOn = loggedOn;
        this.username = username;
        this.password = password;
        this.role = role;
        this.gsm = gsm;
        this.fullname = fullname;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getGsm() {
        return gsm;
    }

    public void setGsm(String gsm) {
        this.gsm = gsm;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
