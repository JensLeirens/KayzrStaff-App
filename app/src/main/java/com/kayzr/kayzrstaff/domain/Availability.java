package com.kayzr.kayzrstaff.domain;

/**
 * Created by Mafken on 14/09/2017.
 */

public class Availability {
    private User user;
    private Tournament tournament;
    private boolean checked;

    public Availability(User user) {
        this.user = user;
    }

    public Availability(User user, Tournament tournament) {
        this.user = user;
        this.tournament = tournament;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
