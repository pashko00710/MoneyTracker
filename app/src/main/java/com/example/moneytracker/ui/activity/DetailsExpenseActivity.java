package com.example.moneytracker.ui.activity;


import android.app.DatePickerDialog;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.moneytracker.R;
import com.example.moneytracker.adapter.SpinnerCategoriesAdapter;
import com.example.moneytracker.database.model.Categories;
import com.example.moneytracker.database.model.Expenses;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.example.moneytracker.database.model.Categories.getAllCategories;

@EActivity(R.layout.activity_details_expense)
public class DetailsExpenseActivity extends AppCompatActivity {

    private static final String EMPTY_STRING = "";

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.US);
    SpinnerCategoriesAdapter adapter;
//    Categories category;
    Calendar calendar  = Calendar.getInstance();
//    ArrayAdapter<CharSequence> adapter;

    @ViewById(R.id.detailsexpense_spinner)
    Spinner spinner;

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

    @ViewById(R.id.expense_item_category)
    TextView categorySpinner;

    private boolean errorTextInput() {
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

        if(textInputLayoutSum.getError() == EMPTY_STRING && textInputLayoutNote.getError() == EMPTY_STRING) {
            return false;
        }
        return true;
    }

    private void initAdapterSpinner() {
        adapter = new SpinnerCategoriesAdapter(this, getDataListSpinner());
        spinner.setAdapter(adapter);
    }

    private List<Categories> getDataListSpinner() {
        return getAllCategories();
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
        expenseDate.setText(dateFormat.format(calendar.getTime()));
        initAdapterSpinner();
    }

    @Click(R.id.detailsexpense_button_cancel)
    public void onClickButtonCancel() {
        onBackPressed();
    }
    @Click(R.id.detailsexpense_button_ready)
    public void onClickButtonReady() {
        if(!errorTextInput()) {
            Categories category = (Categories) spinner.getSelectedItem();
            Expenses expense = new Expenses();
            expense.setPrice(editTextSum.getText().toString());
            expense.setDate(expenseDate.getText().toString());
            expense.setDescription(editTextNote.getText().toString());
            expense.associateCategory(category);
            expense.insert();
            onBackPressed();
        }
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