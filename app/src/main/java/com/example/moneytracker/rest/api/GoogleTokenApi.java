package com.example.moneytracker.rest.api;

import com.example.moneytracker.rest.model.GoogleModel;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Павел on 28.05.2016.
 */
public interface GoogleTokenApi {
    @GET("/gjson")
    GoogleModel googleJsonToken(@Query("google_token") String gToken);
}
