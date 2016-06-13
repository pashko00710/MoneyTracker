package com.example.moneytracker.rest.model;

import com.google.gson.annotations.SerializedName;

public class ExpensesModel {
    @SerializedName("id")
    private Integer id;
    @SerializedName("category_id")
    private Integer categoryId;
    @SerializedName("comment")
    private String comment;
    @SerializedName("sum")
    private Double sum;
    @SerializedName("tr_date")
    private String trDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public String getTrDate() {
        return trDate;
    }

    public void setTrDate(String trDate) {
        this.trDate = trDate;
    }
}
