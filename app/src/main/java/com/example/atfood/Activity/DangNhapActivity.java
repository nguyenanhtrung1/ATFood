package com.example.atfood.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.atfood.R;
import com.example.atfood.Utils.Utils;
import com.example.atfood.Retrofit.ATFoodAPI;
import com.example.atfood.Retrofit.RetrofitClient;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DangNhapActivity extends AppCompatActivity {
    TextView txtDangKi, txtQuenMatKhau;
    EditText edtTaiKhoan, edtMatKhau;
    AppCompatButton btnDangNhap;
    ATFoodAPI atFoodAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    boolean isLogin = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        initView();
        initControl();
    }
    private void initControl() {
        txtDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangNhapActivity.this, DangKiActivity.class);
                startActivity(intent);

            }
        });
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taiKhoan = edtTaiKhoan.getText().toString().trim();
                String matKhau = edtMatKhau.getText().toString().trim();

                if (TextUtils.isEmpty(taiKhoan)) {
                    Toast.makeText(getApplicationContext(), "Bạn Chưa Nhập Tài Khoản!!!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(matKhau)) {
                    Toast.makeText(getApplicationContext(), "Bạn Chưa Nhập Mật Khẩu!!!", Toast.LENGTH_SHORT).show();
                } else {
                    Paper.book().write("taikhoan",taiKhoan);
                    Paper.book().write("matkhau",matKhau);
                    dangNhap(taiKhoan, matKhau);
                }
            }
        });

    }

    private void initView() {
        Paper.init(this);
        txtDangKi = findViewById(R.id.txtClick_DangKi);
        edtMatKhau = findViewById(R.id.edtMatKhauDN);
        edtTaiKhoan = findViewById(R.id.edtTaiKhoanDN);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        txtQuenMatKhau = findViewById(R.id.txtClick_QuenMatKhau);
        atFoodAPI = RetrofitClient.getInstance(Utils.BASE_URL).create(ATFoodAPI.class);

        //Read data
        if(Paper.book().read("taikhoan") != null && Paper.book().read("matkhau") != null){
            edtTaiKhoan.setText(Paper.book().read("taikhoan"));
            edtMatKhau.setText(Paper.book().read("matkhau"));
            if(Paper.book().read("islogin") != null){
                boolean flag = Paper.book().read("islogin");
                if(flag){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dangNhap(Paper.book().read("taikhoan"),Paper.book().read("matkhau"));
                        }
                    },100);
                }
            }
        }
    }

    private void dangNhap(String taiKhoan, String matKhau) {
        compositeDisposable.add(atFoodAPI.dangNhap(taiKhoan, matKhau)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userModel -> {
                            if (userModel.isSuccess()){
                                isLogin = true;
                                Paper.book().write("islogin",isLogin);
                                Utils.user_current = userModel.getResult().get(0);
                                Intent intent = new Intent(DangNhapActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                )
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.user_current.getTaikhoan() != null && Utils.user_current.getMatkhau() != null) {
            edtTaiKhoan.setText(Utils.user_current.getTaikhoan());
            edtMatKhau.setText(Utils.user_current.getMatkhau());
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}