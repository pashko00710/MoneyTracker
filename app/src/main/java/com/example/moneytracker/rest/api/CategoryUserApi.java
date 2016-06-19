package com.example.moneytracker.rest.api;

import com.example.moneytracker.rest.model.CategoriesModel;

import retrofit.http.GET;
import retrofit.http.Query;

public interface CategoryUserApi {
    @GET("/categories")
    CategoriesModel getAllCategories(
            @Query("google_token") String gToken,
            @Query("token")String token);

    @GET("/categories/add")
    CategoriesModel addCategory(
            @Query("google_token") String gToken,
            @Query("title") String title,
            @Query("token")String token);
}
