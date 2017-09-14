package com.kayzr.kayzrstaff.domain;

/**
 * Created by Mafken on 14/09/2017.
 */

public class Availability {
    private String user;
    private int tournamentId;
    private Tournament tournament;
    private boolean checked;

    public Availability(String user) {
        this.user = user;
    }

    public Availability(String user, int tournamentId) {
        this.user = user;
        this.tournamentId = tournamentId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(int tournamentId) {
        this.tournamentId = tournamentId;
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
