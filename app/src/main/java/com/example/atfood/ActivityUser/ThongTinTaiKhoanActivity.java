package com.example.atfood.ActivityUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.atfood.R;
import com.example.atfood.Utils.Utils;

public class ThongTinTaiKhoanActivity extends AppCompatActivity {
    TextView txtTenNguoiDung, txtTenTaiKhoan;
    ImageView imgAvatar;
    Toolbar toolbarHoSo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_tai_khoan);
        initView();
        initData();
        actionBar();
    }

    private void actionBar() {
        setSupportActionBar(toolbarHoSo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbarHoSo.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        txtTenNguoiDung.setText(Utils.user_current.getTennguoidung());
        txtTenTaiKhoan.setText(Utils.user_current.getTaikhoan());
    }

    private void initView() {
        txtTenNguoiDung = findViewById(R.id.txtTenNguoiDung);
        txtTenTaiKhoan = findViewById(R.id.txtTenTaiKhoan);
        imgAvatar = findViewById(R.id.imgAvatar);
        toolbarHoSo = findViewById(R.id.toolbarThongTinTaiKhoan);
    }
}