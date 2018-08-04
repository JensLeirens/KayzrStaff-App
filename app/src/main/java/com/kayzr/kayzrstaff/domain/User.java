package com.kayzr.kayzrstaff.domain;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Mafken on 7/09/2017.
 */
public class User extends RealmObject{

    @PrimaryKey
    private String id;

    private String avatar;
    private String username;
    private String password;
    private String position;

    private boolean loggedOn ;

    private String phone;
    //settings
    private boolean rememberUsernameAndPass;


    public User() {
    }

    public User(String id, boolean loggedOn, String username, String password, String position, String phone) {
        this.id = id;
        this.loggedOn = loggedOn;
        this.username = username;
        this.password = password;
        this.position = position;
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isRememberUsernameAndPass() {
        return rememberUsernameAndPass;
    }

    public void setRememberUsernameAndPass(boolean rememberUsernameAndPass) {
        this.rememberUsernameAndPass = rememberUsernameAndPass;
    }

    public boolean getLoggedOn() {
        return this.loggedOn;
    }

    public boolean getRememberUsernameAndPass() {
        return this.rememberUsernameAndPass;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
