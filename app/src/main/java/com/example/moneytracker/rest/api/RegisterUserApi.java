package com.example.moneytracker.rest.api;

import com.example.moneytracker.rest.model.UserRegistrationModel;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Павел on 18.05.2016.
 */
public interface RegisterUserApi {
    @GET("/auth")
    UserRegistrationModel registerUser(@Query("login") String login,
                                       @Query("password") String password,
                                       @Query("register") String register);
}
