package com.trotos.appsubastas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.trotos.appsubastas.Modelos.Item;

import java.util.List;

public class MyAdapterMisObjetos extends RecyclerView.Adapter<MyAdapterMisObjetos.ViewHolder> {
    private List<Item> mData;
    private LayoutInflater mInflater;
    private Context context;
    final OnItemClickListener listener4;

    boolean estaRegistrado;

    public interface OnItemClickListener {
        void onItemClick(Item item);
    }

    public MyAdapterMisObjetos(List<Item> itemList, Boolean estaRegistrado, Context context, OnItemClickListener listener4){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
        this.listener4 = listener4;
        this.estaRegistrado = estaRegistrado;
    }

    public int getItemCount() {
        return mData.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = mInflater.from(parent.getContext()).inflate(R.layout.list_element_mis_objetos,parent,false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, final int position){
        holder.cv4.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition));
        holder.bindData(mData.get(position));
    }

    public void setItems(List<Item> items){
        mData = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage;
        TextView title, descripcionBreve, ValorActual, estado;
        CardView cv4;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageView4);
            title = itemView.findViewById(R.id.descripcionTextView4);
            descripcionBreve = itemView.findViewById(R.id.descripcionBreveTextView4);
            //ValorActual = itemView.findViewById(R.id.valorActualTextView4);
            estado = itemView.findViewById(R.id.estadoActualTextView4);

            cv4 = itemView.findViewById(R.id.cv4);
        }

        public void bindData(final Item item){
            //iconImage.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);
            title.setText(item.getTitle());
            descripcionBreve.setText(item.getDescription());
            estado.setText(item.getStatus());

            //String valorActualText = String.format("%,d", item.getValorActual());
            //ValorActual.setText(valorActualText);

            if(!estaRegistrado){
                //ValorActual.setVisibility(View.GONE);
                estado.setVisibility(View.GONE);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener4.onItemClick(item);
                }
            });
        }

    }
}
