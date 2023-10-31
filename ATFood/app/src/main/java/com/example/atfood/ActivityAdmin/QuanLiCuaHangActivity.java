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
import com.example.atfood.Adapter.SanPhamAdapter;
import com.example.atfood.EvenBus.SuaXoaEvent;
import com.example.atfood.Model.CuaHang;
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

public class QuanLiCuaHangActivity extends AppCompatActivity {
    Toolbar toolbarQliCuaHang;
    Button btnThem;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ATFoodAPI atFoodAPI;
    QliCuaHangAdapter qliCuaHangAdapter;
    List<CuaHang> arrCuaHang;
    RecyclerView recyclerViewQli;
    CuaHang qliSuaXoacuaHang;
    EditText edtQliSearchCH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanlicuahang);
        initView();
        ActionBar();
        initData();
        initControl();
    }

    private void initControl() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ThemCuaHangActivity.class);
                startActivity(intent);
            }
        });
        edtQliSearchCH.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() ==  0){
                    arrCuaHang.clear();
                    initData();
                }else{
                    getCHSearch(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
    }
    private void getCHSearch(String search) {
        arrCuaHang.clear();
        compositeDisposable.add(atFoodAPI.timKiemCH(search)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        cuaHangModel -> {
                            if(cuaHangModel.isSuccess()){
                                arrCuaHang = cuaHangModel.getResult();
                                qliCuaHangAdapter = new QliCuaHangAdapter(getApplicationContext(),arrCuaHang);
                                recyclerViewQli.setAdapter(qliCuaHangAdapter);

                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                ));
    }
    private void initData() {
        compositeDisposable.add(atFoodAPI.getQliCuaHang()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        cuaHangModel -> {
                            if (cuaHangModel.isSuccess()) {
                                arrCuaHang = cuaHangModel.getResult();
                                qliCuaHangAdapter = new QliCuaHangAdapter(getApplicationContext(), arrCuaHang);
                                recyclerViewQli.setAdapter(qliCuaHangAdapter);
                            }
                        }
                        ,
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Error!!!!", Toast.LENGTH_LONG).show();
                        }
                ));
    }

    private void ActionBar() {
        setSupportActionBar(toolbarQliCuaHang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbarQliCuaHang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        edtQliSearchCH = findViewById(R.id.edtQliSearchCH);
        toolbarQliCuaHang = findViewById(R.id.toolbarQliCuaHang);
        btnThem = findViewById(R.id.btnQliThemCh);
        recyclerViewQli = findViewById(R.id.recycleViewQliCuaHang);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewQli.setHasFixedSize(true);
        recyclerViewQli.setLayoutManager(linearLayoutManager);
        arrCuaHang = new ArrayList<>();
        atFoodAPI = RetrofitClient.getInstance(Utils.BASE_URL).create(ATFoodAPI.class);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getTitle().equals("Sửa")){
            SuaCuaHang();
        }else if(item.getTitle().equals("Xoá")){
            XoaCuaHang();
        }
        return super.onContextItemSelected(item);
    }

    private void XoaCuaHang() {
        compositeDisposable.add(atFoodAPI.xoaCuaHang(qliSuaXoacuaHang.getMacuahang())
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
        Intent intent = new Intent(getApplicationContext(), ThemCuaHangActivity.class);
        intent.putExtra("sua",qliSuaXoacuaHang);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
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
    public void eventSuaXoa(SuaXoaEvent event){
        if (event != null){
            qliSuaXoacuaHang = event.getCuaHang();
        }
    }
}