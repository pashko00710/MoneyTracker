package com.example.moneytracker.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.moneytracker.R;
import com.example.moneytracker.database.model.Expenses;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MyAdapter extends SelectableAdapter<MyAdapter.ViewHolder> {


    private List<Expenses> mDataset;
    private ClickListener clickListener;
    private Context context;
    private int lastPosition = -1;

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(Context context, List<Expenses> myDataset, ClickListener clickListener) {
        this.context = context;
        mDataset = myDataset;
        this.clickListener = clickListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expense_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v, clickListener);
        return vh;
    }

    private void setAnimation(View view, int position) {
        if (position > lastPosition){
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_up);
            view.startAnimation(animation);
            lastPosition = position;
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Expenses expense = mDataset.get(position);
        holder.description.setText(expense.getDescription());
        holder.date.setText(expense.getDate());
        holder.category.setText(expense.getCategory().getName());
        holder.price.setText(expense.getPrice());
        setAnimation(holder.cardView, position);
        holder.view.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void refresh(List<Expenses> data) {
        mDataset.clear();
        mDataset.addAll(data);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        if (mDataset.get(position) != null) {
            mDataset.get(position).delete();
            mDataset.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void removeItems(List<Integer> positions) {

        Collections.sort(positions, new Comparator<Integer>() {
            @Override
            public int compare(Integer lhs, Integer rhs) {
                return rhs - lhs;
            }
        });

        while (!positions.isEmpty()) {
            removeItem(positions.get(0));
            positions.remove(0);
        }

    }

    private void removeRange(int positionStart, int itemCount) {
        for (int position = 0; position < itemCount; position++){
            removeExpenses(positionStart);
        }
        notifyItemRangeRemoved(positionStart, itemCount);
    }

    private void removeExpenses(int position){
        if (mDataset.get(position)!= null){
            mDataset.get(position).delete();
            mDataset.remove(position);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
        View view;
        public TextView description;
        public TextView price;
        public TextView date;
        public TextView category;
        CardView cardView;
        private ClickListener clickListener;
        public ViewHolder(View v, ClickListener clickListener) {
            super(v);
            cardView = (CardView) v.findViewById(R.id.card_view);
            view = v.findViewById(R.id.selected_overlay);
            description = (TextView) v.findViewById(R.id.expense_item_description);
            price = (TextView) v.findViewById(R.id.expense_item_price);
            date = (TextView) v.findViewById(R.id.expense_item_date);
            category = (TextView) v.findViewById(R.id.expense_item_category);

            this.clickListener = clickListener;

            v.setOnClickListener(this);
            v.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.onItemClick(getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (clickListener != null) {
                clickListener.onItemLongClick(getAdapterPosition());
                return true;
            } else {
                return false;
            }
        }
    }


}
