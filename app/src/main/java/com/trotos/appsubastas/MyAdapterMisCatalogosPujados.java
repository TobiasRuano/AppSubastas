package com.trotos.appsubastas;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.trotos.appsubastas.Modelos.ItemCatalogo;

import java.util.List;

public class MyAdapterMisCatalogosPujados extends RecyclerView.Adapter<MyAdapterMisCatalogosPujados.ViewHolder> {
    private List<ItemCatalogo> mData;
    private LayoutInflater mInflater;
    private Context context;
    final OnItemClickListener listener5;

    boolean estaRegistrado;

    public interface OnItemClickListener {
        void onItemClick(ItemCatalogo item);
    }

    public MyAdapterMisCatalogosPujados(List<ItemCatalogo> itemList, Boolean estaRegistrado, Context context, OnItemClickListener listener5){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
        this.listener5 = listener5;
        this.estaRegistrado = estaRegistrado;
    }

    public int getItemCount() {
        return mData.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = mInflater.from(parent.getContext()).inflate(R.layout.list_element_mis_catalogos_pujados,parent,false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, final int position){
        holder.cv5.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition));
        holder.bindData(mData.get(position));
    }

    public void setItems(List<ItemCatalogo> items){
        mData = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage;
        TextView descripcion, descripcionBreve, ValorActual, estado;
        CardView cv5;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageView5);
            descripcion = itemView.findViewById(R.id.descripcionTextView5);
            descripcionBreve = itemView.findViewById(R.id.descripcionBreveTextView5);
            //ValorActual = itemView.findViewById(R.id.valorActualTextView5);
            estado = itemView.findViewById(R.id.estadoActualTextView5);

            cv5 = itemView.findViewById(R.id.cv5);
        }

        public void bindData(final ItemCatalogo item){
            Picasso.with(context).load(item.getUrlImage()).into(iconImage);
            descripcion.setText(item.getTitle());
            descripcionBreve.setText(item.getDescription());
            item.getTimeStatus();
            estado.setText(item.getStatus());
            estado.setTextColor(Color.parseColor("#ff8000"));

            //String valorActualText = String.format("%,d", item.getValorActual());
            //ValorActual.setText(valorActualText);

            if(!estaRegistrado){
                //ValorActual.setVisibility(View.GONE);
                estado.setVisibility(View.GONE);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener5.onItemClick(item);
                }
            });
        }

    }
}
