package com.example.moneytracker.rest.model;

import com.google.gson.annotations.SerializedName;

public class UserExpenseModel {
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
