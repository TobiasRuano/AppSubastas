package com.trotos.appsubastas;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapterCatalogo extends RecyclerView.Adapter<MyAdapterCatalogo.ViewHolder> {
    private List<itemCatalogo> mData;
    private LayoutInflater mInflater;
    private Context context;
    final OnItemClickListener listener2;

    //HARDCODEADO
    boolean estaRegistrado = true;

    public interface OnItemClickListener {
        void onItemClick(itemCatalogo item);
    }

    public MyAdapterCatalogo(List<itemCatalogo> itemList, Context context, OnItemClickListener listener2){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
        this.listener2 = listener2;
    }

    public int getItemCount() {
        return mData.size();
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.from(parent.getContext()).inflate(R.layout.list_element_catalogos,parent,false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(final ViewHolder holder, final int position){
        holder.cv2.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition));
        holder.bindData(mData.get(position));
    }

    public void setItems(List<itemCatalogo> items){
        mData = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage;
        TextView descripcion, descripcionBreve, ValorActual;
        CardView cv2;

        ViewHolder(View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageView2);
            descripcion = itemView.findViewById(R.id.descripcionTextView2);
            descripcionBreve = itemView.findViewById(R.id.descripcionBreveTextView2);
            ValorActual = itemView.findViewById(R.id.valorActualTextView2);

            cv2 = itemView.findViewById(R.id.cv2);
        }

        public void bindData(final itemCatalogo item){
            iconImage.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);
            descripcion.setText(item.getDescripcion());
            descripcionBreve.setText(item.getDescripcionBreve());
            ValorActual.setText(item.getValorActual());

            //HARDCODEADO
            if(estaRegistrado == false){
                ValorActual.setVisibility(View.GONE);
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
