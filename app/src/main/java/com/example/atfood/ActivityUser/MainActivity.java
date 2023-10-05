package com.example.atfood.ActivityUser;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.atfood.Adapter.ChucNangAdapter;
import com.example.atfood.Adapter.LoaiSpAdapter;
import com.example.atfood.Model.ChucNang;
import com.example.atfood.Model.LoaiSp;
import com.example.atfood.Model.User;
import com.example.atfood.R;
import com.example.atfood.Retrofit.ATFoodAPI;
import com.example.atfood.Retrofit.RetrofitClient;
import com.example.atfood.Utils.Utils;
import com.google.android.material.navigation.NavigationView;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    NavigationView navigationView;
    ViewFlipper viewFlipper;
    ListView listViewTrangChu;
    DrawerLayout drawerLayout;
    List<ChucNang> arrChucNang;
    ChucNangAdapter chucNangAdapter;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ATFoodAPI atFoodAPI;
    RecyclerView recycleViewTrangChu;
    List<LoaiSp> arrLoaiSp;
    LoaiSpAdapter loaiSpAdapter;
    ImageView cart,imgSearch;
    NotificationBadge badgeSoLuongSp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        ActionBar();
        ActionViewFlipper();
        initControl();
    }

    private void initControl() {
        if(isConnected(this)){
            LoadChucNang();
            LoadLoaiSp();
            Searh();
            cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
                    startActivity(intent);
                }
            });
        }
        else {
            Toast.makeText(this, "Không có kết nối interner!!! ", Toast.LENGTH_SHORT).show();
        }
        listViewTrangChu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent intentThongTinTk = new Intent(getApplicationContext(), ThongTinTaiKhoanActivity.class);
                        startActivity(intentThongTinTk);
                        break;
                    case 1:
                        Intent xemDonHang = new Intent(getApplicationContext(), XemDonHangActivity.class);
                        startActivity(xemDonHang);
                        break;
                    case 2:
                        Paper.book().delete("user");
                        Intent dangXuat = new Intent(getApplicationContext(), DangNhapActivity.class);
                        startActivity(dangXuat);
                        break;

                }
            }
        });
    }

    private void LoadLoaiSp() {
        compositeDisposable.add(atFoodAPI.getLoaiSp()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        loaiSpModel -> {
                            if(loaiSpModel.isSuccess()){
                                arrLoaiSp = loaiSpModel.getResult();
                                loaiSpAdapter = new LoaiSpAdapter(getApplicationContext(), arrLoaiSp);
                                recycleViewTrangChu.setAdapter(loaiSpAdapter);
                            }

                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Không thể kết nối được Server", Toast.LENGTH_LONG).show();
                        }
                )
        );
    }

    private void Searh() {
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TimKiemActivity.class);
                startActivity(intent);

            }
        });
    }

    private void LoadChucNang() {
        compositeDisposable.add(atFoodAPI.getChucNang()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        chucNangModel -> {
                            if(chucNangModel.isSuccess()){
                                arrChucNang = chucNangModel.getResult();
                                chucNangAdapter  =new ChucNangAdapter(this,arrChucNang);
                                listViewTrangChu.setAdapter(chucNangAdapter);
                            }
                        }

                ));
    }
    private boolean isConnected(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
                return true;
            }
        }
        return false;
    }
    private void initView() {
        drawerLayout = findViewById(R.id.drawerlayoutTrangChu);
        toolbar = findViewById(R.id.toolbarTrangChu);
        navigationView = findViewById(R.id.navigationViewTrangChu);
        listViewTrangChu = findViewById(R.id.listviewTrangChu);
        viewFlipper = findViewById(R.id.viewLipper);
        imgSearch = findViewById(R.id.img_Search);
        recycleViewTrangChu = findViewById(R.id.recycleViewTrangChu);
        badgeSoLuongSp = findViewById(R.id.badge_SoLuongSP);
        cart = findViewById(R.id.cart);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 4);
        recycleViewTrangChu.setLayoutManager(layoutManager);
        arrLoaiSp = new ArrayList<>();
        arrChucNang = new ArrayList<>();
        atFoodAPI = RetrofitClient.getInstance(Utils.BASE_URL).create(ATFoodAPI.class);
        if(Utils.arrGioHang == null){
            Utils.arrGioHang = new ArrayList<>();
        }else{
            int totalItem = 0;
            for (int i = 0; i < Utils.arrGioHang.size(); i++) {
                totalItem = totalItem + Utils.arrGioHang.get(i).getSoluong();

            }
            badgeSoLuongSp.setText(String.valueOf(totalItem));
        }
        Paper.init(this);
        if(Paper.book().read("user")!= null){
            User user = Paper.book().read("user");
            Utils.user_current = user;
        }
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
    private void ActionViewFlipper() {
        List<String> arrQuanCao = new ArrayList<>();
        arrQuanCao.add("https://img.freepik.com/free-vector/flat-design-food-facebook-cover_23-2149108159.jpg?w=1380&t=st=1693427770~exp=1693428370~hmac=fd07cc691a4a9d811c65272fd52089a91f14c430efb594785a124887257bfe0f");
        arrQuanCao.add("https://img.freepik.com/free-vector/flat-design-food-sale-background_23-2149167390.jpg?w=1380&t=st=1693427802~exp=1693428402~hmac=c40586c1723baa98526054b334c05355a420c6ea1135442ca2fb6b17b06a6657");
        arrQuanCao.add("https://img.freepik.com/free-vector/flat-design-food-landing-page_23-2149126180.jpg?w=1060&t=st=1693427824~exp=1693428424~hmac=dddb40e0c025065a093965b83f3d7300c8a4d237441dc01eb5f9b6ad5bf281b6");
        for (int i = 0; i < arrQuanCao.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(arrQuanCao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(30000);
        viewFlipper.setAutoStart(true);
        Animation Slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation Slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(Slide_in);
        viewFlipper.setOutAnimation(Slide_out);
    }
    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        int totalItem = 0;
        for (int i = 0; i < Utils.arrGioHang.size(); i++) {
            totalItem = totalItem + Utils.arrGioHang.get(i).getSoluong();

        }
        badgeSoLuongSp.setText(String.valueOf(totalItem));
    }
}