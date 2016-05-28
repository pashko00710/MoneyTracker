package com.example.moneytracker.rest;

import com.example.moneytracker.rest.model.UserLoginModel;
import com.example.moneytracker.rest.model.UserRegistrationModel;

/**
 * Created by Павел on 18.05.2016.
 */
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

}
