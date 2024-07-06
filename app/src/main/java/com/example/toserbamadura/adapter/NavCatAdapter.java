package com.example.toserbamadura.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.toserbamadura.R;
import com.example.toserbamadura.models.NavCato;

import java.util.List;

public class NavCatAdapter extends RecyclerView.Adapter<NavCatAdapter.ViewHolder> {

    Context context;
    List<NavCato> navCatoList;

    public NavCatAdapter(Context context, List<NavCato> navCatoList) {
        this.context = context;
        this.navCatoList = navCatoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_navacat,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(navCatoList.get(position).getImg_url()).into(holder.imageView);
        holder.name.setText(navCatoList.get(position).getName());
        holder.price.setText(navCatoList.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return navCatoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name,description,price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.cat_nav_img);
            name = itemView.findViewById(R.id.nav_cat_name);
            description = itemView.findViewById(R.id.nav_cat_desc);
            price = itemView.findViewById(R.id.nav_cat_int);
        }
    }
}
