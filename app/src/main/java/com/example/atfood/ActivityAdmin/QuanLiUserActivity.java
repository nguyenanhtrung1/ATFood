package com.example.atfood.ActivityAdmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atfood.Adapter.AdapterAdmin.QliUserAdapter;
import com.example.atfood.Adapter.AdapterAdmin.ThongKeDHAdapter;
import com.example.atfood.Model.ModelAdmin.ThongKeDonHang;
import com.example.atfood.Model.User;
import com.example.atfood.R;
import com.example.atfood.Retrofit.ATFoodAPI;
import com.example.atfood.Retrofit.RetrofitClient;
import com.example.atfood.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QuanLiUserActivity extends AppCompatActivity {
    Toolbar toolbarTKeUser;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ATFoodAPI atFoodAPI;
    QliUserAdapter qliUserAdapter;
    List<User> arrUser;
    RecyclerView recyclerViewQliUser;
    EditText edtTaiKhoan;
    TextView edtMatKhau;
    Spinner spinnerVaiTroUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanliuser);
        initView();
        actionBar();
        //loadData();
        initControl();
    }
    private void initControl() {

    }
    /*private  void loadData(){
        compositeDisposable.add(atFoodAPI.get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        thongKeDonHangModel -> {
                            if (thongKeDonHangModel.isSuccess()) {
                                arrThongKe = thongKeDonHangModel.getResult();
                                thongKeDHAdapter = new ThongKeDHAdapter(getApplicationContext(),arrThongKe);
                                recyclerViewThongKeDH.setAdapter(thongKeDHAdapter);
                            }
                        }
                        ,
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Error!!!!", Toast.LENGTH_LONG).show();
                        }
                ));

    }*/
    /*private void initData(String Search) {

        compositeDisposable.add(atFoodAPI.thongKeDh(Search)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        thongKeDonHangModel -> {
                            if(thongKeDonHangModel.isSuccess()){
                                arrThongKe = thongKeDonHangModel.getResult();
                                thongKeDHAdapter = new ThongKeDHAdapter(getApplicationContext(),arrThongKe);
                                recyclerViewThongKeDH.setAdapter(thongKeDHAdapter);

                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                ));

    }*/

    private void actionBar() {
        setSupportActionBar(toolbarTKeUser);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarTKeUser.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        toolbarTKeUser = findViewById(R.id.toolbarThongKeDonHang);
        recyclerViewQliUser = findViewById(R.id.recycleViewQliUser);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewQliUser.setHasFixedSize(true);
        recyclerViewQliUser.setLayoutManager(linearLayoutManager);
        arrUser = new ArrayList<>();
        atFoodAPI = RetrofitClient.getInstance(Utils.BASE_URL).create(ATFoodAPI.class);
        edtTaiKhoan = findViewById(R.id.edtQliTaiKhoan);
        edtMatKhau = findViewById(R.id.edtQliMatKhau);
        spinnerVaiTroUser = findViewById(R.id.spinner_VaiTroUser);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}