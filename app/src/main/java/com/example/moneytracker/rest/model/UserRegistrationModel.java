package com.example.moneytracker.rest.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Павел on 18.05.2016.
 */
public class UserRegistrationModel {
    @SerializedName("status")
    private String status;
    @SerializedName("id")
    private Integer id;

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
}
