package com.kayzr.kayzrstaff.domain;

/**
 * Created by Mafken on 7/09/2017.
 */

public enum Role {
    CM(0),
    Mod(1),
    Admin(2);

    private final int value;

    Role(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
