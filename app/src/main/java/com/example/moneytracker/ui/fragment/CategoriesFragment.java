package com.example.moneytracker.ui.fragment;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.moneytracker.model.MyListCategory;
import com.example.moneytracker.R;
import com.example.moneytracker.adapter.CategoriesAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
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
