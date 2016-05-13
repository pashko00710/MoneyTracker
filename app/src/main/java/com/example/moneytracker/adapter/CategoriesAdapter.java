package com.example.moneytracker.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moneytracker.R;
import com.example.moneytracker.database.model.Categories;

import java.util.List;


public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesHolder> {
    private List<Categories> categoryList;

    class CategoriesHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public CategoriesHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.category_name);
        }


    }

    public CategoriesAdapter(List<Categories> categoryList) {
        this.categoryList = categoryList;
    }

    @Override
    public CategoriesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.category_item, parent, false);
        CategoriesHolder vh = new CategoriesHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(CategoriesHolder holder, int position) {
        Categories category = categoryList.get(position);
        holder.name.setText(category.getName());
    }

    @Override
    public int getItemCount() {
        return this.categoryList.size();
    }

}
