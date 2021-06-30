package com.trotos.appsubastas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.trotos.appsubastas.Modelos.ItemCatalogo;

import java.util.List;

public class MyAdapterCatalogo extends RecyclerView.Adapter<MyAdapterCatalogo.ViewHolder> {
    private List<ItemCatalogo> mData;
    private LayoutInflater mInflater;
    private Context context;
    final OnItemClickListener listener2;

    boolean estaRegistrado;

    public interface OnItemClickListener {
        void onItemClick(ItemCatalogo item);
    }

    public MyAdapterCatalogo(List<ItemCatalogo> itemList, Boolean estaRegistrado, Context context, OnItemClickListener listener2){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
        this.listener2 = listener2;
        this.estaRegistrado = estaRegistrado;
    }

    public int getItemCount() {
        return mData.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = mInflater.from(parent.getContext()).inflate(R.layout.list_element_catalogos,parent,false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, final int position){
        //holder.cv2.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition));
        holder.bindData(mData.get(position));
    }

    public void setItems(List<ItemCatalogo> items){
        mData = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage;
        TextView descripcion, descripcionBreve, ValorActual, moneda;
        CardView cv2;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageView2);
            descripcion = itemView.findViewById(R.id.descripcionTextView2);
            descripcionBreve = itemView.findViewById(R.id.descripcionBreveTextView2);
            ValorActual = itemView.findViewById(R.id.valorActualTextView2);
            moneda = itemView.findViewById(R.id.monedaActualTextView2);
            cv2 = itemView.findViewById(R.id.cv2);
        }

        public void bindData(final ItemCatalogo item){
            Picasso.with(context).load(item.getUrlImage()).into(iconImage);
            descripcion.setText(item.getTitle());
            descripcionBreve.setText(item.getDescription());
            moneda.setText(item.getCurrency());

            String valorActualText = String.format("%,d", item.getBasePrice());
            ValorActual.setText(valorActualText);

            if(!estaRegistrado){
                ValorActual.setVisibility(View.GONE);
                moneda.setVisibility(View.GONE);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener2.onItemClick(item);
                }
            });
        }
    }
}
