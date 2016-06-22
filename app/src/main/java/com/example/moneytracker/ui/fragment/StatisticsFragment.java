package com.example.moneytracker.ui.fragment;

import android.graphics.Color;
import android.support.v4.app.Fragment;

import com.example.moneytracker.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Random;

@EFragment(R.layout.fragment_statistics)
public class StatisticsFragment extends Fragment {
    @ViewById(R.id.pie_chart)
    PieChart pieChart;

    @AfterViews
    public void ready() {
        pieChart.setCenterText("Spending by Category");
        setData();
    }

    private void setData() {
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(4, 0));
        entries.add(new Entry(8, 1));
        entries.add(new Entry(6, 2));
        entries.add(new Entry(12, 3));
        entries.add(new Entry(18, 4));
        entries.add(new Entry(9, 5));
        entries.add(new Entry(2, 6));

        PieDataSet dataset = new PieDataSet(entries, "# of Calls");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");
        labels.add("October");

        PieData data = new PieData(labels, dataset);
        Random random = new Random();
        dataset.setColors(new int[]{Color.argb(128, random.nextInt(256), random.nextInt(256), random.nextInt(256)),
                Color.argb(128, random.nextInt(256), random.nextInt(256), random.nextInt(256)),
                Color.argb(128, random.nextInt(256), random.nextInt(256), random.nextInt(256)),
                Color.argb(128, random.nextInt(256), random.nextInt(256), random.nextInt(256)),
                Color.argb(128, random.nextInt(256), random.nextInt(256), random.nextInt(256)),
                Color.argb(128, random.nextInt(256), random.nextInt(256), random.nextInt(256)),
                Color.argb(128, random.nextInt(256), random.nextInt(256), random.nextInt(256))}); //
        pieChart.setDescription("Description");
        pieChart.setData(data);

        pieChart.animateY(2800);
    }

}
