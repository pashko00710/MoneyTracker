package com.example.moneytracker.rest.api;

import com.example.moneytracker.rest.model.UserExpenseModel;

import retrofit.http.POST;
import retrofit.http.Query;

public interface ExpenseUserApi {
    @POST("/transactions/add")
    UserExpenseModel addExpense(@Query("sum") Double sum, @Query("comment") String comment,
                                @Query("category_id") Integer categoryId, @Query("tr_date") String trDate,
                                @Query("google_token") String gToken, @Query("auth_token") String token);
}
