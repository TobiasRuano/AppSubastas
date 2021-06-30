package com.trotos.appsubastas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.trotos.appsubastas.Modelos.Auction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class MyAdapterSubasta extends RecyclerView.Adapter<MyAdapterSubasta.ViewHolder> {
    private List<Auction> mData;
    private LayoutInflater mInflater;
    private Context context;
    final OnItemClickListener listener;

    private ArrayList<Auction> mDataOriginal;

    public interface OnItemClickListener {
        void onItemClick(Auction item);
    }

    public MyAdapterSubasta(List<Auction> itemList, Context context, OnItemClickListener listener){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
        this.listener = listener;
        mDataOriginal = new ArrayList<>();
        mDataOriginal.addAll(mData);
    }

    public int getItemCount() {
        return mData.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = mInflater.from(parent.getContext()).inflate(R.layout.list_element_subastas,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position){
        holder.cv.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition));
        holder.bindData(mData.get(position));
    }

    public void setItems(List<Auction> items){
        mData = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage;
        TextView name, state, category;
        CardView cv;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageView);
            name = itemView.findViewById(R.id.nameTextView);
            state = itemView.findViewById(R.id.stateTextView);
            category = itemView.findViewById(R.id.categoryTextView);
            cv = itemView.findViewById(R.id.cv2);
        }

        public void bindData(final Auction item){
            String stateString = item.getTimeStatus();
            name.setText(item.getTitle());
            state.setText(stateString);
            category.setText(item.getCategory());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public void filtrado(final String txtBuscar){
        int longitud = txtBuscar.length();
        if(longitud == 0){
            mData.clear();
            mData.addAll(mDataOriginal);
        }else{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                List<Auction> subastas = mData.stream()
                        .filter(i -> i.getTitle().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                mData.clear();
                mData.addAll(subastas);
            }else{
                for(Auction a: mDataOriginal){
                    if(a.getTitle().toLowerCase().contains(txtBuscar.toLowerCase())){
                        mData.add(a);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }


}