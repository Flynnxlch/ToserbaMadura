package com.example.toserbamadura.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.toserbamadura.Activity.ViewAllActivity;
import com.example.toserbamadura.R;
import com.example.toserbamadura.models.PopulerMod;

import java.util.List;

public class PopAdapter extends RecyclerView.Adapter<PopAdapter.ViewHolder> {

    private Context context;
    private List<PopulerMod> populerModList ;

    public PopAdapter(Context context, List<PopulerMod> populerModList) {
        this.context = context;
        this.populerModList = populerModList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.populer_menu,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(populerModList.get(position).getImg_url()).into(holder.popImg);
        holder.name.setText(populerModList.get(position).getName());
        holder.description.setText(populerModList.get(position).getDescription());
        holder.rating.setText(populerModList.get(position).getRating());
        holder.price.setText(populerModList.get(position).getPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Intent intent = new Intent(context, ViewAllActivity.class);
                    intent.putExtra("type", populerModList.get(adapterPosition).getType());
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return populerModList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView popImg;
        TextView name,description,rating,price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            popImg = itemView.findViewById(R.id.pop_img);
            name = itemView.findViewById(R.id.popname1);
            description = itemView.findViewById(R.id.popdec);
            price = itemView.findViewById(R.id.pop_harga);
            rating = itemView.findViewById(R.id.pop_rate);

        }
    }
}
