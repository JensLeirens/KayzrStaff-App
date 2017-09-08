package com.kayzr.kayzrstaff.network;

import com.kayzr.kayzrstaff.domain.Tournament;
import com.kayzr.kayzrstaff.domain.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Calls {

    @GET("thisweek/")
    Call<List<Tournament>> getThisWeekTournaments();

    @GET("NextWeek/")
    Call<List<Tournament>> getNextWeekTournaments();

    @GET("users/?user={username}")
    Call<User> getUser(@Path("username") String username);

    @GET("users/")
    Call<List<User>> getUsers();

}
