package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.chatapp.databinding.ActivityPhoneBinding;
import com.google.firebase.auth.FirebaseAuth;

public class PhoneActivity extends AppCompatActivity {
    ActivityPhoneBinding binding;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityPhoneBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null){
            Intent i=new Intent(PhoneActivity.this,MainActivity.class);
            startActivity(i);
            finish();
        }
        binding.phoneBox.requestFocus();
        binding.continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(PhoneActivity.this,OTPActivity.class);
                i.putExtra("phoneNumber",binding.phoneBox.getText().toString());
                startActivity(i);
            }
        });
    }


}