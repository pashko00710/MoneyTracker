package com.example.moneytracker.ui.fragment;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.moneytracker.R;
import com.example.moneytracker.database.model.Categories;
import com.example.moneytracker.database.model.Expenses;
import com.github.mikephil.charting.charts.PieChart;
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
import java.util.Locale;
import java.util.Random;

@EFragment(R.layout.fragment_statistics)
public class StatisticsFragment extends Fragment {
    @ViewById(R.id.pie_chart)
    PieChart pieChart;

    int screenWidth, screenHeight;
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
        getDisplayParams();
        setPieChartParams();
    }

    private CharSequence setCenterText() {
        SpannableString title = new SpannableString(getString(R.string.statistics_total));
        SpannableString inTotal = new SpannableString(String.valueOf(this.getTotal()));
        float relativeSizeSpanInTotal ,relativeSizeSpanTitle;

        if(getTotal() > 150000) {
            relativeSizeSpanInTotal = 1.6f;
            relativeSizeSpanTitle = 1.7f;
        } else {
            relativeSizeSpanInTotal = 1.8f;
            relativeSizeSpanTitle = 1.9f;
        }

        title.setSpan(new RelativeSizeSpan(relativeSizeSpanTitle), 0, title.length(), 0);
        title.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 0, title.length(), 0);

        inTotal.setSpan(new RelativeSizeSpan(relativeSizeSpanInTotal), 0, inTotal.length(), 0);
        inTotal.setSpan(new StyleSpan(Typeface.ITALIC), 0, inTotal.length(), 0);

        return TextUtils.concat(title,inTotal);
    }

    private void getDisplayParams() {
        DisplayMetrics metrics = new DisplayMetrics();
        ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
    }

    private void setPieChartParams() {
        pieChart.setCenterText(setCenterText());
        pieChart.setDescription(getString(R.string.statistics_distrib));
        pieChart.setDescriptionPosition(1080,screenHeight-256);
        pieChart.setDescriptionColor(Color.parseColor("#00E5FF"));
        pieChart.setDescriptionTextSize(18f);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setUsePercentValues(true);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);
        pieChart.setTransparentCircleRadius(58f);
    }

    private void setDataChart(List<Categories> categories) {
        final ArrayList<String> labels = new ArrayList<String>();
        ArrayList<Entry> entries = new ArrayList<>();
        for (Categories category: categories) {
            if(category.getCategoryTotal() != 0.f) {
                labels.add(category.getName());
                entries.add(new Entry(category.getCategoryTotal(), labels.size()-1));
            }
        }

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                if (e == null) return;
                Toast.makeText(getActivity(), String.format(Locale.getDefault(),
                        "%s %n %.1f$",
                        labels.get(e.getXIndex()),
                        e.getVal()), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

        PieDataSet dataset = new PieDataSet(entries, "");
        dataset.setValueFormatter(new PercentFormatter());
        dataset.setSliceSpace(2.5f);
        dataset.setSelectionShift(5f);
        dataset.setColors(colors);

        PieData data = new PieData(labels, dataset);
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        pieChart.getLegend().setEnabled(false); // убирает подписи категорий снизу
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
