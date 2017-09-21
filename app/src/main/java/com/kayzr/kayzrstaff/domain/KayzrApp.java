package com.kayzr.kayzrstaff.domain;

import android.app.Application;
import android.widget.TableLayout;

import com.kayzr.kayzrstaff.fragments.RosterFragment;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class KayzrApp extends Application {

    private List<Tournament> thisWeek = new ArrayList<>();
    private List<Tournament> nextWeek = new ArrayList<>();
    private List<Availability> availabilities = new ArrayList<>();
    private List<User> kayzrTeam = new ArrayList<>();
    private User currentUser ;
    private EndWeek endOfWeek;


    public List<Tournament> getNextWeek() {
        return nextWeek;
    }

    public void setNextWeek(List<Tournament> nextWeek) {
        this.nextWeek = nextWeek;
    }

    public EndWeek getEndOfWeek() {
        return endOfWeek;
    }

    public void setEndOfWeek(EndWeek endOfWeek) {
        this.endOfWeek = endOfWeek;
    }

    public List<Tournament> getThisWeek() {
        return thisWeek;
    }

    public void setThisWeek(List<Tournament> thisWeek) {
        this.thisWeek = thisWeek;
    }

    public List<Availability> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(List<Availability> availabilities) {
        this.availabilities = availabilities;
    }

    public List<User> getKayzrTeam() {
        return kayzrTeam;
    }

    public void setKayzrTeam(List<User> kayzrTeam) {
        this.kayzrTeam = kayzrTeam;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }


    /*selecting default (current)day of the week */
    private int tabIndex;

    public int currentDeviceDay(){//neemt de huidige dag vd week vh toestel
        GregorianCalendar calendar = new GregorianCalendar();
        return calendar.get(Calendar.DAY_OF_WEEK);

    }
    public int currentDayOfWeek(){
        int day = currentDeviceDay();
        switch(day){

            case 1: tabIndex = 6;//day = sunday, tabIndex = 6 (starts from 0 and sunday is the last day so 6)
                break;

            case 2: tabIndex = 0;
                break;

            case 3: tabIndex = 1;
                break;

            case 4: tabIndex = 2;
                break;

            case 5: tabIndex = 3;
                break;

            case 6: tabIndex = 4;
                break;

            case 7: tabIndex = 5;//day = saturday, tabIndex = 5 (starts from 0 and saturday is the 2nd last day so 5)
                break;

        }
        return tabIndex;
    }
    public String dayOfWeek(){
        String selectedDay = "";
        if(tabIndex == 0 ){
            selectedDay = "Maandag";
        } else if(tabIndex == 1){
            selectedDay = "Dinsdag";
        }else if(tabIndex == 2){
            selectedDay = "Woensdag";
        }else if(tabIndex == 3){
            selectedDay = "Donderdag";
        }else if(tabIndex == 4){
            selectedDay = "Vrijdag";
        }else if(tabIndex == 5){
            selectedDay = "Zaterdag";
        }else if(tabIndex == 6){
            selectedDay = "Zondag";
        }
        return selectedDay;
    }

}