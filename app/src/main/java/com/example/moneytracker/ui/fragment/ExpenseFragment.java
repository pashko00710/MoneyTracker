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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.moneytracker.R;
import com.example.moneytracker.adapter.MyAdapter;
import com.example.moneytracker.database.model.Categories;
import com.example.moneytracker.database.model.Expenses;
import com.example.moneytracker.ui.activity.DetailsExpenseActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@OptionsMenu(R.menu.search_menu)
@EFragment(R.layout.fragment_expense)
public class ExpenseFragment extends Fragment {

    @ViewById(R.id.expense_fragment_coordinatorlayout)
    CoordinatorLayout rootLayout;

    @ViewById(R.id.expense_fabBtn)
    FloatingActionButton expenseFabBtn;

    @ViewById(R.id.fragment_expense_waste)
    RecyclerView expensesListRecyclerView;

    @OptionsMenuItem(R.id.search_action)
    MenuItem menuItem;

    @Click(R.id.expense_fabBtn)
    public void fabClick() {
        DetailsExpenseActivity_.intent(this).start();
    }

    @AfterViews
    public void initExpensesRecylerView() {
        expensesListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if(Categories.getAllCategories().isEmpty()) {
            insertCategories();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadExpenses("");
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint(getString(R.string.search_title));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                loadExpenses(newText);
                return false;
            }
        });
    }

    @Background
    public void queryExpenses(String filter) {
        loadExpenses(filter);
    }

    private void loadExpenses(final String filter) {
        getLoaderManager().restartLoader(1, null, new LoaderManager.LoaderCallbacks<List<Expenses>>() {
            @Override
            public Loader<List<Expenses>> onCreateLoader(int id, Bundle args) {
                final AsyncTaskLoader<List<Expenses>> loader = new AsyncTaskLoader<List<Expenses>>(getActivity()) {
                    @Override
                    public List<Expenses> loadInBackground() {
                        return Expenses.getAllExpenses(filter);
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
