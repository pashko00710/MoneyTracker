package com.example.moneytracker.ui.fragment;

import android.support.v4.app.Fragment;

import com.example.moneytracker.R;
import com.example.moneytracker.ui.PieChartView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_statistics)
public class StatisticsFragment extends Fragment {
    @ViewById(R.id.pie_chart)
    PieChartView pieChart;

    private float[] dataPoints = {450, 1250, 300, 400, 300};

    @AfterViews
    public void ready() {
        pieChart.setDataPoints(dataPoints);
    }
}
