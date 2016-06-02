package com.example.moneytracker.rest.api;

import com.example.moneytracker.rest.model.UserLoginModel;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Павел on 28.05.2016.
 */
public interface LoginUserApi {
    @GET("/auth")
    UserLoginModel loginUser(@Query("login") String login,
                             @Query("password") String password);
}
