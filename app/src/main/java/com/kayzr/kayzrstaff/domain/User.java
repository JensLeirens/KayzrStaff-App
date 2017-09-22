package com.kayzr.kayzrstaff.domain;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * Created by Mafken on 7/09/2017.
 */
@Entity
public class User {

    @Id
    private long id;
    
    private int userId;
    private boolean loggedOn ;
    private String username;
    private String password;

    @Convert(converter = RoleConverter.class, columnType = String.class)
    private Role role;

    private String gsm;
    private String fullname;

    //settings
    private boolean notifactionsModDay;
    private boolean notifactionsAV;
    private boolean rememberUsernameAndPass;


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

    @Generated(hash = 1597889354)
    public User(long id, int userId, boolean loggedOn, String username, String password, Role role, String gsm,
            String fullname, boolean notifactionsModDay, boolean notifactionsAV, boolean rememberUsernameAndPass) {
        this.id = id;
        this.userId = userId;
        this.loggedOn = loggedOn;
        this.username = username;
        this.password = password;
        this.role = role;
        this.gsm = gsm;
        this.fullname = fullname;
        this.notifactionsModDay = notifactionsModDay;
        this.notifactionsAV = notifactionsAV;
        this.rememberUsernameAndPass = rememberUsernameAndPass;
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

    public boolean isNotifactionsModDay() {
        return notifactionsModDay;
    }

    public void setNotifactionsModDay(boolean notifactionsModDay) {
        this.notifactionsModDay = notifactionsModDay;
    }

    public boolean isNotifactionsAV() {
        return notifactionsAV;
    }

    public void setNotifactionsAV(boolean notifactionsAV) {
        this.notifactionsAV = notifactionsAV;
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

    public boolean getNotifactionsModDay() {
        return this.notifactionsModDay;
    }

    public boolean getNotifactionsAV() {
        return this.notifactionsAV;
    }

    public boolean getRememberUsernameAndPass() {
        return this.rememberUsernameAndPass;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    static class RoleConverter implements PropertyConverter<Role, String> {
        @Override
        public Role convertToEntityProperty(String databaseValue) {
            return Role.valueOf(databaseValue);
        }

        @Override
        public String convertToDatabaseValue(Role entityProperty) {
            return entityProperty.name();
        }
    }
}
