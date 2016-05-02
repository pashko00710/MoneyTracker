package com.example.moneytracker.model;

/**
 * Created by Павел on 21.04.2016.
 */
public class MyListCategory {
    private String name;

    public MyListCategory(String name) {
        this.setName(name);
    }
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
