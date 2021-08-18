package com.example.collagefinalproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.collagefinalproject.databinding.ActSplashBinding;

public class ActSplash extends AppCompatActivity {
    private ActSplashBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActSplashBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent mainIntent = new Intent(ActSplash.this, ActLogin.class);
            this.startActivity(mainIntent);
            this.finish();
        }, 1500);
    }
}