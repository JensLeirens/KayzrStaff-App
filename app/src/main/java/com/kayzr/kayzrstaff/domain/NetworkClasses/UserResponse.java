package com.kayzr.kayzrstaff.domain.NetworkClasses;

import com.kayzr.kayzrstaff.domain.User;

import java.util.List;

public class UserResponse extends Response {

    private List<User> data ;

    public UserResponse(List<User> data) {
        this.data = data;
    }

    public List<User> getData() {
        return data;
    }
}
