package com.example.moneytracker.rest.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Павел on 28.05.2016.
 */
public class UserLoginModel {
    @SerializedName("status")
    private String status;
    @SerializedName("id")
    private Integer id;
    @SerializedName("auth_token")
    private String authToken;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
