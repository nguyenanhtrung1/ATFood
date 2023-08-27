package com.example.atfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class OTPActivity extends AppCompatActivity {
    EditText edtOTPSoDienThoai;
    AppCompatButton btnOTPDangKi;
    TextView txtSendOTP;
    String mPhoneNumber, mVetificationOTP;
    FirebaseAuth mAuth;
    PhoneAuthProvider.ForceResendingToken mForceResendingToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);
        initView();
        initControl();
        getDataIntent();
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
                        .setPhoneNumber(mPhoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // (optional) Activity for callback binding
                        // If no activity is passed, reCAPTCHA verification can not be used.
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

                            FirebaseUser user = task.getResult().getUser();
                            goToMainActivity(user.getPhoneNumber());
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(OTPActivity.this, "The verification code entered was invalid", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                });
    }
    private void goToMainActivity(String phoneNumber) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("phone_number",phoneNumber);
        startActivity(intent);
    }
    private void initView() {
        edtOTPSoDienThoai = findViewById(R.id.edtOTPSoDienThoai);
        btnOTPDangKi = findViewById(R.id.btnOTPDangKi);
        txtSendOTP = findViewById(R.id.txtClick_SendOTP);
        mAuth = FirebaseAuth.getInstance();
    }
}