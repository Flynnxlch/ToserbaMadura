package com.example.toserbamadura.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.toserbamadura.R;
import com.example.toserbamadura.adapter.NavCatAdapter;
import com.example.toserbamadura.models.NavCatModel;
import com.example.toserbamadura.models.NavCato;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NavCatActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<NavCato> listCato;
    NavCatAdapter adapter;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_cat);

        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.nav_cat_resikler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listCato = new ArrayList<>();
        adapter = new NavCatAdapter(this,listCato);
        recyclerView.setAdapter(adapter);

        //Database produk berdasarkan Pengelompokan

        db.collection("AllProduct")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                NavCato navCatModel = document.toObject(NavCato.class);
                                listCato.add(navCatModel);
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(NavCatActivity.this, "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}