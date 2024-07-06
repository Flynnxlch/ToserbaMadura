package com.example.toserbamadura;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.toserbamadura.Activity.PlaceOrderActivity;
import com.example.toserbamadura.adapter.KeranjangAdapter;
import com.example.toserbamadura.models.KeranjangModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class KeranjangFragment extends Fragment {

        FirebaseFirestore mDb;
        FirebaseAuth auth;
        TextView overTotalHarga;
        RecyclerView recyclerView;
        KeranjangAdapter keranjangAdapter;
        Button belicuk;
        List<KeranjangModel> keranjangModelList;

    public KeranjangFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_keranjang, container, false);

        mDb = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        recyclerView = root.findViewById(R.id.recyclerKeranjang);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        belicuk = root.findViewById(R.id.btn_buy);

        overTotalHarga = root.findViewById(R.id.textkeranjang1);
        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(mMessageRecei,new IntentFilter("TotalHarga"));

        keranjangModelList = new ArrayList<>();
        keranjangAdapter = new KeranjangAdapter(getActivity(),keranjangModelList);
        recyclerView.setAdapter(keranjangAdapter);

        mDb.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("AddToCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){

                                String documentId = documentSnapshot.getId();
                                KeranjangModel keranjangModel = documentSnapshot.toObject(KeranjangModel.class);
                                keranjangModel.setDoucmentId(documentId);
                                keranjangModelList.add(keranjangModel);
                                keranjangAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    belicuk.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getContext(), PlaceOrderActivity.class);
            intent.putExtra("itemList", (Serializable) keranjangModelList);
            startActivity(intent);
        }
    });
        

    return root;
    }
    public BroadcastReceiver mMessageRecei = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int totalBill = intent.getIntExtra("totalhargasaya", 0 );
            overTotalHarga.setText("Total Tagihan : Rp" + totalBill);
        }
    };
}