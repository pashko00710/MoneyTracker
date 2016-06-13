package com.example.moneytracker.rest;

import android.content.Context;

import com.example.moneytracker.rest.model.GoogleModel;
import com.example.moneytracker.rest.model.UserExpenseModel;
import com.example.moneytracker.rest.model.UserLoginModel;
import com.example.moneytracker.rest.model.UserLogoutModel;
import com.example.moneytracker.rest.model.UserRegistrationModel;
import com.example.moneytracker.rest.model.UserSyncCategoriesModel;
import com.example.moneytracker.rest.model.UserSyncExpensesModel;
import com.example.moneytracker.util.DataBaseApp;

public class RestService {
    private static final String REGISTER_FLAG = "1";

    private RestClient restClient;

    public RestService() {
        restClient = new RestClient();
    }

    public UserRegistrationModel register(String login, String password) {
        return restClient.getRegisterUserApi().registerUser(login, password, REGISTER_FLAG);
    }

    public UserLoginModel login(String login, String password){
        return restClient.getLoginUserApi().loginUser(login, password);
    }

    public GoogleModel getJsonModel(Context context) {
        return restClient.getGoogleTokenApi().googleJsonToken(DataBaseApp.getGoogleToken(context));
    }

    public UserLogoutModel logout() {
        return restClient.getLogoutUserApi().logoutUser();
    }

    public UserSyncCategoriesModel syncCategories(Context context, String data) {
        return restClient.getSyncUserApi().syncCategory(DataBaseApp.getGoogleToken(context), data, DataBaseApp.getAuthKey());
    }

    public UserSyncExpensesModel syncExpenses(Context context, String data) {
        return restClient.getSyncUserApi().syncExpense(DataBaseApp.getGoogleToken(context), data, DataBaseApp.getAuthKey());
    }

    public UserExpenseModel addExpense(int sum, String comment, int categoryId, String trDate, String gToken) {
        return restClient.getExpenseUserApi().addExpense(sum, comment, categoryId, trDate, gToken, DataBaseApp.getAuthKey());
    }



}
