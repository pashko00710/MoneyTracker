package com.example.moneytracker.sync;

import android.content.Context;
import android.util.Log;

import com.example.moneytracker.database.model.Categories;
import com.example.moneytracker.rest.RestService;
import com.example.moneytracker.rest.model.UserData;
import com.example.moneytracker.rest.model.UserSyncCategoriesModel;
import com.example.moneytracker.util.ConstantManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class CategoriesSync {
    private static final String TAG  = CategoriesSync.class.getSimpleName();

    public static boolean syncCategories(Context context){

        RestService restService = new RestService();
        UserSyncCategoriesModel userSyncCategoriesModel = restService.syncCategories(context, getDataSync());
        Log.d(TAG, userSyncCategoriesModel.getStatus());

        if(!userSyncCategoriesModel.getStatus().equals(ConstantManager.STATUS_SUCCESS)) return false;

        return true;

    }

    public static String getDataSync(){
        List<Categories> listCat = Categories.getAllCategories();
        List<String> listStr = new ArrayList<>();
        UserData data = new UserData();
        Gson gson = new Gson();

        for (Categories category: listCat){
            data.setId(0); // в таком случае только и работает..
            data.setTitle(category.getName());
            listStr.add(gson.toJson(data));
        }

        Log.d("result1", String.valueOf(listStr));
        return String.valueOf(listStr);
    }
}
