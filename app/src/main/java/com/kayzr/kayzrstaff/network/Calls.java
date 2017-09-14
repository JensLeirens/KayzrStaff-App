package com.kayzr.kayzrstaff.network;

import com.kayzr.kayzrstaff.domain.Tournament;
import com.kayzr.kayzrstaff.domain.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Calls {

    @GET("thisweek/")
    Call<List<Tournament>> getThisWeekTournaments();

    @GET("availabilities/")
    Call<List<Tournament>> getAvailabilities();

    @GET("users/")
    Call<List<User>> getUser(@Query("user") String username);

    @GET("users/")
    Call<List<User>> getUsers();

    //End Week 0 = Niet einde van de week. Dus availabilities zijn nog open.
    //End Week 1 = Einde van de Week. Roster Next Week is gemaakt en mensen kunnen geen availabilities meer invullen
    @GET("EndWeek/")
    Call<List<Integer>> getEndweek();

}
