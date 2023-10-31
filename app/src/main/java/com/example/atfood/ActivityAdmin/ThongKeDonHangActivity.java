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
import android.widget.TextView;
import android.widget.Toast;

import com.example.atfood.Adapter.AdapterAdmin.QliCuaHangAdapter;
import com.example.atfood.Adapter.AdapterAdmin.ThongKeDHAdapter;
import com.example.atfood.Adapter.AdapterAdmin.ThongKeSPAdapter;
import com.example.atfood.Model.ModelAdmin.ThongKeDonHang;
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

public class ThongKeDonHangActivity extends AppCompatActivity {
    Toolbar toolbarTKeDonHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ATFoodAPI atFoodAPI;
    ThongKeDHAdapter thongKeDHAdapter;
    List<ThongKeDonHang> arrThongKe;
    RecyclerView recyclerViewThongKeDH;
    EditText edtMaNguoiDung;
    TextView txtTextDonHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongkedonhang);
        initView();
        actionBar();
        loadData();
        initControl();

    }

    private void initControl() {
        edtMaNguoiDung.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() ==  0){
                    arrThongKe.clear();
                    loadData();
                }else{
                    initData(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
    }
    private  void loadData(){
        compositeDisposable.add(atFoodAPI.getDonHang()
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

    }
    private void initData(String Search) {

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

    }

    private void actionBar() {
        setSupportActionBar(toolbarTKeDonHang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarTKeDonHang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        toolbarTKeDonHang = findViewById(R.id.toolbarThongKeDonHang);
        recyclerViewThongKeDH = findViewById(R.id.recycleViewThongKeDh);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewThongKeDH.setHasFixedSize(true);
        recyclerViewThongKeDH.setLayoutManager(linearLayoutManager);
        arrThongKe = new ArrayList<>();
        atFoodAPI = RetrofitClient.getInstance(Utils.BASE_URL).create(ATFoodAPI.class);
        edtMaNguoiDung = findViewById(R.id.edtMaNguoiDung);
        txtTextDonHang = findViewById(R.id.txtTextDonHang);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}