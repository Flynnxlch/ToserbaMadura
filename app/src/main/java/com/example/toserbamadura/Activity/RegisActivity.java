package com.example.toserbamadura.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toserbamadura.R;
import com.example.toserbamadura.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisActivity extends AppCompatActivity {

    Button Daftar;
    EditText Nama, Email ,Password;
    TextView Masuk;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        progressBar = findViewById(R.id.prog_bar);
        progressBar.setVisibility(View.GONE);

        Daftar = findViewById(R.id.regis_btn);
        Nama = findViewById(R.id.sign_name);
        Email = findViewById(R.id.sign_email);
        Password = findViewById(R.id.sign_pass);
        Masuk = findViewById(R.id.sign_regis);

        Masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisActivity.this, LoginActivity.class));
            }
        });
        Daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createUser();
                progressBar.setVisibility(View.VISIBLE);
            }
        });

    }

    private void createUser() {

        String userName = Nama.getText().toString();
        String userEmail = Email.getText().toString();
        String userPassword = Password.getText().toString();

        if (TextUtils.isEmpty(userName)){
            Toast.makeText(this,"Namanya Kosong Nih",Toast.LENGTH_SHORT).show();
            return;
        }
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
        //Bikin User
        auth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            UserModel userModel = new UserModel(userName, userEmail, userPassword);
                            String id = task.getResult().getUser().getUid();
                            database.getReference().child("Users").child(id).setValue(userModel);
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(RegisActivity.this, "Pendaftaran Akun Kamu Berhail", Toast.LENGTH_SHORT).show();
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(RegisActivity.this, "Ada yang Error ni", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
