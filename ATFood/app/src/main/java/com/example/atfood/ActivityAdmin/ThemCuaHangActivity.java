package com.example.atfood.ActivityAdmin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.atfood.Adapter.CuaHangAdapter;
import com.example.atfood.Model.CuaHang;
import com.example.atfood.Model.KetQuaModel;
import com.example.atfood.R;
import com.example.atfood.Retrofit.ATFoodAPI;
import com.example.atfood.Retrofit.RetrofitClient;
import com.example.atfood.Utils.Utils;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemCuaHangActivity extends AppCompatActivity {
    Toolbar toolbarThemCuaHang;
    EditText edtTenCuaHang,edtDiaChiCH,edtHinhAnhCH;
    Button btnThemSP;
    Spinner spinnerDanhMuc;
    int danhMuc = 0;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ATFoodAPI atFoodAPI;
    ImageView imgThemHinhAnh;
    String mediaPath;
    CuaHang cuaHang;
    boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_cua_hang);
        iniView();
        ActionBar();
        initData();
        initConTrol();
    }

    private void initConTrol() {
        spinnerDanhMuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                danhMuc = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        imgThemHinhAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(ThemCuaHangActivity.this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start();
            }
        });
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
                        edtHinhAnhCH.setText(mediaPath);
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
        List<String> listDanhMuc = new ArrayList<>();
        listDanhMuc.add("Danh mục ");
        listDanhMuc.add("Đồ ăn sáng");
        listDanhMuc.add("Đồ ăn nhanh");
        listDanhMuc.add("Cơm ngon");
        listDanhMuc.add("Rau-Củ-Quả");
        listDanhMuc.add("Súp nóng hổi");
        listDanhMuc.add("Trà sữa");
        listDanhMuc.add("Ăn vặt");
        listDanhMuc.add("Đồ uống");
        ArrayAdapter<String> spinerAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listDanhMuc);
        spinnerDanhMuc.setAdapter(spinerAdapter);

        Intent intent = getIntent();
        cuaHang = (CuaHang) intent.getSerializableExtra("sua");
        if(cuaHang == null){
            flag = false;
        }else {
            flag = true;
            btnThemSP.setText("Sửa cửa hàng");
            edtTenCuaHang.setText(cuaHang.getTencuahang());
            edtDiaChiCH.setText(cuaHang.getDiachi());
            edtHinhAnhCH.setText(cuaHang.getHinhanh());
            spinnerDanhMuc.setSelection(cuaHang.getDanhmuc());
        }

    }

    private void ActionBar() {
        setSupportActionBar(toolbarThemCuaHang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbarThemCuaHang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnThemSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag == false){
                    ThemCuaHang();
                }else {
                    suaCuaHang();
                }
            }
        });
    }

    private void suaCuaHang() {
        String tenCH = edtTenCuaHang.getText().toString().trim();
        String diaChiCH = edtDiaChiCH.getText().toString().trim();
        String hinhAnhCH = edtHinhAnhCH.getText().toString().trim();
        if(TextUtils.isEmpty(tenCH) || TextUtils.isEmpty(diaChiCH) || TextUtils.isEmpty(hinhAnhCH) || danhMuc == 0){
            Toast.makeText(getApplicationContext(), "Hãy nhập đủ dữ liệu!!!", Toast.LENGTH_SHORT).show();
        }
        else {
            compositeDisposable.add(atFoodAPI.suaCuaHang(tenCH,diaChiCH,hinhAnhCH,danhMuc,cuaHang.getMacuahang())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            ketQuaModel -> {
                                if(ketQuaModel.isSuccess()){
                                    Toast.makeText(getApplicationContext(), "Sửa thành công!!!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), QuanLiCuaHangActivity.class);
                                    startActivity(intent);

                                }
                            }
                            ,
                            throwable -> {
                                Toast.makeText(getApplicationContext(), "Error!!!!", Toast.LENGTH_LONG).show();
                            }
                    ));
        }
    }

    private void ThemCuaHang() {
        String tenCH = edtTenCuaHang.getText().toString().trim();
        String diaChiCH = edtDiaChiCH.getText().toString().trim();
        String hinhAnhCH = edtHinhAnhCH.getText().toString().trim();
        if(TextUtils.isEmpty(tenCH) || TextUtils.isEmpty(diaChiCH) || TextUtils.isEmpty(hinhAnhCH) || danhMuc == 0){
            Toast.makeText(getApplicationContext(), "Hãy nhập đủ dữ liệu!!!", Toast.LENGTH_SHORT).show();
        }
        else {
            compositeDisposable.add(atFoodAPI.themCuaHang(tenCH,diaChiCH,hinhAnhCH,danhMuc)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            ketQuaModel -> {
                                if(ketQuaModel.isSuccess()){
                                    Intent intent = new Intent(getApplicationContext(), QuanLiCuaHangActivity.class);
                                    startActivity(intent);
                                }
                            }
                            ,
                            throwable -> {
                                Toast.makeText(getApplicationContext(), "Error!!!!", Toast.LENGTH_LONG).show();
                            }
                    ));
        }
    }

    private void iniView() {
        toolbarThemCuaHang = findViewById(R.id.toobarThemCuaHang);
        edtTenCuaHang = findViewById(R.id.edtTenCuaHang);
        edtDiaChiCH = findViewById(R.id.edtDiaChiCuaHang);
        edtHinhAnhCH = findViewById(R.id.edtHinhAnhCuaHang);
        btnThemSP = findViewById(R.id.btnThemSP);
        spinnerDanhMuc = findViewById(R.id.spinner_DanhMuc);
        imgThemHinhAnh = findViewById(R.id.imgThemHinhAnh);
        atFoodAPI = RetrofitClient.getInstance(Utils.BASE_URL).create(ATFoodAPI.class);
    }

}