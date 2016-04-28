package com.example.moneytracker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


    private List<MyListCosts> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView description;
        public TextView price;
        public ViewHolder(View v) {
            super(v);
            description = (TextView) v.findViewById(R.id.expense_item_description);
            price = (TextView) v.findViewById(R.id.expense_item_price);
        }


    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<MyListCosts> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expense_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.description.setText(mDataset.get(position).getName());
        holder.price.setText(mDataset.get(position).getPriceString());


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
