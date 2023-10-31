package com.example.atfood.ActivityUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.atfood.Adapter.SanPhamAdapter;
import com.example.atfood.Model.SanPham;
import com.example.atfood.R;
import com.example.atfood.Retrofit.ATFoodAPI;
import com.example.atfood.Retrofit.RetrofitClient;
import com.example.atfood.Utils.Utils;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TimKiemActivity extends AppCompatActivity {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ATFoodAPI atFoodAPI;
    Toolbar toolbarSearch;
    RecyclerView recyclerViewSearch;
    SanPhamAdapter sanPhamAdapter;
    List<SanPham> arrSanPham;
    TextInputEditText edtSearch;
    Button btnThemSPTrongCH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        ActionBar();
        initControl();
    }
    private void initControl() {
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() ==  0){
                    arrSanPham.clear();
                    sanPhamAdapter = new SanPhamAdapter(getApplicationContext(),arrSanPham);
                    recyclerViewSearch.setAdapter(sanPhamAdapter);
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
        compositeDisposable.add(atFoodAPI.timKiemSP(search)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanPhamModel -> {
                            if(sanPhamModel.isSuccess()){
                                arrSanPham = sanPhamModel.getResult();
                                sanPhamAdapter = new SanPhamAdapter(getApplicationContext(),arrSanPham);
                                recyclerViewSearch.setAdapter(sanPhamAdapter);

                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                ));
    }
    private void initView() {
        toolbarSearch = findViewById(R.id.toolbarSearch);
        recyclerViewSearch = findViewById(R.id.recycleViewSearch);
        edtSearch = findViewById(R.id.edtSearchSP);
        arrSanPham = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewSearch.setHasFixedSize(true);
        recyclerViewSearch.setLayoutManager(layoutManager);
        atFoodAPI = RetrofitClient.getInstance(Utils.BASE_URL).create(ATFoodAPI.class);
        btnThemSPTrongCH = findViewById(R.id.btnThemSPTrongCH);
    }
    private void ActionBar() {
        setSupportActionBar(toolbarSearch);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarSearch.setNavigationIcon(android.R.drawable.ic_media_rew);

        toolbarSearch.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

}