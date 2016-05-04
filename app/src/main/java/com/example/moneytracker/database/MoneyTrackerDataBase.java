package com.example.moneytracker.database;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = MoneyTrackerDataBase.NAME, version = MoneyTrackerDataBase.VERSION)
public class MoneyTrackerDataBase {
    public static final String NAME = "AppDatabase";
    public static final int VERSION = 1;
}
