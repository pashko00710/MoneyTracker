package com.example.moneytracker;


import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@EActivity(R.layout.activity_details_expense)
public class DetailsExpenseActivity extends AppCompatActivity {

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.US);
    Calendar calendar  = Calendar.getInstance();

    @ViewById(R.id.add_expense_date)
    EditText expenseDate;

    @AfterViews
    public void ready() {
        setTitle("Add expense");
        expenseDate.setHint(dateFormat.format(calendar.getTime()));
    }

    public void setDate(View v) {
        new DatePickerDialog(this, d,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            expenseDate.setText(dateFormat.format(calendar.getTime()));
        }
    };
}


//            Snackbar.make(rootLayout, "Snackbar in ExpensesFragment!", Snackbar.LENGTH_SHORT)
//                        .setAction("Undo", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//
//                            }
//                        })
//                        .show();