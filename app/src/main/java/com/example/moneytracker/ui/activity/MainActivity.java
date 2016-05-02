package com.example.moneytracker.ui.activity;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.moneytracker.R;
import com.example.moneytracker.ui.fragment.CategoriesFragment_;
import com.example.moneytracker.ui.fragment.ExpenseFragment_;
import com.example.moneytracker.ui.fragment.SettingsFragment_;
import com.example.moneytracker.ui.fragment.StatisticsFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @ViewById(R.id.toolbar)
    Toolbar toolbar;
    @ViewById(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @ViewById(R.id.navigation_view)
    NavigationView navigationView;

    @AfterViews
    public void ready() {
//                if(savedInstanceState == null) {
//            replaceFragment(new ExpenseFragment());
//        }
        setTitle("ExpenseFragment");
        replaceFragment(new ExpenseFragment_());
        setupActionBar();
        setupDrawerLayout();
        backStack();
    }

    private void backStack() {
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {

            @Override
            public void onBackStackChanged() {
                Fragment f = getSupportFragmentManager().findFragmentById(R.id.main_container);
                if (f != null){
                    updateTitleAndDrawer (f);
                }

            }
        });
    }

    private void updateTitleAndDrawer(Fragment fragment) {
        String fragClassName = fragment.getClass().getName();

        if (fragClassName.equals(ExpenseFragment_.class.getName())) {
            setTitle("ExpenseFragment");
            navigationView.setCheckedItem(R.id.drawer_expenses);
            //set selected item position, etc
        } else if (fragClassName.equals(CategoriesFragment_.class.getName())) {
            setTitle("CategoriesFragment");
            navigationView.setCheckedItem(R.id.drawer_categories);
            //set selected item position, etc
        } else if (fragClassName.equals(StatisticsFragment_.class.getName())) {
            setTitle("StatisticsFragment");
            navigationView.setCheckedItem(R.id.drawer_statistics);
            //set selected item position, etc
        } else if (fragClassName.equals(SettingsFragment_.class.getName())) {
            setTitle("SettingsFragment");
            navigationView.setCheckedItem(R.id.drawer_settings);
            //set selected item position, etc
        }
    }

    private void replaceFragment (Fragment fragment){
        String backStateName =  fragment.getClass().getName();
        String fragmentTag = backStateName;

        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);

        if (!fragmentPopped && manager.findFragmentByTag(fragmentTag) == null){ //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.main_container, fragment, fragmentTag);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }

    private void setupActionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else if (getSupportFragmentManager().getBackStackEntryCount() == 1){
            finish();
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if (drawerLayout != null) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        switch(item.getItemId()) {
            case R.id.drawer_expenses:
                setTitle("ExpenseFragment");
                replaceFragment(new ExpenseFragment_());
                break;
            case R.id.drawer_categories:
                setTitle("CategoriesFragment");
                replaceFragment(new CategoriesFragment_());
                break;
            case R.id.drawer_statistics:
                setTitle("StatisticsFragment");
                replaceFragment(new StatisticsFragment_());
                break;
            case R.id.drawer_settings:
                setTitle("SettingsFragment");
                replaceFragment(new SettingsFragment_());
                break;
            default:
//                setTitle("ExpenseFragment");
//                replaceFragment(new ExpenseFragment_());
                break;
        }
        return true;
    }

}
