package com.example.moneytracker.sync;

import android.content.Context;
import android.util.Log;

import com.example.moneytracker.database.model.Expenses;
import com.example.moneytracker.rest.RestService;
import com.example.moneytracker.rest.model.ExpensesModel;
import com.example.moneytracker.rest.model.UserSyncExpensesModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ExpensesSync {
    private static final String TAG  = ExpensesSync.class.getSimpleName();


    public static void synceExpenses(Context context) {
        RestService restService = new RestService();
        UserSyncExpensesModel userSyncExpensesModel = restService.syncExpenses(context,getDataSync());
        Log.d(TAG, userSyncExpensesModel.getStatus());

    }

    public static String getDataSync(){
        List<Expenses> listExp = Expenses.getAllExpenses();
        List<String> listStr = new ArrayList<>();
        ExpensesModel expenseModel = new ExpensesModel();
        Gson gson = new Gson();

        for (Expenses expense: listExp){
//            (int) expense.getId()
            expenseModel.setId((int) expense.getId());
            expenseModel.setCategoryId((int) expense.categoryGetId());
            expenseModel.setComment(expense.getDescription());
            expenseModel.setSum(Double.parseDouble(expense.getPrice()));
            expenseModel.setTrDate(expense.getDate());
            listStr.add(gson.toJson(expenseModel));
        }

        Log.d("result2", listStr.toString());
        return String.valueOf(listStr);
    }
}
