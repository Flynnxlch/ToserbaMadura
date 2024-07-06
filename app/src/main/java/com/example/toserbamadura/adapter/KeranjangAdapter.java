package com.example.toserbamadura.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toserbamadura.R;
import com.example.toserbamadura.models.KeranjangModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Locale;

public class KeranjangAdapter extends RecyclerView.Adapter<KeranjangAdapter.ViewHolder> {

    FirebaseFirestore firestore;
    FirebaseAuth auth;
    Context context;
    List<KeranjangModel> keranjangModelList;
    int totalPrice = 0;

    public KeranjangAdapter(Context context, List<KeranjangModel> keranjangModelList) {
        this.context = context;
        this.keranjangModelList = keranjangModelList;
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(keranjangModelList.get(position).getProductName());
        holder.price.setText(keranjangModelList.get(position).getProductPrice());
        holder.date.setText(keranjangModelList.get(position).getCurrentDate());
        holder.time.setText(keranjangModelList.get(position).getCurrentTime());
        holder.quantity.setText(keranjangModelList.get(position).getTotalQuantity());
        holder.totalPrice.setText(String.valueOf(keranjangModelList.get(position).getTotalPrice()));


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                        .collection("AddToCart")
                        .document(keranjangModelList.get(position).getDoucmentId())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    keranjangModelList.remove(keranjangModelList.get(position));
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        //Pass Total Harga
        totalPrice = totalPrice + keranjangModelList.get(position).getTotalPrice();
        Intent intent = new Intent("TotalHarga");
        intent.putExtra("totalhargasaya" ,totalPrice);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    @Override
    public int getItemCount() {
        return keranjangModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,price,date,time,quantity,totalPrice;
        ImageView delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.productname);
            price = itemView.findViewById(R.id.productprice);
            date = itemView.findViewById(R.id.current_date);
            time = itemView.findViewById(R.id.current_time);
            quantity = itemView.findViewById(R.id.total_quantity);
            totalPrice = itemView.findViewById(R.id.total_price);
            delete = itemView.findViewById(R.id.btn_rmvcart);

        }
    }
}
