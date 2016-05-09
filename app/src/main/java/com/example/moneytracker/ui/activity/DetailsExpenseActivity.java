package com.example.moneytracker.ui.activity;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import com.example.moneytracker.R;
import com.example.moneytracker.adapter.SpinnerCategoriesAdapter;
import com.example.moneytracker.database.MoneyTrackerDataBase;
import com.example.moneytracker.database.model.Categories;
import com.example.moneytracker.database.model.Expenses;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

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
public class DetailsExpenseActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Categories>> {

    private static final String EMPTY_STRING = "";

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.US);
    SpinnerCategoriesAdapter adapter;
    Categories categories;
    Calendar calendar  = Calendar.getInstance();

    @ViewById(R.id.detailsexpense_spinner)
    AppCompatSpinner spinner;

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
            saveExpense();
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


    @Override
    public Loader<List<Categories>> onCreateLoader(int id, Bundle args) {
        final AsyncTaskLoader<List<Categories>> loader = new AsyncTaskLoader<List<Categories>>(this) {
            @Override
            public List<Categories> loadInBackground() {
                return Categories.getAllCategories();
            }
        };
        loader.forceLoad();
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<Categories>> loader, List<Categories> data) {
    }

    @Override
    public void onLoaderReset(Loader<List<Categories>> loader) {

    }

    private void saveExpense() {

        Expenses expense = new Expenses();

        DatabaseDefinition database = FlowManager.getDatabase(MoneyTrackerDataBase.class);

        ProcessModelTransaction<Expenses> processModelTransaction =
                new ProcessModelTransaction.Builder<>(new ProcessModelTransaction.ProcessModel<Expenses>() {
                    @Override
                    public void processModel(Expenses expense) {
                        Categories category = (Categories) spinner.getSelectedItem();
                        expense.setPrice(editTextSum.getText().toString());
                        expense.setDate(expenseDate.getText().toString());
                        expense.setDescription(editTextNote.getText().toString());
                        expense.associateCategory(category);
                        expense.save();
                    }
                }).processListener(new ProcessModelTransaction.OnModelProcessListener<Expenses>() {
                    @Override
                    public void onModelProcessed(long current, long total, Expenses modifiedModel) {

                    }
                }).addAll(expense).build();

        Transaction transaction = database.beginTransactionAsync(processModelTransaction)
                .success(new Transaction.Success() {
                    @Override
                    public void onSuccess(Transaction transaction) {
                        Log.d("Here1", "Normal");
                        finish();
                    }
                })
                .error(new Transaction.Error() {
                    @Override
                    public void onError(Transaction transaction, Throwable error) {
                        Log.d("Here2", "Not Normal");
                    }
                }).build();

        transaction.execute();

    }

}