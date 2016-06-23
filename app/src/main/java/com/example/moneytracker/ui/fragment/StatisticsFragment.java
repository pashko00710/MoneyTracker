package com.example.moneytracker.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.widget.Toast;

import com.example.moneytracker.R;
import com.example.moneytracker.database.model.Categories;
import com.example.moneytracker.database.model.Expenses;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@EFragment(R.layout.fragment_statistics)
public class StatisticsFragment extends Fragment {
    @ViewById(R.id.pie_chart)
    PieChart pieChart;
    Random random = new Random();
    int[] colors = {Color.argb(128, random.nextInt(256), random.nextInt(256), random.nextInt(256)),
            Color.argb(128, random.nextInt(256), random.nextInt(256), random.nextInt(256)),
            Color.argb(128, random.nextInt(256), random.nextInt(256), random.nextInt(256)),
            Color.argb(128, random.nextInt(256), random.nextInt(256), random.nextInt(256)),
            Color.argb(128, random.nextInt(256), random.nextInt(256), random.nextInt(256)),
            Color.argb(128, random.nextInt(256), random.nextInt(256), random.nextInt(256)),
            Color.argb(128, random.nextInt(256), random.nextInt(256), random.nextInt(256)),
            Color.argb(128, random.nextInt(256), random.nextInt(256), random.nextInt(256)),
            Color.argb(128, random.nextInt(256), random.nextInt(256), random.nextInt(256)),
            Color.argb(128, random.nextInt(256), random.nextInt(256), random.nextInt(256))};

    @AfterViews
    public void ready() {
        setSettings();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadCategories("");
    }

    private void loadCategories(final String filter) {
        getLoaderManager().restartLoader(2, null, new LoaderManager.LoaderCallbacks<List<Categories>>() {
            @Override
            public Loader<List<Categories>> onCreateLoader(int id, Bundle args) {
                final AsyncTaskLoader<List<Categories>> loader = new AsyncTaskLoader<List<Categories>>(getActivity()) {
                    @Override
                    public List<Categories> loadInBackground() {
                        return Categories.getAllCategories(filter);
                    }
                };
                loader.forceLoad();
                return loader;
            }

            @Override
            public void onLoadFinished(Loader<List<Categories>> loader, List<Categories> data) {
                setDataChart(data);
            }

            @Override
            public void onLoaderReset(Loader<List<Categories>> loader) {

            }
        });
    }

    private void setSettings() {
        pieChart.setCenterText("Total costs:"+ '\n' + getTotal());
        pieChart.setUsePercentValues(true);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                // display msg when value selected
                if (e == null) return;
                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
        Legend l = pieChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setXEntrySpace(7);
        l.setYEntrySpace(6);

    }

    private void setDataChart(List<Categories> categories) {
        ArrayList<String> labels = new ArrayList<String>();
        ArrayList<Entry> entries = new ArrayList<>();
        for (Categories category: categories) {
            if(category.getCategoryTotal() != 0.f) {
                Log.d("Statistic2", "getCategoryTotal: "+category.getCategoryTotal());
                labels.add(category.getName());
                entries.add(new Entry(category.getCategoryTotal(), labels.size()-1));
            }
        }

        PieDataSet dataset = new PieDataSet(entries, "");
        dataset.setValueFormatter(new PercentFormatter());
        dataset.setColors(colors);

        PieData data = new PieData(labels, dataset);
        pieChart.animateY(2000);
        pieChart.setData(data);
        pieChart.highlightValues(null);
        pieChart.invalidate();
    }

    private float getTotal() {
        float total = 0.f;
        List<Expenses> listExp = Expenses.getAllExpenses();
        for (Expenses expense: listExp) {
            total += Float.valueOf(expense.getPrice());
        }
        return total;
    }

}
