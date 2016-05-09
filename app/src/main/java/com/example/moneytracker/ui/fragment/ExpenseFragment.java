package com.example.moneytracker.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.moneytracker.R;
import com.example.moneytracker.adapter.MyAdapter;
import com.example.moneytracker.database.model.Categories;
import com.example.moneytracker.database.model.Expenses;
import com.example.moneytracker.ui.activity.DetailsExpenseActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EFragment(R.layout.fragment_expense)
public class ExpenseFragment extends Fragment {

    @ViewById(R.id.expense_fragment_coordinatorlayout)
    CoordinatorLayout rootLayout;

    @ViewById(R.id.expense_fabBtn)
    FloatingActionButton expenseFabBtn;

    @ViewById(R.id.fragment_expense_waste)
    RecyclerView expensesListRecyclerView;

//    @Bean
//    MyAdapter expensesAdapter;

    @Click(R.id.expense_fabBtn)
    public void fabClick() {
        DetailsExpenseActivity_.intent(this).start();
    }

    @AfterViews
    public void initExpensesRecylerView() {
        expensesListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if(Expenses.getAllExpenses().isEmpty()) {
            insertExpenses();
        }
        if(Categories.getAllCategories().isEmpty()) {
            insertCategories();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadExpenses();
    }

    private void loadExpenses() {
        getLoaderManager().restartLoader(1, null, new LoaderManager.LoaderCallbacks<List<Expenses>>() {
            @Override
            public Loader<List<Expenses>> onCreateLoader(int id, Bundle args) {
                final AsyncTaskLoader<List<Expenses>> loader = new AsyncTaskLoader<List<Expenses>>(getActivity()) {
                    @Override
                    public List<Expenses> loadInBackground() {
                        return Expenses.getAllExpenses();
                    }
                };
                loader.forceLoad();
                return loader;
            }
            @Override
            public void onLoadFinished(Loader<List<Expenses>> loader, List<Expenses> data) {
                expensesListRecyclerView.setAdapter(new MyAdapter(data));
            }
            @Override
            public void onLoaderReset(Loader<List<Expenses>> loader) {
//                ​loader = null;
            }
        });
    }

    private void insertExpenses() {
        Expenses expenses = new Expenses();
        expenses.setPrice("1300");
        expenses.setDescription("Cinema");
        expenses.setDate("12.05.2021");
        expenses.insert();
        expenses.setPrice("1200");
        expenses.setDescription("Car");
        expenses.setDate("10.10.10");
        expenses.insert();
    }

    private void insertCategories() {
        Categories category = new Categories();
        category.setName("Food");
        category.insert();
        category.setName("Cinema");
        category.insert();
        category.setName("Transport");
        category.insert();
    }
}
