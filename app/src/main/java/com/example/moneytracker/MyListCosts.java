package com.example.moneytracker;

/**
 * Created by Павел on 19.04.2016.
 */
public class MyListCosts {
    private String name;
    private int price;


    public MyListCosts(String _name, int _price) {
        this.name = _name;
        this.price = _price;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(int price) {
        this.price = price;
    }


    public String getName() {
        return this.name;
    }

    public int getPrice() {
        return this.price;
    }

    public String getPriceString() {
        return String.valueOf(this.price);
    }
}
