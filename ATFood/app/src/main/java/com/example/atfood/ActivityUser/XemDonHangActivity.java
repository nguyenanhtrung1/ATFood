package com.example.atfood.ActivityUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.atfood.Adapter.DonHangAdapter;
import com.example.atfood.R;
import com.example.atfood.Retrofit.ATFoodAPI;
import com.example.atfood.Retrofit.RetrofitClient;
import com.example.atfood.Utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class XemDonHangActivity extends AppCompatActivity {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ATFoodAPI atFoodAPI;
    RecyclerView recyclerViewDonhang;
    Toolbar toolbarDonHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_don_hang);
        initView();
        actionBar();
        xemDonHang();
    }

    private void xemDonHang() {
        compositeDisposable.add(atFoodAPI.xemDonHang(Utils.user_current.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        donHangModel -> {
                            DonHangAdapter donHangAdapter = new DonHangAdapter(getApplicationContext(),donHangModel.getResult());
                            recyclerViewDonhang.setAdapter(donHangAdapter);

                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                )
        );
    }

    private void actionBar() {
        setSupportActionBar(toolbarDonHang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarDonHang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        atFoodAPI = RetrofitClient.getInstance(Utils.BASE_URL).create(ATFoodAPI.class);
        toolbarDonHang = findViewById(R.id.toolbarDonHang);
        recyclerViewDonhang = findViewById(R.id.recycleViewDonHang);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewDonhang.setLayoutManager(layoutManager);
    }
}