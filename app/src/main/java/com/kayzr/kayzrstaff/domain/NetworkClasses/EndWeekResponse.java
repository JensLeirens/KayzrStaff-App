package com.kayzr.kayzrstaff.domain.NetworkClasses;

import com.kayzr.kayzrstaff.domain.EndWeek;

public class EndWeekResponse extends Response {

    private EndWeek data;

    public EndWeekResponse(EndWeek endWeek) {
        this.data = endWeek;
    }

    public EndWeek getEndWeek() {
        return data;
    }

    public void setEndWeek(EndWeek endWeek) {
        this.data = endWeek;
    }
}
