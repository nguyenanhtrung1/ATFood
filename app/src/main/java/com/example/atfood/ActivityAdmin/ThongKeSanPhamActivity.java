package com.example.atfood.ActivityAdmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.atfood.Adapter.AdapterAdmin.ThongKeSPAdapter;
import com.example.atfood.Model.ModelAdmin.ThongKeSanPham;
import com.example.atfood.R;
import com.example.atfood.Retrofit.ATFoodAPI;
import com.example.atfood.Retrofit.RetrofitClient;
import com.example.atfood.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ThongKeSanPhamActivity extends AppCompatActivity {
    Toolbar toolbarQliDonHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ATFoodAPI atFoodAPI;
    ThongKeSPAdapter thongKeSPAdapter;
    List<ThongKeSanPham> arrThongKe;
    RecyclerView recyclerViewThongKeSP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongkesanpham);
        initView();
        actionBar();
        initData();
    }

    private void initData() {
        compositeDisposable.add(atFoodAPI.thongKeSp()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        thongKeSanPhamModel -> {
                            if(thongKeSanPhamModel.isSuccess()){
                                arrThongKe = thongKeSanPhamModel.getResult();
                                thongKeSPAdapter = new ThongKeSPAdapter(getApplicationContext(),arrThongKe);
                                recyclerViewThongKeSP.setAdapter(thongKeSPAdapter);

                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    private void actionBar() {
        setSupportActionBar(toolbarQliDonHang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbarQliDonHang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        toolbarQliDonHang = findViewById(R.id.toolbarQliDonHang);
        recyclerViewThongKeSP = findViewById(R.id.recycleViewThongKeSp);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewThongKeSP.setHasFixedSize(true);
        recyclerViewThongKeSP.setLayoutManager(linearLayoutManager);
        arrThongKe = new ArrayList<>();
        atFoodAPI = RetrofitClient.getInstance(Utils.BASE_URL).create(ATFoodAPI.class);
    }
}