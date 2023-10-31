package com.example.atfood.ActivityAdmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.atfood.Adapter.AdapterAdmin.ThongKeDHAdapter;
import com.example.atfood.R;
import com.example.atfood.Retrofit.ATFoodAPI;
import com.example.atfood.Retrofit.RetrofitClient;
import com.example.atfood.Utils.Utils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class ThongKeDoanhThuActivity extends AppCompatActivity {
    Toolbar toolbarThongKeDoanhThu;
    BarChart barChart;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ATFoodAPI atFoodAPI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongkedoanhthu);
        initView();
        actionBar();
        setDataChart();
        getThongKeThang();
    }

    private void getThongKeThang() {
        compositeDisposable.add(atFoodAPI.thongKeTheoThang()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        thongKeDoanhSoModel -> {
                            if (thongKeDoanhSoModel.isSuccess()) {
                                List<BarEntry> arrData = new ArrayList<>();
                                for (int i = 0; i < thongKeDoanhSoModel.getResult().size();i++){
                                    String tongTien = thongKeDoanhSoModel.getResult().get(i).getTongtienhang();
                                    String thang = thongKeDoanhSoModel.getResult().get(i).getThang();
                                    arrData.add(new BarEntry(Integer.parseInt(thang),Float.parseFloat(tongTien)));
                                }
                                BarDataSet  barDataSet = new BarDataSet(arrData,"Doanh Thu ");
                                barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                                barDataSet.setValueTextSize(14f);
                                barDataSet.setValueTextColor(Color.RED);

                                BarData data = new BarData(barDataSet);
                                barChart.animateXY(2000,2000);
                                barChart.setData(data);
                                barChart.invalidate();
                            }
                        }
                        ,
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Error!!!!", Toast.LENGTH_LONG).show();
                        }
                ));

    }

    private void setDataChart() {
        barChart.getDescription().setEnabled(false);
        barChart.setDrawValueAboveBar(false);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setAxisMinimum(1);
        xAxis.setAxisMaximum(12);
        YAxis yAxisRight = barChart.getAxisRight();
        yAxisRight.setAxisMinimum(0);
        YAxis yAxisLeft = barChart.getAxisLeft();
        yAxisLeft.setAxisMinimum(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_thongke,menu);
        return super.onCreateOptionsMenu(menu);
    }

        /*@Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()){
                case R.id.ThongKeDTThang:
                    getTKThang();
                    return true;

                default:
                    return super.onOptionsItemSelected(item);
            }

        }

        private void getTKThang() {

        }*/
    private void initView() {
        barChart = findViewById(R.id.barChart);
        toolbarThongKeDoanhThu  = findViewById(R.id.toolbarThongKeDoanhThu);
        atFoodAPI = RetrofitClient.getInstance(Utils.BASE_URL).create(ATFoodAPI.class);
    }
    private void actionBar() {
        setSupportActionBar(toolbarThongKeDoanhThu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbarThongKeDoanhThu.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}