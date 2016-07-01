package com.example.moneytracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.moneytracker.R;
import com.example.moneytracker.database.model.Categories;

import java.util.List;

public class SpinnerCategoriesAdapter extends ArrayAdapter {

    List<Categories> categories;

    public SpinnerCategoriesAdapter(Context context, List<Categories> categories) {
        super(context, 0, categories);
        this.categories = categories;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Categories category = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_categories, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.spinner_name_text);
        name.setText(category.getName());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        Categories category = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_dropdown_item, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(android.R.id.text1);
        name.setText(category.getName());

        return convertView;
    }

    @Override
    public Categories getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
