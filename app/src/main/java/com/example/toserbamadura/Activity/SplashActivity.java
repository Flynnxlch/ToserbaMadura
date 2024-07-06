package com.example.toserbamadura.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.toserbamadura.R;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    Button Pesan;
    ProgressBar progressBar;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        auth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressbarr);
        progressBar.setVisibility(View.GONE);

        if (auth.getCurrentUser()!= null){
            progressBar.setVisibility(View.VISIBLE);
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            Toast.makeText(this, "Tunggu Sebentar, Anda Sudah Login", Toast.LENGTH_SHORT).show();
            finish();
        }

         Pesan = findViewById(R.id.btn_pesan);
         Pesan.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
             }
         });
    }

}