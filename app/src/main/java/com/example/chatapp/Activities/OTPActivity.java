package com.example.chatapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.chatapp.databinding.ActivityOtpactivityBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.mukeshsolanki.OnOtpCompletionListener;

import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity {
    ActivityOtpactivityBinding binding;
    FirebaseAuth auth;

    String verificationId;

    ProgressDialog dialog;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dialog = new ProgressDialog(this);
        dialog.setMessage("Sending OTP...");
        dialog.setCancelable(false);
        dialog.show();

        auth = FirebaseAuth.getInstance();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
//        getSupportActionBar().hide();



        String phoneNumber = getIntent().getStringExtra("phoneNumber");

        binding.phoneText.setText("Verify " + phoneNumber);

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(OTPActivity.this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        Toast.makeText(OTPActivity.this,"Verified Sucessfully",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        // Called when verification fails
                        dialog.dismiss(); // Dismiss the progress dialog in case of failure
                        Toast.makeText(OTPActivity.this, "Verification failed.Please try again.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String verifyId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(verifyId, forceResendingToken);
                        dialog.dismiss();
                        verificationId = verifyId;

                        InputMethodManager imm = (InputMethodManager)   getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                        binding.otpView.requestFocus();
                    }
                }).build();

        PhoneAuthProvider.verifyPhoneNumber(options);

        binding.otpView.setOtpCompletionListener(new OnOtpCompletionListener() {
            @Override
            public void onOtpCompleted(String otp) {
                if (verificationId != null && !verificationId.isEmpty()) {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otp);

                auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Intent intent = new Intent(OTPActivity.this, setupProfileActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        } else {
                            Toast.makeText(OTPActivity.this, "Failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else {
                    // Handle the case where verificationId is null or empty
                    Toast.makeText(OTPActivity.this, "Verification failed. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });





    }

}