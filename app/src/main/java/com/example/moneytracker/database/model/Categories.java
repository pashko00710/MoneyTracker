package com.example.moneytracker.database.model;

import com.example.moneytracker.database.MoneyTrackerDataBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

@ModelContainer
@Table(database = MoneyTrackerDataBase.class)
public class Categories extends BaseModel {

    @PrimaryKey(autoincrement = true)
    long id;

    @Column
    String name;

    List<Expenses> expenses;

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "expenses")
    public List<Expenses> getExpenses() {
        if (expenses == null || expenses.isEmpty()) {
            expenses = SQLite.select()
                    .from(Expenses.class)
                    .where(Expenses_Table.category_id.eq(id))
                    .queryList();
        }
        return expenses;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public static List<Categories> getAllCategories() {
        return SQLite.select()
                .from(Categories.class)
                .queryList();
    }
}
