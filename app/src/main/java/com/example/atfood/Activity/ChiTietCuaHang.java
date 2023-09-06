package com.example.atfood.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.atfood.Adapter.SanPhamAdapter;
import com.example.atfood.EvenBus.SetBadgeEven;
import com.example.atfood.EvenBus.TinhTongEven;
import com.example.atfood.Model.CuaHang;
import com.example.atfood.Model.GioHang;
import com.example.atfood.Model.SanPham;
import com.example.atfood.R;
import com.example.atfood.Retrofit.ATFoodAPI;
import com.example.atfood.Retrofit.RetrofitClient;
import com.example.atfood.Utils.Utils;
import com.nex3z.notificationbadge.NotificationBadge;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ChiTietCuaHang extends AppCompatActivity {
    Toolbar toolbarChiTietCH;
    CuaHang cuaHang;
    ImageView imgChiTietCH;
    TextView txtTenCH;
    TextView txtDiaChiCH;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ATFoodAPI atFoodAPI;
    SanPhamAdapter sanPhamAdapter;
    RecyclerView recyclerViewSanPham;
    List<SanPham> arrSanPham;
    Button btnThemSanPham;
    SanPham sanPham;
    NotificationBadge badgeSoLuongSpGH;
    ImageView imgCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_cua_hang);
        initView();
        ActionBar();
        initData();
        getData();
        initConTrol();

    }

    private void initConTrol() {
        btnThemSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //themGioHang();
            }
        });
        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);
            }
        });
        setBadgeCart();
    }

    private void setBadgeCart() {
        int totalItem = 0;
        for (int i = 0; i < Utils.arrGioHang.size(); i++) {
            totalItem = totalItem + Utils.arrGioHang.get(i).getSoluong();

        }
        badgeSoLuongSpGH.setText(String.valueOf(totalItem));
    }


    private void getData() {
        compositeDisposable.add(atFoodAPI.getSanPham(cuaHang.getMacuahang())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanPhamModel -> {
                            arrSanPham = sanPhamModel.getResult();
                            sanPhamAdapter = new SanPhamAdapter(getApplicationContext(),arrSanPham);
                            recyclerViewSanPham.setAdapter(sanPhamAdapter);

                        }
                        ,
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Error!!!!", Toast.LENGTH_LONG).show();
                        }
                ));
    }

    private void initData() {
        cuaHang = (CuaHang) getIntent().getSerializableExtra("cuahang");
        txtTenCH.setText(cuaHang.getTencuahang());
        txtDiaChiCH.setText(cuaHang.getDiachi());
        Glide.with(getApplicationContext()).load(cuaHang.getHinhanh()).into(imgChiTietCH);
    }

    private void initView() {
        toolbarChiTietCH = findViewById(R.id.toolbarChiTietCH);
        txtTenCH = findViewById(R.id.txt_TenCH);
        txtDiaChiCH = findViewById(R.id.txt_DiaChiCH);
        imgChiTietCH = findViewById(R.id.img_CuaHang);
        recyclerViewSanPham = findViewById(R.id.recycleViewSpTrongCH);
        arrSanPham = new ArrayList<>();
        atFoodAPI = RetrofitClient.getInstance(Utils.BASE_URL).create(ATFoodAPI.class);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewSanPham.setHasFixedSize(true);
        recyclerViewSanPham.setLayoutManager(layoutManager);
        btnThemSanPham = findViewById(R.id.btnThayDoiGiaoHang);
        badgeSoLuongSpGH = findViewById(R.id.badge_SoLuongSP);
        if(Utils.arrGioHang != null){
            badgeSoLuongSpGH.setText(String.valueOf(Utils.arrGioHang.size()));
        }
        imgCart = findViewById(R.id.cart);
    }

    private void ActionBar() {
        setSupportActionBar(toolbarChiTietCH);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChiTietCH.setNavigationIcon(android.R.drawable.ic_media_rew);

        toolbarChiTietCH.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        int totalItem = 0;
        for (int i = 0; i < Utils.arrGioHang.size(); i++) {
            totalItem = totalItem + Utils.arrGioHang.get(i).getSoluong();

        }
        badgeSoLuongSpGH.setText(String.valueOf(totalItem));
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
    public void eventSetBadge(SetBadgeEven even){
        if (even != null){
            setBadgeCart();
        }
    }
}