package com.example.moneytracker.ui.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.moneytracker.R;
import com.example.moneytracker.rest.RestService;
import com.example.moneytracker.rest.model.GoogleModel;
import com.example.moneytracker.ui.fragment.CategoriesFragment_;
import com.example.moneytracker.ui.fragment.ExpenseFragment_;
import com.example.moneytracker.ui.fragment.SettingsFragment_;
import com.example.moneytracker.ui.fragment.StatisticsFragment_;
import com.example.moneytracker.util.DataBaseApp;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @ViewById(R.id.toolbar)
    Toolbar toolbar;
    @ViewById(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @ViewById(R.id.navigation_view)
    NavigationView navigationView;
//    @ViewById(R.id.profile_image)
//    ImageView imageView;

    public String name,email,pictureUrl;
    Context context = this;

    @AfterViews
    public void ready() {
        setTitle("ExpenseFragment");
        replaceFragment(new ExpenseFragment_());
        setupActionBar();
        setupDrawerLayout();
        backStack();
        setHeaderDrawerInfo();
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
        } else if (fragClassName.equals(CategoriesFragment_.class.getName())) {
            setTitle("CategoriesFragment");
            navigationView.setCheckedItem(R.id.drawer_categories);
        } else if (fragClassName.equals(StatisticsFragment_.class.getName())) {
            setTitle("StatisticsFragment");
            navigationView.setCheckedItem(R.id.drawer_statistics);
        } else if (fragClassName.equals(SettingsFragment_.class.getName())) {
            setTitle("SettingsFragment");
            navigationView.setCheckedItem(R.id.drawer_settings);
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

    @Background
    public void setHeaderDrawerInfo() {
        if(!DataBaseApp.getGoogleToken(this).equalsIgnoreCase("2")) {
            RestService restService = new RestService();
            GoogleModel googleModel = restService.getJsonModel(this);

            name = googleModel.getName();
            email = googleModel.getEmail();
            pictureUrl = googleModel.getPicture();
            Log.d("GoogleModel", name);

            setInfo();
            setPicture();
        }
    }

    @UiThread
    public void setInfo() {
        TextView userName = (TextView)findViewById(R.id.username_drawer_header);
        TextView userEmail = (TextView)findViewById(R.id.email_drawer_header);
        userName.setText(name);
        userEmail.setText(email);
    }

    @UiThread
    public void setPicture() {
        final ImageView imageView = (ImageView)findViewById(R.id.profile_image);
        Glide.with(context).load(pictureUrl).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageView) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    imageView.setImageDrawable(circularBitmapDrawable);
                }
            });
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
                break;
        }
        return true;
    }

}
