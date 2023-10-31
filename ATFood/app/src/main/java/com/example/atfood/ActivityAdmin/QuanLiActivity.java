package com.example.atfood.ActivityAdmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.atfood.R;

public class QuanLiActivity extends AppCompatActivity {
    Button btnQliDanhMuc,btnQliCuaHang,btnQliUser,btnQliDonHang,btnThongKeDH,btnThongKeDT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_li);
        initView();
        initConTrol();
    }

    private void initConTrol() {
        btnQliCuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentQliCH = new Intent(QuanLiActivity.this, QuanLiCuaHangActivity.class);
                startActivity(intentQliCH);
            }
        });
        btnQliDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentQliDH = new Intent(QuanLiActivity.this, ThongKeSanPhamActivity.class);
                startActivity(intentQliDH);
            }
        });
        btnThongKeDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentQliDH = new Intent(QuanLiActivity.this, ThongKeDonHangActivity.class);
                startActivity(intentQliDH);
            }
        });
        btnThongKeDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentQliDH = new Intent(QuanLiActivity.this, ThongKeDoanhThuActivity.class);
                startActivity(intentQliDH);
            }
        });
        btnQliDanhMuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentQliDM = new Intent(QuanLiActivity.this, QuanLiDanhMucActivity.class);
                startActivity(intentQliDM);
            }
        });
        btnQliUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentQliDM = new Intent(QuanLiActivity.this, QuanLiUserActivity.class);
                startActivity(intentQliDM);
            }
        });
    }

    private void initView() {
        btnQliDanhMuc = findViewById(R.id.btnQLiDanhMuc);
        btnQliCuaHang = findViewById(R.id.btnQliCuaHang);
        btnQliUser = findViewById(R.id.btnQLiUser);
        btnQliDonHang = findViewById(R.id.btnThongKeSP);
        btnThongKeDH = findViewById(R.id.btnThongKeDH);
        btnThongKeDT =  findViewById(R.id.btnThongKeDT);
    }
}