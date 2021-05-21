package com.trotos.appsubastas;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.CardViewHolder> {

    MPTarjeta[] tarjetas;
    Context context;

    public CardViewAdapter(Context ct, MPTarjeta[] tarjetas) {
        this.context = ct;
        this.tarjetas = tarjetas;
    }

    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.tarjeta_row, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewAdapter.CardViewHolder holder, int position) {
        String dashedString = createDashedString(tarjetas[position].getNumeroTarjeta());
        holder.number.setText(dashedString);
        holder.name.setText(tarjetas[position].getNombreTarjeta());
        String prov = tarjetas[position].getProveedorTarjeta();
        holder.provider.setText(prov);

        selectCardColor(holder, prov);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showActionAlert("Eliminar", "Estas seguro que deseas eliminar esta tarjeta?");
                return false;
            }

            private void showActionAlert(String titulo, String mensaje) {
                new AlertDialog.Builder(holder.card.getContext())
                        .setTitle(titulo)
                        .setMessage(mensaje)
                        .setNegativeButton("Cancelar", null)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //TODO: Eliminar la tarjeta
                                tarjetas = filtrarTarjetaEliminada(tarjetas, position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position,tarjetas.length);
                            }
                        })
                        .show();
            }

            private MPTarjeta[] filtrarTarjetaEliminada(MPTarjeta[] tarjetas, int position) {
                int size = 0;
                for(int i=0;i<tarjetas.length;i++){
                    if(i != position)
                        size++;
                }
                MPTarjeta[] tmp = new MPTarjeta[size];
                size=0;
                for(int i=0;i<tarjetas.length;i++){
                    if(i != position)
                        tmp[size++] = tarjetas[i];
                }

                return tmp;
            }
        });
    }

    private void selectCardColor(CardViewHolder holder, String prov) {
        if (prov == "Visa") {
            holder.card.setCardBackgroundColor(Color.parseColor("#4048BD"));
        } else if (prov == "Amex") {
            holder.card.setCardBackgroundColor(Color.parseColor("#4d4f53"));
        } else if (prov == "MasterCard") {
            holder.card.setCardBackgroundColor(Color.parseColor("#EB001B"));
        } else {
            holder.card.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        }
    }

    private String createDashedString(String numeroTarjeta) {
        String s1 = numeroTarjeta.substring(0, 4);
        String s2 = numeroTarjeta.substring(4, 8);
        String s3 = numeroTarjeta.substring(8, 12);
        String s4 = numeroTarjeta.substring(12, 16);
        return s1 + "-" + s2 + "-" + s3 + "-" + s4;
    }

    @Override
    public int getItemCount() {
        return tarjetas.length;
    }

    public class CardViewHolder extends RecyclerView.ViewHolder{
        TextView number;
        TextView name;
        TextView provider;
        CardView card;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.numberText);
            name = itemView.findViewById(R.id.nameText);
            provider = itemView.findViewById(R.id.proveedorText);
            card = itemView.findViewById(R.id.cardView);
        }
    }
}
