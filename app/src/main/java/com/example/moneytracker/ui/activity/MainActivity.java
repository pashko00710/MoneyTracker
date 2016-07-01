package com.example.moneytracker.ui.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.moneytracker.R;
import com.example.moneytracker.rest.RestService;
import com.example.moneytracker.rest.model.GoogleModel;
import com.example.moneytracker.rest.model.UserLogoutModel;
import com.example.moneytracker.sync.TrackerSyncAdapter;
import com.example.moneytracker.ui.fragment.CategoriesFragment_;
import com.example.moneytracker.ui.fragment.ExpenseFragment_;
import com.example.moneytracker.ui.fragment.SettingsFragment_;
import com.example.moneytracker.ui.fragment.StatisticsFragment_;
import com.example.moneytracker.util.ConstantManager;
import com.example.moneytracker.util.DataBaseApp;
import com.example.moneytracker.util.NetworkStatusChecker;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import retrofit.RetrofitError;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @ViewById(R.id.toolbar)
    Toolbar toolbar;
    @ViewById(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @ViewById(R.id.navigation_view)
    NavigationView navigationView;

    public String name,email,pictureUrl;
    Context context = this;

    ImageView imageView;
    TextView userName, userEmail;

    @AfterViews
    public void ready() {
        setTitle(getString(R.string.expenses));
        replaceFragment(new ExpenseFragment_());
        setupActionBar();
        setupDrawerLayout();
        backStack();

        TrackerSyncAdapter.initializeSyncAdapter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setHeaderDrawerInfo();
    }

    private void backStack() {
        getFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {

            @Override
            public void onBackStackChanged() {
                Fragment f = getFragmentManager().findFragmentById(R.id.main_container);
                if (f != null){
                    updateTitleAndDrawer (f);
                }

            }
        });
    }

    private void updateTitleAndDrawer(Fragment fragment) {
        String fragClassName = fragment.getClass().getName();

        if (fragClassName.equals(ExpenseFragment_.class.getName())) {
            setTitle(R.string.expenses);
            navigationView.setCheckedItem(R.id.drawer_expenses);
        } else if (fragClassName.equals(CategoriesFragment_.class.getName())) {
            setTitle(getString(R.string.categories));
            navigationView.setCheckedItem(R.id.drawer_categories);
        } else if (fragClassName.equals(StatisticsFragment_.class.getName())) {
            setTitle(getString(R.string.statistics));
            navigationView.setCheckedItem(R.id.drawer_statistics);
        } else if (fragClassName.equals(SettingsFragment_.class.getName())) {
            setTitle(getString(R.string.settings));
            navigationView.setCheckedItem(R.id.drawer_settings);
        }
    }

    private void replaceFragment (Fragment fragment){
        String backStateName =  fragment.getClass().getName();
        String fragmentTag = backStateName;

        FragmentManager manager = getFragmentManager();
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

        View headerLayout = navigationView.getHeaderView(0);
        imageView = (ImageView) headerLayout.findViewById(R.id.profile_image);
        userName = (TextView) headerLayout.findViewById(R.id.username_drawer_header);
        userEmail = (TextView) headerLayout.findViewById(R.id.email_drawer_header);
    }

    @Background
    public void setHeaderDrawerInfo() {
        if(!DataBaseApp.getGoogleToken(this).equalsIgnoreCase("2")) {
            RestService restService = null;
            GoogleModel googleModel = null;
            try {
                restService = new RestService();
                googleModel = restService.getJsonModel(this);
            } catch (RetrofitError e) {
                e.printStackTrace();
                return;
            }

            name = googleModel.getName();
            email = googleModel.getEmail();
            pictureUrl = googleModel.getPicture();

            setInfo();
            setPicture();
        }
    }

    @UiThread
    public void setInfo() {
        userName.setText(name);
        userEmail.setText(email);
    }

    @UiThread
    public void setPicture() {
        if(NetworkStatusChecker.isNetworkAvailable(getApplicationContext())) {
            try {
                Glide.with(context).load(pictureUrl).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else if (getFragmentManager().getBackStackEntryCount() == 1) {
            try {
                finish();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
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
                setTitle(R.string.expenses);
                replaceFragment(new ExpenseFragment_());
                break;
            case R.id.drawer_categories:
                setTitle(R.string.categories);
                replaceFragment(new CategoriesFragment_());
                break;
            case R.id.drawer_statistics:
                setTitle(R.string.statistics);
                replaceFragment(new StatisticsFragment_());
                break;
            case R.id.drawer_settings:
                setTitle(R.string.settings);
                replaceFragment(new SettingsFragment_());
                break;
            case R.id.drawer_exit:
                logoutUser();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Glide.clear(imageView);
    }

    @Background
    public void logoutUser() {
        if(!NetworkStatusChecker.isNetworkAvailable(getApplicationContext())) {
            Snackbar snackbar = Snackbar.make(drawerLayout, R.string.internet_not_connected , Snackbar.LENGTH_LONG);
            snackbar.show();
            return;
        }
        DataBaseApp.setAuthToken("");
        RestService restService = new RestService();
        UserLogoutModel userLogoutModel = restService.logout();

        switch (userLogoutModel.getStatus()) {
            case ConstantManager.STATUS_SUCCESS:
                LoginActivity_.intent(this).start();
                return;
            case ConstantManager.STATUS_ERROR :
                Snackbar.make(drawerLayout, R.string.detailsexpense_error_note, Snackbar.LENGTH_SHORT).show();
                break;
            case ConstantManager.STATUS_UNAUTHORIZED :
                LoginActivity_.intent(this).start();
                return;
            case ConstantManager.STATUS_EMPTY :
                DataBaseApp.setAuthToken("");
                LoginActivity_.intent(this).start();
                return;
        }
    }

}
