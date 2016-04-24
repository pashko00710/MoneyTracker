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

public class ExpenseFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView =  inflater.inflate(R.layout.fragment_expense, container, false);
        CoordinatorLayout rootLayout = (CoordinatorLayout) fragmentView.findViewById(R.id.expense_fragment_coordinatorlayout);
        FloatingActionButton expenseFabBtn = (FloatingActionButton) fragmentView.findViewById(R.id.expense_fabBtn);

        initInstances(rootLayout ,expenseFabBtn);

        this.initExpensesRecylerView(fragmentView);
        return fragmentView;
    }

    private void initInstances(final CoordinatorLayout rootLayout, FloatingActionButton expenseFabBtn) {
        expenseFabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(rootLayout, "Snackbar in ExpensesFragment!", Snackbar.LENGTH_SHORT)
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
        RecyclerView expensesListRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.fragment_expense_waste);
        expensesListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        MyAdapter expensesAdapter = new MyAdapter(getExpenses());
        expensesListRecyclerView.setAdapter(expensesAdapter);
    }

    private List<MyListCosts> getExpenses() {
        List<MyListCosts> expenses = new ArrayList<>();
        expenses.add(new MyListCosts("Food", 1000));
        expenses.add(new MyListCosts("Study", 2000));
        expenses.add(new MyListCosts("Cinema", 3000));
        expenses.add(new MyListCosts("Cloth", 4000));
        expenses.add(new MyListCosts("Weapon", 5000));
        return expenses;
    }
}
