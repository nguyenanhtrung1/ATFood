package com.example.atfood.ActivityUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.atfood.Adapter.CuaHangAdapter;
import com.example.atfood.Model.CuaHang;
import com.example.atfood.Model.LoaiSp;
import com.example.atfood.R;
import com.example.atfood.Retrofit.ATFoodAPI;
import com.example.atfood.Retrofit.RetrofitClient;
import com.example.atfood.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CuaHangActivity extends AppCompatActivity {
    Toolbar toolbarCuaHang;
    RecyclerView recyclerViewCuaHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ATFoodAPI atFoodAPI;
    CuaHangAdapter cuaHangAdapter;
    List<CuaHang> arrCuaHang;
    LinearLayoutManager linearLayoutManager;
    int idLoaiSP = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cua_hang);
        initView();
        initData();
        actionToolBar();
        getData();

    }
    private void initData() {
        LoaiSp loaiSp = (LoaiSp) getIntent().getSerializableExtra("idLoaiSp");
        idLoaiSP = loaiSp.getMaloaisanpham();
    }

    private void getData() {
        compositeDisposable.add(atFoodAPI.getCuaHang1(idLoaiSP)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        cuaHangModel -> {
                            if (cuaHangModel.isSuccess()) {
                                arrCuaHang = cuaHangModel.getResult();
                                cuaHangAdapter = new CuaHangAdapter(getApplicationContext(), arrCuaHang);
                                recyclerViewCuaHang.setAdapter(cuaHangAdapter);
                                Utils.cuahang_current = cuaHangModel.getResult().get(0);
                            }
                        }
                        ,
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Error!!!!", Toast.LENGTH_LONG).show();
                        }
                ));
    }

    private void actionToolBar() {
        setSupportActionBar(toolbarCuaHang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbarCuaHang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        toolbarCuaHang = findViewById(R.id.toolbarCuaHang);
        recyclerViewCuaHang = findViewById(R.id.recycleViewCuaHang);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewCuaHang.setHasFixedSize(true);
        recyclerViewCuaHang.setLayoutManager(linearLayoutManager);
        arrCuaHang = new ArrayList<>();
        atFoodAPI = RetrofitClient.getInstance(Utils.BASE_URL).create(ATFoodAPI.class);
    }
}