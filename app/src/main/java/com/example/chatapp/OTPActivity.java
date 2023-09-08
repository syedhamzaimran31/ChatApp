package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

    String verificationID;

    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOtpactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dialog=new ProgressDialog(this);
        dialog.setMessage("sending OTP...");
        dialog.setCancelable(false);
        dialog.show();
        auth=FirebaseAuth.getInstance();
        String phoneNumber=getIntent().getStringExtra("phoneNumber");
        binding.phoneText.setText("Verify "+phoneNumber);

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(120L, TimeUnit.SECONDS)
                .setActivity(OTPActivity.this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                    }

                    @Override
                    public void onCodeSent(@NonNull String idVerify, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(idVerify, forceResendingToken);
                        dialog.dismiss();
                        verificationID=idVerify;
                    }
                }).build();
                    PhoneAuthProvider.verifyPhoneNumber(options);
                    binding.otpView.setOtpCompletionListener(new OnOtpCompletionListener() {
                        @Override
                        public void onOtpCompleted(String otp) {
                            PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationID,otp);
                            auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        Intent i=new Intent(OTPActivity.this,setupProfileActivity.class);
                                        startActivity(i);
                                        finishAffinity();
                                        Toast.makeText(OTPActivity.this, "Logged In Successful", Toast.LENGTH_SHORT).show();
                                    } else{
                                        Toast.makeText(OTPActivity.this, "Logged In Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
    }
}