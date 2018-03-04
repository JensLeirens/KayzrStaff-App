package com.kayzr.kayzrstaff.network;

import com.kayzr.kayzrstaff.domain.Availability;
import com.kayzr.kayzrstaff.domain.EndWeek;
import com.kayzr.kayzrstaff.domain.JsonResponse;
import com.kayzr.kayzrstaff.domain.Tournament;
import com.kayzr.kayzrstaff.domain.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Calls {
//this RESTish API doc can be found on https://gist.github.com/ThePjpollie/c72a80f70b9186dc737d447b76273b3c

    @GET("thisweek/")
    Call<List<Tournament>> getThisWeekTournaments();

    @GET("availabilities/")
    Call<List<Availability>> getAvailabilities();

    @GET("NextWeek/")
    Call<List<Tournament>> getNextWeekTournaments();

    @GET("users/")
    Call<User> getUser(@Query("user") String username);

    @GET("SendAV/")
    Call<JsonResponse> sendAV(@Query("key") String Key,@Query("user") String username, @Query("id") String TournamentId );

    @GET("ClearAV/")
    Call<JsonResponse> clearAV(@Query("key") String Key, @Query("user") String User);

    @GET("users/")
    Call<List<User>> getUsers();

     @GET("EndWeek/")
    Call<EndWeek> getEndweek();

}
