package com.example.moneytracker.rest.api;


import com.example.moneytracker.rest.model.UserLogoutModel;

import retrofit.http.GET;

public interface LogoutUserApi {
    @GET("/logout")
    UserLogoutModel logoutUser();
}
