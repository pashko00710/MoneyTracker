package com.example.moneytracker.database.model;

import com.example.moneytracker.database.MoneyTrackerDataBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;

import java.util.List;

@Table(database = MoneyTrackerDataBase.class)
public class Expenses extends BaseModel {
    @PrimaryKey(autoincrement = true)
    long id;

    @Column
    String price;

    @Column
    String description;

    @Column
    String date;

    @ForeignKey
    ForeignKeyContainer<Categories> category;

    public void associateCategory(Categories category) {
        this.category = FlowManager.getContainerAdapter(Categories.class)
                .toForeignKeyContainer(category);
    }

    public Categories getCategory() {
        return category.load();
    }

    public void setPrice(String price) {
        this.price = price;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public long getId() {
        return this.id;
    }
    public String getPrice() {
        return this.price;
    }
    public String getDescription() {
        return this.description;
    }
    public String getDate() {
        return this.date;
    }

    public static List<Expenses> getAllExpenses() {
        return SQLite.select()
                .from(Expenses.class)
                .queryList();
    }
}
