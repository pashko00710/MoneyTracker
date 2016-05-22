package com.example.moneytracker.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.moneytracker.R;
import com.example.moneytracker.rest.RestService;
import com.example.moneytracker.rest.model.UserRegistrationModel;
import com.example.moneytracker.util.NetworkStatusChecker;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_registration)
public class RegistrationActivity extends AppCompatActivity {

    private static final String MY_REGISTRATION = "myRegistration";
    private static final String MY_ID = "myId";

    public static SharedPreferences sp;

    @ViewById(R.id.registration_username)
    EditText userName;
    @ViewById(R.id.registration_password)
    EditText userPassword;
    @ViewById(R.id.btnLogin)
    Button loginBtn;

    @AfterViews
    public void ready() {
        setTitle("Authorization");
        sp = getSharedPreferences(MY_REGISTRATION,
                Context.MODE_PRIVATE);
        loadLogin();
    }

    @Click(R.id.btnLogin)
    public void login(View loginView) {
        View view = this.getCurrentFocus();

        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        if(!NetworkStatusChecker.isNetworkAvailable(getApplicationContext())) {
            Snackbar snackbar = Snackbar.make(loginView, "Internet is not defined", Snackbar.LENGTH_LONG);
            snackbar.show();
            return;
        }

        if(userName.length() < 5) {
            Snackbar.make(loginView, getString(R.string.registration_min_symbols_login), Snackbar.LENGTH_LONG).show();
            return;
        } else if(userPassword.length() < 5) {
            Snackbar.make(loginView, getString(R.string.registration_min_symbols_password), Snackbar.LENGTH_LONG).show();
            return;
        }
        registerUser(loginView);
    }

    @Background
    public void registerUser(View regView) {
        String login = userName.getText().toString();
        String password = userPassword.getText().toString();

        RestService restService = new RestService();
        UserRegistrationModel userRegistrationModel = restService.register(login, password);

        switch (userRegistrationModel.getStatus()) {
            case NetworkStatusChecker.STATUS_LOGIN_ALREADY :
                Snackbar.make(regView, getString(R.string.registration_busyalready), Snackbar.LENGTH_LONG).show();
                break;
            case NetworkStatusChecker.STATUS_SUCCESS :
                Log.d("RegLogs", "status: " + userRegistrationModel.getStatus() + ", id: " + userRegistrationModel.getId());
                saveLogin(userRegistrationModel);
                MainActivity_.intent(this).start();
                return;
            default :
                Snackbar.make(regView, getString(R.string.registration_error), Snackbar.LENGTH_LONG).show();
                break;
        }

    }

    public void saveLogin(UserRegistrationModel user) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(MY_ID, String.valueOf(user.getId()));
        editor.apply();
    }

    public void loadLogin() {
        Log.d("Here", String.valueOf(sp.contains(MY_ID)));
        if(sp.contains(MY_ID)) {
            MainActivity_.intent(this).start();
        }
    }
}
