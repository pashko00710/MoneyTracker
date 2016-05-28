package com.example.moneytracker.rest;

import com.example.moneytracker.rest.api.LoginUserApi;
import com.example.moneytracker.rest.api.RegisterUserApi;

import retrofit.RestAdapter;

/**
 * Created by Павел on 18.05.2016.
 */
public class RestClient {
    private static final String BASE_URL = "http://lmt.loftblog.tmweb.ru/";

    private RegisterUserApi registerUserApi;
    private LoginUserApi loginUserApi;

    public RestClient() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        registerUserApi = restAdapter.create(RegisterUserApi.class);
    }

    public RegisterUserApi getRegisterUserApi() {
        return registerUserApi;
    }

    public LoginUserApi getLoginUserApi() {
        return loginUserApi;
    }
}
