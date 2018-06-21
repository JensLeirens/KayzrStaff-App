package com.kayzr.kayzrstaff.domain.NetworkClasses;

import com.kayzr.kayzrstaff.domain.User;

public class AuthResponse extends Response{
    private User data;

    public AuthResponse() {
    }

    public AuthResponse(User data) {
        this.data = data;
    }

    public User getUser() {
        return data;
    }

    public void setUser(User user) {
        this.data = user;
    }
}
