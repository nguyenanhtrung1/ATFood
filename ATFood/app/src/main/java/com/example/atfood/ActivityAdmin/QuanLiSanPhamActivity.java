package com.example.atfood.ActivityAdmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.atfood.Adapter.AdapterAdmin.QliCuaHangAdapter;
import com.example.atfood.Adapter.AdapterAdmin.QliSanPhamAdapter;
import com.example.atfood.Adapter.SanPhamAdapter;
import com.example.atfood.EvenBus.SuaXoaEvent;
import com.example.atfood.EvenBus.SuaXoaEventSP;
import com.example.atfood.Model.CuaHang;
import com.example.atfood.Model.SanPham;
import com.example.atfood.R;
import com.example.atfood.Retrofit.ATFoodAPI;
import com.example.atfood.Retrofit.RetrofitClient;
import com.example.atfood.Utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QuanLiSanPhamActivity extends AppCompatActivity {
    Toolbar toolbarQliSanPham;
    Button btnThem;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ATFoodAPI atFoodAPI;
    QliSanPhamAdapter sanPhamAdapter;
    List<SanPham> arrSanPham;
    RecyclerView recyclerViewQli;
    CuaHang cuaHang;
    SanPham qliSuaXoaSanPham;
    EditText edtQliSearchSP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanlisanpham);
        initView();
        ActionBar();
        initData();
        initControl();
    }
    private void initControl() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ThemSanPhamActivity.class);
                intent.putExtra("cuahang", cuaHang);
                startActivity(intent);
            }
        });
        edtQliSearchSP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() ==  0){
                    arrSanPham.clear();
                    initData();
                }else{
                    getSPSearch(s.toString());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
    }
    private void getSPSearch(String search) {
        arrSanPham.clear();
        compositeDisposable.add(atFoodAPI.timKiemSPTrongCH(search,cuaHang.getMacuahang())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanPhamModel -> {
                            if(sanPhamModel.isSuccess()){
                                arrSanPham = sanPhamModel.getResult();
                                sanPhamAdapter = new QliSanPhamAdapter(getApplicationContext(),arrSanPham);
                                recyclerViewQli.setAdapter(sanPhamAdapter);

                            }else {
                                Toast.makeText(getApplicationContext(), "Không có sản phẩm này!!!", Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                ));
    }
    private void initData() {
        compositeDisposable.add(atFoodAPI.getSanPham(cuaHang.getMacuahang())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanPhamModel -> {
                            if (sanPhamModel.isSuccess()) {
                                arrSanPham = sanPhamModel.getResult();
                                sanPhamAdapter = new QliSanPhamAdapter(getApplicationContext(), arrSanPham);
                                recyclerViewQli.setAdapter(sanPhamAdapter);
                            }
                        }
                        ,
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Error!!!!", Toast.LENGTH_LONG).show();
                        }
                ));
    }

    private void ActionBar() {
        setSupportActionBar(toolbarQliSanPham);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbarQliSanPham.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        cuaHang = (CuaHang) getIntent().getSerializableExtra("cuahang");
        toolbarQliSanPham = findViewById(R.id.toolbarQliSanPham);
        btnThem = findViewById(R.id.btnQliThemSp);
        recyclerViewQli = findViewById(R.id.recycleViewQliSanPham);
        edtQliSearchSP = findViewById(R.id.edtQliSearchSP);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewQli.setHasFixedSize(true);
        recyclerViewQli.setLayoutManager(linearLayoutManager);
        arrSanPham = new ArrayList<>();
        atFoodAPI = RetrofitClient.getInstance(Utils.BASE_URL).create(ATFoodAPI.class);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getTitle().equals("Sửa")){
            SuaCuaHang();
        }else if(item.getTitle().equals("Xoá")){
            XoaSanPham();
        }
        return super.onContextItemSelected(item);
    }

    private void XoaSanPham() {
        compositeDisposable.add(atFoodAPI.xoaSanPham(qliSuaXoaSanPham.getMasanpham())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ketQuaModel -> {
                            initData();
                            Toast.makeText(getApplicationContext(), "Xoá thành công!!!", Toast.LENGTH_SHORT).show();
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    private void SuaCuaHang() {
        Intent intent = new Intent(getApplicationContext(), ThemSanPhamActivity.class);
        intent.putExtra("sua",qliSuaXoaSanPham);
        startActivity(intent);
    }


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        initData();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void eventSuaXoa(SuaXoaEventSP event){
        if (event != null){
            qliSuaXoaSanPham = event.getSanPham();
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

}