package com.kayzr.kayzrstaff.domain.NetworkClasses;

import com.kayzr.kayzrstaff.domain.Tournament;

import java.util.List;

public class TournamentResponse extends Response {
    private List<Tournament> data ;

    public TournamentResponse(List<Tournament> data) {
        this.data = data;
    }

    public List<Tournament> getData() {
        return data;
    }

    public void setData(List<Tournament> data) {
        this.data = data;
    }
}
