package com.kayzr.kayzrstaff.network;

import com.kayzr.kayzrstaff.domain.EndWeek;
import com.kayzr.kayzrstaff.domain.NetworkClasses.AuthRequest;
import com.kayzr.kayzrstaff.domain.NetworkClasses.AuthResponse;
import com.kayzr.kayzrstaff.domain.NetworkClasses.AvRequest;
import com.kayzr.kayzrstaff.domain.NetworkClasses.AvResponse;
import com.kayzr.kayzrstaff.domain.NetworkClasses.AvSendResponse;
import com.kayzr.kayzrstaff.domain.NetworkClasses.TournamentResponse;
import com.kayzr.kayzrstaff.domain.NetworkClasses.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface Calls {
//this REST API doc can be found on https://gist.github.com/ThePjpollie/c72a80f70b9186dc737d447b76273b3c

    @GET("thisweek/")
    Call<TournamentResponse> getThisWeekTournaments();

    @GET("availabilities/")
    Call<AvResponse> getAvailabilities();

    @GET("NextWeek/")
    Call<TournamentResponse> getNextWeekTournaments();

    @POST("auth/login")
    Call<AuthResponse> getAuth(@Body AuthRequest body);

    @PUT("availabilities")
    Call<AvSendResponse> sendAVs(@Body AvRequest body);

    @GET("users/")
    Call<UserResponse> getUsers(@Query("key") String Key);

    @GET("EndWeek/")
    Call<EndWeek> getEndweek();

}
