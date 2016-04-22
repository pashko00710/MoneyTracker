package com.example.moneytracker;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String MY_TAG = "myLogs";
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FloatingActionButton expenseFabBtn;
    private CoordinatorLayout rootLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
//    private String[] myDataset;
    ArrayList<MyListCosts> myDataset = new ArrayList<MyListCosts>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupActionBar();
        setupDrawerLayout();
        //setupRecyclerView();
        initInstances();
        if(savedInstanceState == null) {
            replaceFragment(new ExpenseFragment());
        }

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

    private void initInstances() {
        rootLayout = (CoordinatorLayout) findViewById(R.id.expense_fragment_coordinatorlayout);
        expenseFabBtn = (FloatingActionButton) findViewById(R.id.expense_fabBtn);
        expenseFabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_SHORT).show();
                Snackbar.make(rootLayout, "Hello. I am Snackbar!", Snackbar.LENGTH_SHORT)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .show();
            }
        });
    }

    private void updateTitleAndDrawer(Fragment fragment) {
        String fragClassName = fragment.getClass().getName();

        if (fragClassName.equals(ExpenseFragment.class.getName())) {
            setTitle("ExpenseFragment");
            navigationView.setCheckedItem(R.id.drawer_expenses);
            //set selected item position, etc
        } else if (fragClassName.equals(CategoriesFragment.class.getName())) {
            setTitle("CategoriesFragment");
            navigationView.setCheckedItem(R.id.drawer_categories);
            //set selected item position, etc
        } else if (fragClassName.equals(StatisticsFragment.class.getName())) {
            setTitle("StatisticsFragment");
            navigationView.setCheckedItem(R.id.drawer_statistics);
            //set selected item position, etc
        } else if (fragClassName.equals(SettingsFragment.class.getName())) {
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

//    private void setupRecyclerView() {
//        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
//
//        // use this setting to improve performance if you know that changes
//        // in content do not change the layout size of the RecyclerView
//        mRecyclerView.setHasFixedSize(true);
//
//        // use a linear layout manager
//        mLayoutManager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//
//        fillData();
//
//        // specify an adapter (see also next example)
//        mAdapter = new MyAdapter(myDataset);
//        mRecyclerView.setAdapter(mAdapter);
//    }

    private void setupActionBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupDrawerLayout() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        setTitle(getString(R.string.app_name));
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
                replaceFragment(new ExpenseFragment());
                break;
            case R.id.drawer_categories:
                setTitle("CategoriesFragment");
                replaceFragment(new CategoriesFragment());
                break;
            case R.id.drawer_statistics:
                setTitle("StatisticsFragment");
                replaceFragment(new StatisticsFragment());
                break;
            case R.id.drawer_settings:
                setTitle("SettingsFragment");
                replaceFragment(new SettingsFragment());
                break;
            default:
//                setTitle("ExpenseFragmentWaste");
//                replaceFragment(new ExpenseFragmentWaste());
                break;
        }
        return true;
    }

    // генерируем данные для адаптера
//    public void fillData() {
//        for (int i = 1; i <= 10; i++) {
//            myDataset.add(new MyListCosts("Product " + i, i * 1000));
//            myDataset.add(new MyListCosts("Cinema " + i, i * 1000));
//        }
//    }

}
