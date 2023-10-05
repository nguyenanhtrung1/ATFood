package com.example.atfood.ActivityUser;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import com.example.atfood.R;
import com.example.atfood.Retrofit.ATFoodAPI;
import com.example.atfood.Retrofit.RetrofitClient;
import com.example.atfood.Utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class DangKiActivity extends AppCompatActivity {
    EditText edtTaiKhoan, edtMatKhau, edtReMatKhau, edtSoDienThoai,edtTenNguoiDung;
    AppCompatButton btnDangKi;
    FirebaseAuth mAuth;
    ATFoodAPI atFoodAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Spinner spinnerVaiTro;
    int vaiTro;
    CountryCodePicker countryCodePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ki);
        initView();
        initControl();
    }

    private void initControl() {
        spinnerVaiTro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                vaiTro = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DangKi();
            }
        });
    }
    private void DangKi() {
        String countryCode = countryCodePicker.getSelectedCountryCode();
        String taiKhoan = edtTaiKhoan.getText().toString().trim();
        String tenNguoiDung = edtTenNguoiDung.getText().toString().trim();
        String matKhau = edtMatKhau.getText().toString().trim();
        String reMatKhau = edtReMatKhau.getText().toString().trim();
        String sodienthoai ="+84" + edtSoDienThoai.getText().toString().trim();
        String sodienthoai2 ="+"+countryCode + edtSoDienThoai.getText().toString().trim();
        Log.d("CountryCodePicker", countryCode);
        Log.d("sdt", sodienthoai);
        Log.d("sdt2", sodienthoai2);

        if(TextUtils.isEmpty(taiKhoan)){
            Toast.makeText(getApplicationContext(), "Bạn Chưa Nhập Tài Khoản!!!", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(tenNguoiDung)){
            Toast.makeText(getApplicationContext(), "Bạn Chưa Nhập Tên Người Dùng!!!", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(matKhau)){
            Toast.makeText(getApplicationContext(), "Bạn Chưa Nhập Mật Khẩu!!!", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(reMatKhau)){
            Toast.makeText(getApplicationContext(), "Bạn Chưa Nhập Lại Mật Khẩu!!!", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(sodienthoai)){
            Toast.makeText(getApplicationContext(), "Bạn chưa điền số điện thoại!!!", Toast.LENGTH_SHORT).show();
        }else{
            if(matKhau.equals(reMatKhau)){
                VerifyPhoneNumber(sodienthoai2);
            }else{
                Toast.makeText(getApplicationContext(), "Mật khẩu điền lại chưa khớp!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void VerifyPhoneNumber(String phoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // (optional) Activity for callback binding
                        // If no activity is passed, reCAPTCHA verification can not be used.
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(DangKiActivity.this, "Fail!", Toast.LENGTH_SHORT).show();

                            }
                            @Override
                            public void onCodeSent(@NonNull String verificationOTP, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(verificationOTP, forceResendingToken);
                                goToOPTActivity(phoneNumber,verificationOTP);
                            }
                        })

                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(DangKiActivity.this, "The verification code entered was invalid", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                });
    }

    private void goToOPTActivity(String phoneNumber, String verificationOTP) {
        String taiKhoan = edtTaiKhoan.getText().toString().trim();
        String tenNguoiDung = edtTenNguoiDung.getText().toString().trim();
        String matKhau = edtMatKhau.getText().toString().trim();

        Intent intent = new Intent(this, OTPActivity.class);
        intent.putExtra("phone_number",phoneNumber);
        intent.putExtra("verification_Otp",verificationOTP);
        intent.putExtra("taiKhoan",taiKhoan);
        intent.putExtra("matKhau",matKhau);
        intent.putExtra("tenNguoiDung",tenNguoiDung);
        intent.putExtra("vaiTro",vaiTro);
        startActivity(intent);

    }
    private void initView() {
        edtTaiKhoan = findViewById(R.id.edtTaiKhoan);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        edtReMatKhau = findViewById(R.id.edtReMatKhau);
        btnDangKi = findViewById(R.id.btnDangKi);
        edtSoDienThoai = findViewById(R.id.edtSoDienThoai);
        edtTenNguoiDung = findViewById(R.id.edtTenNguoiDung);
        spinnerVaiTro = findViewById(R.id.spinner_vaiTro);
        countryCodePicker = findViewById(R.id.countryCodePicker);
        countryCodePicker.setCountryForNameCode("VN");

        List<String> listVaiTro = new ArrayList<>();
        listVaiTro.add("User ");
        listVaiTro.add("Admin");
        ArrayAdapter<String> spinerAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listVaiTro);
        spinnerVaiTro.setAdapter(spinerAdapter);

        mAuth = FirebaseAuth.getInstance();
        atFoodAPI = RetrofitClient.getInstance(Utils.BASE_URL).create(ATFoodAPI.class);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}