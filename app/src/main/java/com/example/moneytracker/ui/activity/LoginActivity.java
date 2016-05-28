package com.example.moneytracker.ui.activity;

import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.moneytracker.R;
import com.example.moneytracker.rest.RestService;
import com.example.moneytracker.rest.model.UserLoginModel;
import com.example.moneytracker.util.ConstantManager;
import com.example.moneytracker.util.DataBaseApp;
import com.example.moneytracker.util.NetworkStatusChecker;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;

@EActivity(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity {

//    private static final String MY_REGISTRATION = "myRegistration";
//    private static final String MY_ID = "myId";
//    public static SharedPreferences sp;
    @ViewById(R.id.login_username)
    EditText userName;
    @ViewById(R.id.login_password)
    EditText userPassword;

//    @AfterViews
//    public void ready() {
////        sp = getSharedPreferences(MY_REGISTRATION,
////                Context.MODE_PRIVATE);
////        loadLogin();
//    }

//    private void loadLogin() {
//        Log.d("Here", String.valueOf(sp.contains(MY_ID)));
//        if(sp.contains(MY_ID)) {
//            MainActivity_.intent(this).start();
//        }
//    }

    public static final String LOG_TAG = LoginActivity.class.getSimpleName();

    @Click(R.id.btnLinkToRegister)
    public void registerButton() {
        RegistrationActivity_.intent(this).start();
    }

    @Click(R.id.google_login_btn)
    public void googleButton() {
        Intent intent = AccountPicker.newChooseAccountIntent(null, null, new String[]{"com.google"},
                false, null, null, null, null);
        startActivityForResult(intent, 15);
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
        loginUser(loginView);
    }

    @Background
    public void loginUser(View regView) {
        String login = userName.getText().toString();
        String password = userPassword.getText().toString();

        RestService restService = new RestService();
        UserLoginModel userLoginModel = restService.login(login, password);

        switch (userLoginModel.getStatus()) {
            case ConstantManager.STATUS_SUCCESS :
                DataBaseApp.setAuthToken(userLoginModel.getAuthToken());
                Log.d("RegLogs", "status: " + userLoginModel.getStatus() + ", id: " + userLoginModel.getId());
                MainActivity_.intent(this).start();
                break;
            case ConstantManager.WRONG_PASSWORD:
                Snackbar.make(regView, R.string.password_wrong, Snackbar.LENGTH_SHORT).show();
                break;

            case ConstantManager.WRONG_LOGIN:
                Snackbar.make(regView, R.string.login_wrong, Snackbar.LENGTH_SHORT).show();
                break;
            default :
                Snackbar.make(regView, getString(R.string.registration_error), Snackbar.LENGTH_LONG).show();
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 15 && resultCode == RESULT_OK) {
            performLogin(data);
        }
    }

    @Background
    public void performLogin(Intent data) {
        final String accountname = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        String token = null;

        try {
            token = GoogleAuthUtil.getToken(LoginActivity.this, accountname, ConstantManager.SCOPES);
        } catch(UserRecoverableAuthException userAuthEx){
            startActivityForResult(userAuthEx.getIntent(), 10);
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        } catch (GoogleAuthException fatalAuthEx) {
            fatalAuthEx.printStackTrace();
            Log.e(LOG_TAG, "Fatal Exception " + fatalAuthEx.getLocalizedMessage());
        }

        DataBaseApp.setGoogleToken(this, token);
    }
}
