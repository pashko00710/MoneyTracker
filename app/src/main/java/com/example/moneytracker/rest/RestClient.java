package com.example.moneytracker.rest;

import com.example.moneytracker.rest.api.CategoryUserApi;
import com.example.moneytracker.rest.api.ExpenseUserApi;
import com.example.moneytracker.rest.api.GoogleTokenApi;
import com.example.moneytracker.rest.api.LoginUserApi;
import com.example.moneytracker.rest.api.LogoutUserApi;
import com.example.moneytracker.rest.api.RegisterUserApi;
import com.example.moneytracker.rest.api.SyncUserApi;

import retrofit.RestAdapter;

public class RestClient {
    private static final String BASE_URL = "http://lmt.loftblog.tmweb.ru/";

    private RegisterUserApi registerUserApi;
    private LoginUserApi loginUserApi;
    private GoogleTokenApi googleTokenApi;
    private LogoutUserApi logoutUserApi;
    private SyncUserApi syncUserApi;
    private ExpenseUserApi expenseUserApi;
    private CategoryUserApi categoryUserApi;

    public RestClient() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        registerUserApi = restAdapter.create(RegisterUserApi.class);
        loginUserApi = restAdapter.create(LoginUserApi.class);
        googleTokenApi = restAdapter.create(GoogleTokenApi.class);
        logoutUserApi = restAdapter.create(LogoutUserApi.class);
        syncUserApi = restAdapter.create(SyncUserApi.class);
        expenseUserApi = restAdapter.create(ExpenseUserApi.class);
        categoryUserApi = restAdapter.create(CategoryUserApi.class);
    }

    public RegisterUserApi getRegisterUserApi() {
        return registerUserApi;
    }

    public LoginUserApi getLoginUserApi() {
        return loginUserApi;
    }

    public GoogleTokenApi getGoogleTokenApi() {
        return  googleTokenApi;
    }

    public LogoutUserApi getLogoutUserApi() {
        return logoutUserApi;
    }

    public SyncUserApi getSyncUserApi() {
        return  syncUserApi;
    }

    public ExpenseUserApi getExpenseUserApi() { return  expenseUserApi; }

    public CategoryUserApi getCategoryUserApi() {
        return  categoryUserApi;
    }
}
