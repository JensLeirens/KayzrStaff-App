package com.kayzr.kayzrstaff.network;

import com.kayzr.kayzrstaff.domain.Availability;
import com.kayzr.kayzrstaff.domain.EndWeek;
import com.kayzr.kayzrstaff.domain.JsonResponse;
import com.kayzr.kayzrstaff.domain.Tournament;
import com.kayzr.kayzrstaff.domain.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Calls {

    @GET("thisweek/")
    Call<List<Tournament>> getThisWeekTournaments();

    @GET("availabilities/")
    Call<List<Availability>> getAvailabilities();


    @GET("NextWeek/")
    Call<List<Tournament>> getNextWeekTournaments();

    @GET("users/")
    Call<List<User>> getUser(@Query("user") String username);

    @POST("SendAV/")
    Call<List<JsonResponse>> sendAV(@Query("Mod") String username, @Query("Id") String TournamentId, @Query("Key") String Key);

    @POST("ClearAV/")
    Call<List<JsonResponse>> clearAV(@Query("Mod") String User, @Query("Key") String Key);

    @GET("users/")
    Call<List<User>> getUsers();

     @GET("EndWeek/")
    Call<List<EndWeek>> getEndweek();

}
