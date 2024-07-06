package com.example.toserbamadura.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toserbamadura.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    Button Masuk;
    EditText Email,Password;
    TextView Daftar;

    FirebaseAuth auth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate  (savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.prog_bar);
        progressBar.setVisibility(View.GONE);

        Masuk = findViewById(R.id.login_btn);
        Email = findViewById(R.id.log_email);
        Password = findViewById(R.id.log_pass);
        Daftar = findViewById(R.id.log_daftar);

        Daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisActivity.class));
            }
        });
        Masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginUser();
                progressBar.setVisibility(View.VISIBLE);

            }
        });



    }

        private void loginUser() {
            String userEmail = Email.getText().toString();
            String userPassword = Password.getText().toString();

            if (TextUtils.isEmpty(userEmail)){
                Toast.makeText(this,"Emailnya Kosong Nih",Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(userPassword)){
                Toast.makeText(this,"Passwordnya Kosong Nih",Toast.LENGTH_SHORT).show();
                return;
            }
            if (userPassword.length() < 6){
                Toast.makeText(this, "Passwordnya Harus lebih dari 6 kata", Toast.LENGTH_SHORT).show();
                return;
            }
            //Untuk Login User
            auth.signInWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            
                            if (task.isSuccessful()){
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            } else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this, "Error 404", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
    }
}