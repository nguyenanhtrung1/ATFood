package com.example.atfood.ActivityAdmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.atfood.Adapter.AdapterAdmin.QliCuaHangAdapter;
import com.example.atfood.Adapter.AdapterAdmin.QliDanhMucAdapter;
import com.example.atfood.Adapter.LoaiSpAdapter;
import com.example.atfood.EvenBus.SuaXoaEvent;
import com.example.atfood.EvenBus.SuaXoaEventDM;
import com.example.atfood.Model.CuaHang;
import com.example.atfood.Model.KetQuaModel;
import com.example.atfood.Model.LoaiSp;
import com.example.atfood.R;
import com.example.atfood.Retrofit.ATFoodAPI;
import com.example.atfood.Retrofit.RetrofitClient;
import com.example.atfood.Utils.Utils;
import com.github.dhaval2404.imagepicker.ImagePicker;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuanLiDanhMucActivity extends AppCompatActivity {
    Toolbar toolbarQliDanhMuc;
    Button btnThem;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ATFoodAPI atFoodAPI;
    QliDanhMucAdapter loaiSpAdapter;
    List<LoaiSp> arrLoaiSP;
    RecyclerView recyclerViewQli;
    LoaiSp qliSuaXoaDanhMucSP;
    EditText edtTenDanhMuc,edtHinhAnhDM;
    ImageView imgThemHinhAnh;
    String mediaPath;
    boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_li_danh_muc);
        initView();
        ActionBar();
        initData();
        initControl();
    }

    private void initControl() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == false){
                    themDanhMuc();
                }else {
                    SuaDanhMuc();
                }
            }
        });
        imgThemHinhAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(QuanLiDanhMucActivity.this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start();
            }
        });
        /*if(qliSuaXoaDanhMucSP == null){
            flag = false;
        }else {
            flag = true;
            edtTenDanhMuc.setText(qliSuaXoaDanhMucSP.getTenloaisanpham());
            edtHinhAnhDM.setText(qliSuaXoaDanhMucSP.getHinhanh());
            btnThem.setText("Sửa");
        }*/
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getTitle().equals("Sửa")){
            flag = true;
            edtTenDanhMuc.setText(qliSuaXoaDanhMucSP.getTenloaisanpham());
            edtHinhAnhDM.setText(qliSuaXoaDanhMucSP.getHinhanh());
            btnThem.setText("Sửa");
        }else if(item.getTitle().equals("Xoá")){
            XoaDanhMuc();
        }
        return super.onContextItemSelected(item);
    }

    private void XoaDanhMuc() {
        compositeDisposable.add(atFoodAPI.xoaDanhMuc(qliSuaXoaDanhMucSP.getMaloaisanpham())
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

    private void SuaDanhMuc() {
        String tenDM = edtTenDanhMuc.getText().toString().trim();
        String hinhAnhDM = edtHinhAnhDM.getText().toString().trim();
        if(TextUtils.isEmpty(tenDM)  || TextUtils.isEmpty(hinhAnhDM) ){
            Toast.makeText(getApplicationContext(), "Hãy nhập đủ dữ liệu!!!", Toast.LENGTH_SHORT).show();
        }
        else {
            compositeDisposable.add(atFoodAPI.suaDanhMuc(tenDM,hinhAnhDM,qliSuaXoaDanhMucSP.getMaloaisanpham())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            ketQuaModel -> {
                                if(ketQuaModel.isSuccess()){
                                    flag = false;
                                    btnThem.setText("Thêm");
                                    edtTenDanhMuc.setText(null);
                                    edtHinhAnhDM.setText(null);
                                    initData();
                                }
                            }
                            ,
                            throwable -> {
                                Toast.makeText(getApplicationContext(), "Error!!!!", Toast.LENGTH_LONG).show();
                            }
                    ));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mediaPath = data.getDataString();
        uploadMultipleFile();
    }
    private String getPath(Uri uri){
        String result;
        Cursor cursor = getContentResolver().query(uri,null,null,null,null);
        if (cursor == null){
            result = uri.getPath();
        }else {
            cursor.moveToFirst();
            int index  = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(index);
            cursor.close();
        }
        return  result;
    }
    private void uploadMultipleFile() {
        Uri uri = Uri.parse(mediaPath);
        File file = new File(getPath(uri));
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload1 = MultipartBody.Part.createFormData("file", file.getName(), requestBody1);
        Call<KetQuaModel> call = atFoodAPI.uploadFile(fileToUpload1);
        call.enqueue(new Callback< KetQuaModel >() {
            @Override
            public void onResponse(Call < KetQuaModel > call, Response< KetQuaModel > response) {
                KetQuaModel serverResponse = response.body();
                if (serverResponse != null) {
                    if (serverResponse.isSuccess()) {
                        edtHinhAnhDM.setText(mediaPath);
                    } else {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Log.v("Response", serverResponse.toString());
                }
            }
            @Override
            public void onFailure(Call< KetQuaModel > call, Throwable t) {
                Log.d("log", t.getMessage());
            }
        });
    }
    private void initData() {
        compositeDisposable.add(atFoodAPI.getLoaiSp()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        loaiSpModel -> {
                            if(loaiSpModel.isSuccess()){
                                arrLoaiSP = loaiSpModel.getResult();
                                loaiSpAdapter = new QliDanhMucAdapter(getApplicationContext(), arrLoaiSP);
                                recyclerViewQli.setAdapter(loaiSpAdapter);
                            }

                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Không thể kết nối được Server", Toast.LENGTH_LONG).show();
                        }
                )
        );
    }
    private void themDanhMuc() {
        String tenDM = edtTenDanhMuc.getText().toString().trim();
        String hinhAnhDM = edtHinhAnhDM.getText().toString().trim();
        if(TextUtils.isEmpty(tenDM)  || TextUtils.isEmpty(hinhAnhDM) ){
            Toast.makeText(getApplicationContext(), "Hãy nhập đủ dữ liệu!!!", Toast.LENGTH_SHORT).show();
        }
        else {
            compositeDisposable.add(atFoodAPI.themDanhMuc(tenDM,hinhAnhDM)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            ketQuaModel -> {
                                if(ketQuaModel.isSuccess()){
                                    Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                                    edtTenDanhMuc.setText(null);
                                    edtHinhAnhDM.setText(null);
                                    initData();
                                }
                            }
                            ,
                            throwable -> {
                                Toast.makeText(getApplicationContext(), "Error!!!!", Toast.LENGTH_LONG).show();
                            }
                    ));
        }
    }

    private void ActionBar() {
        setSupportActionBar(toolbarQliDanhMuc);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbarQliDanhMuc.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        toolbarQliDanhMuc = findViewById(R.id.toolbarQliDanhMuc);
        btnThem = findViewById(R.id.btnQliThemDm);
        recyclerViewQli = findViewById(R.id.recycleViewQliDanhMuc);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 4);
        recyclerViewQli.setLayoutManager(layoutManager);
        arrLoaiSP = new ArrayList<>();
        atFoodAPI = RetrofitClient.getInstance(Utils.BASE_URL).create(ATFoodAPI.class);
        edtTenDanhMuc = findViewById(R.id.edtTenDanhMuc);
        edtHinhAnhDM = findViewById(R.id.edtHinhAnhDanhMuc);
        imgThemHinhAnh = findViewById(R.id.imgThemHinhAnh);
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
    public void eventSuaXoa(SuaXoaEventDM event){
        if (event != null){
            qliSuaXoaDanhMucSP = event.getLoaiSp();
        }
    }
}