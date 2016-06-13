package com.example.moneytracker.rest.model;

import com.google.gson.annotations.SerializedName;

public class UserData {
    @SerializedName("id")
    private Integer id;
    @SerializedName("title")
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
