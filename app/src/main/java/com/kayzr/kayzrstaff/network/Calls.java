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

    @GET("NextWeek/")
    Call<List<Tournament>> getNextWeekTournaments();

    @GET("users/")
    Call<List<User>> getUser(@Query("user") String username);

    @GET("users/")
    Call<List<User>> getUsers();

}
