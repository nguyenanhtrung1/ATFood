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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.atfood.Model.CuaHang;
import com.example.atfood.Model.KetQuaModel;
import com.example.atfood.Model.SanPham;
import com.example.atfood.R;
import com.example.atfood.Retrofit.ATFoodAPI;
import com.example.atfood.Retrofit.RetrofitClient;
import com.example.atfood.Utils.Utils;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemSanPhamActivity extends AppCompatActivity {
    Toolbar toolbarThemSanPham;
    EditText edtTenSanPham,edtGiaSanPham,edtMoTaSanPham,edtHinhAnhSP;
    Button btnThemSP;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ATFoodAPI atFoodAPI;
    ImageView imgThemHinhAnh;
    String mediaPath;
    CuaHang cuaHang;
    SanPham sanPham;
    boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themsanpham);
        iniView();
        initData();
        ActionBar();
        initConTrol();
    }
    private void initConTrol() {
        imgThemHinhAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(ThemSanPhamActivity.this)
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
                        edtHinhAnhSP.setText(mediaPath);
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

        Intent intent = getIntent();
        sanPham = (SanPham) intent.getSerializableExtra("sua");
        if(sanPham == null){
            flag = false;
        }else {
            flag = true;
            btnThemSP.setText("Sửa sản phẩm");
            edtTenSanPham.setText(sanPham.getTensanpham());
            edtGiaSanPham.setText(sanPham.getGiasanpham());
            edtHinhAnhSP.setText(sanPham.getHinhanh());
            edtMoTaSanPham.setText(sanPham.getMota());
        }

    }

    private void ActionBar() {
        setSupportActionBar(toolbarThemSanPham);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbarThemSanPham.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnThemSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == false){
                    ThemSanPham();
                }else {
                    suaSanPham();
                }
            }
        });
    }

    private void suaSanPham() {
        String tenSP = edtTenSanPham.getText().toString().trim();
        String giaSP = edtGiaSanPham.getText().toString().trim();
        String motaSP = edtMoTaSanPham.getText().toString().trim();
        String hinhAnhCH = edtHinhAnhSP.getText().toString().trim();
        if(TextUtils.isEmpty(tenSP)  || TextUtils.isEmpty(hinhAnhCH) || TextUtils.isEmpty(motaSP)){
            Toast.makeText(getApplicationContext(), "Hãy nhập đủ dữ liệu!!!", Toast.LENGTH_SHORT).show();
        }
        else {
            compositeDisposable.add(atFoodAPI.suaSanPham(tenSP,giaSP,hinhAnhCH,motaSP,sanPham.getMacuahang(),sanPham.getMasanpham())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            ketQuaModel -> {
                                if(ketQuaModel.isSuccess()){
                                    Toast.makeText(getApplicationContext(), "Sửa thành công!!!", Toast.LENGTH_SHORT).show();
                                    onBackPressed();

                                }
                            }
                            ,
                            throwable -> {
                                Toast.makeText(getApplicationContext(), "Error!!!!", Toast.LENGTH_LONG).show();
                            }
                    ));
        }
    }

    private void ThemSanPham() {
        String tenSP = edtTenSanPham.getText().toString().trim();
        String giaSP = edtGiaSanPham.getText().toString().trim();
        String motaSP = edtMoTaSanPham.getText().toString().trim();
        String hinhAnhCH = edtHinhAnhSP.getText().toString().trim();
        if(TextUtils.isEmpty(tenSP) || TextUtils.isEmpty(giaSP) || TextUtils.isEmpty(hinhAnhCH) || TextUtils.isEmpty(motaSP)){
            Toast.makeText(getApplicationContext(), "Hãy nhập đủ dữ liệu!!!", Toast.LENGTH_SHORT).show();
        }
        else {
            compositeDisposable.add(atFoodAPI.themSanPham(tenSP,giaSP,hinhAnhCH,motaSP,cuaHang.getMacuahang())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            ketQuaModel -> {
                                if(ketQuaModel.isSuccess()){
                                    Toast.makeText(getApplicationContext(), "thanh cong", Toast.LENGTH_SHORT).show();
                                    onBackPressed();
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
        cuaHang = (CuaHang) getIntent().getSerializableExtra("cuahang");
        toolbarThemSanPham = findViewById(R.id.toobarThemSanPham);
        edtTenSanPham = findViewById(R.id.edtTenSanPham);
        edtGiaSanPham = findViewById(R.id.edtGiaSanPham);
        edtHinhAnhSP = findViewById(R.id.edtHinhAnhSanPham);
        edtMoTaSanPham = findViewById(R.id.edtMoTaSanPham);
        btnThemSP = findViewById(R.id.btnThemSP);
        imgThemHinhAnh = findViewById(R.id.imgThemHinhAnh);
        atFoodAPI = RetrofitClient.getInstance(Utils.BASE_URL).create(ATFoodAPI.class);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
