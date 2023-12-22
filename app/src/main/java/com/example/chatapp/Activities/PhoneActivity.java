package com.example.chatapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
//        if (auth.getCurrentUser() != null){
//            Intent i=new Intent(PhoneActivity.this,MainActivity.class);
//            startActivity(i);
//            finish();
//        }
        binding.phoneBox.requestFocus();
        binding.continueBtn.setOnClickListener(view -> {
            String phoneNumber = binding.phoneBox.getText().toString().trim();
            if (!phoneNumber.isEmpty()) {
                Intent i = new Intent(PhoneActivity.this, OTPActivity.class);
                i.putExtra("phoneNumber", phoneNumber);
                startActivity(i);
            } else {
                Toast.makeText(PhoneActivity.this, "Enter a valid number", Toast.LENGTH_SHORT).show();
            }
        });
    }


}