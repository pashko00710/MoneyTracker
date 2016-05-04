package com.example.moneytracker.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.moneytracker.R;
import com.example.moneytracker.adapter.CategoriesAdapter;
import com.example.moneytracker.database.model.Categories;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EFragment(R.layout.fragment_categories)
public class CategoriesFragment extends Fragment {

    @ViewById(R.id.categories_fragment_coordinatorlayout)
    CoordinatorLayout rootLayout;

    @ViewById(R.id.expense_fabBtn)
    FloatingActionButton expenseFabBtn;

    @ViewById(R.id.fragment_categories)
    RecyclerView categoriesListRecyclerView;

    @Click(R.id.categories_fabBtn)
    public void fabClick() {
//        if(getView()!= null && expenseFabBtn.isPressed()) {
        Snackbar.make(rootLayout, "Snackbar in CategoriesFragment!", Snackbar.LENGTH_SHORT)
                .setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .show();
//        }
    }

    @AfterViews
    public void initExpensesRecylerView() {
        categoriesListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if(Categories.getAllCategories().isEmpty()) {
            insertCategories();
        }
//        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(getCategories());
//        categoriesListRecyclerView.setAdapter(categoriesAdapter);
    }

//    private List<MyListCategory> getCategories() {
//        List<MyListCategory> categories = new ArrayList<>();
//        categories.add(new MyListCategory("Food"));
//        categories.add(new MyListCategory("Study"));
//        categories.add(new MyListCategory("Cloth"));
//        categories.add(new MyListCategory("Cinema"));
//        categories.add(new MyListCategory("Weapon"));
//        return categories;
//    }

    @Override
    public void onResume() {
        super.onResume();
        loadCategories();
    }

    private void loadCategories(){
        getLoaderManager().restartLoader(1, null, new LoaderManager.LoaderCallbacks<List<Categories>>() {
            @Override
            public Loader<List<Categories>> onCreateLoader(int id, Bundle args) {
                final AsyncTaskLoader<List<Categories>> loader = new AsyncTaskLoader<List<Categories>>(getActivity()) {
                    @Override
                    public List<Categories> loadInBackground() {
                        return Categories.getAllCategories();
                    }
                };
                loader.forceLoad();
                return loader;
            }

            @Override
            public void onLoadFinished(Loader<List<Categories>> loader, List<Categories> data) {
                categoriesListRecyclerView.setAdapter(new CategoriesAdapter(data));
            }

            @Override
            public void onLoaderReset(Loader<List<Categories>> loader) {

            }
        });
    }

    private void insertCategories() {
        Categories category = new Categories();
        category.setName("Food");
        category.insert();
        category.setName("Cinema");
        category.insert();
    }
}
