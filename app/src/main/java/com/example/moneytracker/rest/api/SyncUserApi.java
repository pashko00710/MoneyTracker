package com.example.moneytracker.rest.api;

import com.example.moneytracker.rest.model.UserSyncCategoriesModel;
import com.example.moneytracker.rest.model.UserSyncExpensesModel;

import retrofit.http.POST;
import retrofit.http.Query;

public interface SyncUserApi {
    @POST("/categories/synch")
    UserSyncCategoriesModel syncCategory(
            @Query("google_token") String gToken,
            @Query("data") String data,
            @Query("auth_token") String token);

    @POST("/transactions/synch")
    UserSyncExpensesModel syncExpense(
            @Query("google_token") String gToken,
            @Query("data") String data,
            @Query("auth_token") String token);
}
