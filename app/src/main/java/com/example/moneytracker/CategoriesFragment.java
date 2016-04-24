package com.example.moneytracker;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView =  inflater.inflate(R.layout.fragment_categories, container, false);
        CoordinatorLayout rootLayout = (CoordinatorLayout) fragmentView.findViewById(R.id.categories_fragment_coordinatorlayout);
        FloatingActionButton expenseFabBtn = (FloatingActionButton) fragmentView.findViewById(R.id.categories_fabBtn);

        initInstances(rootLayout ,expenseFabBtn);

        this.initExpensesRecylerView(fragmentView);
        return fragmentView;
    }

    private void initInstances(final CoordinatorLayout rootLayout, FloatingActionButton expenseFabBtn) {
        expenseFabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(rootLayout, "Snackbar in CategoriesFragment!", Snackbar.LENGTH_SHORT)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .show();
            }
        });
    }

    private void initExpensesRecylerView(View fragmentView) {
        RecyclerView categoriesListRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.fragment_categories);
        categoriesListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(getCategories());
        categoriesListRecyclerView.setAdapter(categoriesAdapter);
    }

    private List<MyListCategory> getCategories() {
        List<MyListCategory> categories = new ArrayList<>();
        categories.add(new MyListCategory("Food"));
        categories.add(new MyListCategory("Study"));
        categories.add(new MyListCategory("Cloth"));
        categories.add(new MyListCategory("Cinema"));
        categories.add(new MyListCategory("Weapon"));
        return categories;
    }
}
