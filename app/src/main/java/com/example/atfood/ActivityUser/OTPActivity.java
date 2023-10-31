package com.example.atfood.ActivityUser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OTPActivity extends AppCompatActivity {
    EditText edtOTPSoDienThoai;
    AppCompatButton btnOTPDangKi;
    TextView txtSendOTP;
    String mPhoneNumber, mVetificationOTP;
    FirebaseAuth mAuth;
    PhoneAuthProvider.ForceResendingToken mForceResendingToken;
    ATFoodAPI atFoodAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);
        getDataIntent();
        initView();
        initControl();

    }

    private void getDataIntent() {
        mPhoneNumber = getIntent().getStringExtra("phone_number");
        mVetificationOTP = getIntent().getStringExtra("verification_Otp");
    }

    private void initControl() {
        btnOTPDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String OTP = edtOTPSoDienThoai.getText().toString().trim();
                onClickOTPcode(OTP);
            }
        });
        txtSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickOTPAgain();
            }
        });
    }

    private void onClickOTPAgain() {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(mPhoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)

                        .setForceResendingToken(mForceResendingToken)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(OTPActivity.this, "Fail!", Toast.LENGTH_SHORT).show();

                            }
                            @Override
                            public void onCodeSent(@NonNull String verificationOTP, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(verificationOTP, forceResendingToken);
                                mVetificationOTP = verificationOTP;
                                mForceResendingToken = forceResendingToken;
                            }
                        })

                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void onClickOTPcode(String otp) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVetificationOTP, otp);
        signInWithPhoneAuthCredential(credential);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String taiKhoan = getIntent().getStringExtra("taiKhoan");
                            String matKhau = getIntent().getStringExtra("matKhau");
                            String tenNguoiDung = getIntent().getStringExtra("tenNguoiDung");
                            int vaiTro = getIntent().getIntExtra("vaiTro",0);
                            FirebaseUser user = task.getResult().getUser();
                            if(user != null){
                                compositeDisposable.add(atFoodAPI.dangKi(taiKhoan, matKhau, tenNguoiDung, mPhoneNumber,vaiTro,user.getUid())
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(
                                                userModel -> {
                                                    if(userModel.isSuccess()){
                                                        Utils.user_current.setTaikhoan(taiKhoan);
                                                        Utils.user_current.setMatkhau(matKhau);
                                                        goToMainActivity();
                                                        finish();
                                                    }else{
                                                        Toast.makeText(getApplicationContext(), "Success!!", Toast.LENGTH_SHORT).show();
                                                    }
                                                },
                                                throwable -> {
                                                    Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                        )
                                );
                            }

                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(OTPActivity.this, "The verification code entered was invalid", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                });
    }
    private void goToMainActivity() {
        Intent intent = new Intent(getApplicationContext(), DangNhapActivity.class);
        //intent.putExtra("phone_number",phoneNumber);
        startActivity(intent);
    }
    private void initView() {
        edtOTPSoDienThoai = findViewById(R.id.edtOTPSoDienThoai);
        btnOTPDangKi = findViewById(R.id.btnOTPDangKi);
        txtSendOTP = findViewById(R.id.txtClick_SendOTP);
        mAuth = FirebaseAuth.getInstance();
        atFoodAPI = RetrofitClient.getInstance(Utils.BASE_URL).create(ATFoodAPI.class);
    }
    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}