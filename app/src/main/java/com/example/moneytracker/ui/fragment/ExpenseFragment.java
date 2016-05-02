package com.example.moneytracker.ui.fragment;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.moneytracker.ui.activity.DetailsExpenseActivity_;
import com.example.moneytracker.MyAdapter;
import com.example.moneytracker.model.MyListCosts;
import com.example.moneytracker.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
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
//        if(getView()!= null && expenseFabBtn.isPressed()) {
//        }
    }

    @AfterViews
    public void initExpensesRecylerView() {
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
