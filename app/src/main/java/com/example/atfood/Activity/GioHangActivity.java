package com.example.atfood.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.atfood.Adapter.GioHangAdapter;
import com.example.atfood.EvenBus.TinhTongEven;
import com.example.atfood.R;
import com.example.atfood.Utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;

public class GioHangActivity extends AppCompatActivity {
    ImageView imgGioHangTrong;
    TextView txtTongTien;
    Toolbar toolbarGioHang;
    RecyclerView recyclerViewGioHang;
    Button btnMuaHang;
    GioHangAdapter gioHangAdapter;
    long tongtiensp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        initView();
        actionBar();
        initControl();
        totalMoney();
    }

    public void totalMoney() {
        tongtiensp = 0;
        for(int i = 0; i < Utils.arrGioHang.size(); i++){
            tongtiensp = tongtiensp +  (Utils.arrGioHang.get(i).getGiasanpham()* Utils.arrGioHang.get(i).getSoluong());
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtTongTien.setText(decimalFormat.format(tongtiensp));
    }

    private void initControl() {

        if(Utils.arrGioHang.size() == 0){
            imgGioHangTrong.setVisibility(View.VISIBLE);
        }else{
            gioHangAdapter = new GioHangAdapter( getApplicationContext(), Utils.arrGioHang);
            recyclerViewGioHang.setAdapter(gioHangAdapter);
        }
        btnMuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(GioHangActivity.this, ThanhToanActivity.class);
                intent.putExtra("tongtien",tongtiensp);
                Utils.arrGioHang.clear();
                startActivity(intent);*/
            }
        });

    }
    private void actionBar() {
        setSupportActionBar(toolbarGioHang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarGioHang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void initView() {
        txtTongTien = findViewById(R.id.txtTongTien);
        imgGioHangTrong = findViewById(R.id.img_GioHangRong);
        toolbarGioHang = findViewById(R.id.toolbarGioHang);
        recyclerViewGioHang = findViewById(R.id.recycleViewGioHang);
        btnMuaHang = findViewById(R.id.btnMuaHang);
        recyclerViewGioHang.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewGioHang.setLayoutManager(layoutManager);

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void eventTinhTien(TinhTongEven even){
        if (even != null){
            totalMoney();
        }
    }
}