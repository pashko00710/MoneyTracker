package com.example.moneytracker.rest;

import com.example.moneytracker.rest.api.RegisterUserApi;

import retrofit.RestAdapter;

/**
 * Created by Павел on 18.05.2016.
 */
public class RestClient {
    private static final String BASE_URL = "http://lmt.loftblog.tmweb.ru/";

    private RegisterUserApi registerUserApi;
    //...

    public RestClient() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        registerUserApi = restAdapter.create(RegisterUserApi.class);
        //...
    }

    public RegisterUserApi getRegisterUserApi() {
        return registerUserApi;
    }
}
