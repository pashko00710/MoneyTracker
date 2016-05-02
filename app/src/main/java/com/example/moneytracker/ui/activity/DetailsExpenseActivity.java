package com.example.moneytracker.ui.activity;


import android.app.DatePickerDialog;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;

import com.example.moneytracker.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

@EActivity(R.layout.activity_details_expense)
public class DetailsExpenseActivity extends AppCompatActivity {

    private static final String EMPTY_STRING = "";

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.US);
    Calendar calendar  = Calendar.getInstance();
    ArrayAdapter<CharSequence> adapter;

    @ViewById(R.id.detailsexpense_spinner)
    Spinner spinner;

//    @ViewById
//    ArrayAdapter<CharSequence> adapter;

    @ViewById(R.id.detailsexpense_textinputlayout_sum)
    TextInputLayout textInputLayoutSum;

    @ViewById(R.id.detailsexpense_edittext_sum)
    EditText editTextSum;

    @ViewById(R.id.detailsexpense_textinputlayout_note)
    TextInputLayout textInputLayoutNote;

    @ViewById(R.id.detailsexpense_edittext_note)
    EditText editTextNote;

    @ViewById(R.id.gridLayout)
    GridLayout gridLayout;

    @ViewById(R.id.add_expense_date)
    EditText expenseDate;

    private void errorTextInput() {
        int textLengthNote = editTextNote.getText().length();
        int textLengthSum = editTextSum.getText().length();

        if(textLengthNote == 0) {
            textInputLayoutNote.setError(getString(R.string.detailsexpense_error_note));
        } else {
            textInputLayoutNote.setError(EMPTY_STRING);
        }

        if(textLengthSum == 0) {
            textInputLayoutSum.setError(getString(R.string.detailsexpense_error_sum));
        } else {
            textInputLayoutSum.setError(EMPTY_STRING);
        }
    }

    private void initAdapterSpinner() {
        adapter = ArrayAdapter.createFromResource(DetailsExpenseActivity.this,
                R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            expenseDate.setText(dateFormat.format(calendar.getTime()));
        }
    };

    @AfterViews
    public void ready() {
        setTitle(getString(R.string.detailsexpense_titlename));
        expenseDate.setHint(dateFormat.format(calendar.getTime()));
        initAdapterSpinner();
    }

    @Click(R.id.detailsexpense_button_cancel)
    public void onClickButtonCancel() {
        onBackPressed();
    }
    @Click(R.id.detailsexpense_button_ready)
    public void onClickButtonReady() {
        Snackbar.make(gridLayout, "Clicked on ready!", Snackbar.LENGTH_SHORT)
                .setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .show();
        errorTextInput();
    }

    @Click(R.id.add_expense_date)
    public void setDate() {
        new DatePickerDialog(this, d,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
                .show();
    }

}