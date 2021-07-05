package com.trotos.appsubastas;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.trotos.appsubastas.Modelos.MPTarjeta;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;


public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.CardViewHolder> {

    List<MPTarjeta> tarjetas;
    Context context;

    public CardViewAdapter(Context ct, List<MPTarjeta> tarjetas) {
        this.context = ct;
        this.tarjetas = tarjetas;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.tarjeta_row, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewAdapter.CardViewHolder holder, int position) {
        String substring = (tarjetas.get(position).getCardNumber()).substring(12);
        String cardNumber = "************" + substring;
        String dashedString = separateString(cardNumber);
        holder.number.setText(dashedString);
        holder.name.setText(tarjetas.get(position).getCardHolderName());
        String prov = tarjetas.get(position).getCompany();
        holder.provider.setText(prov);

        if(!tarjetas.get(position).getDefaultCard()){
            holder.statusCard.setVisibility(View.GONE);
        }

        holder.card.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition));

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
                        .setPositiveButton("Cancelar", null)
                        .setNegativeButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteTarjeta(tarjetas.get(position));
                            }
                        })
                        .show();
            }

            private void deleteTarjeta(MPTarjeta tarjeta) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences", MODE_PRIVATE);
                String token = sharedPreferences.getString("Token", null);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://10.0.2.2:3000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ApiUtils as = retrofit.create(ApiUtils.class);
                Call<MPTarjeta> call = as.deleteTarjeta(tarjeta, "Bearer "+ token);

                call.enqueue(new Callback<MPTarjeta>() {
                    @Override
                    public void onResponse(Call<MPTarjeta> call, Response<MPTarjeta> response) {
                        if(response.isSuccessful()) {
                            tarjetas.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position,tarjetas.size());
                        } else {
                            Toast toast1 = Toast.makeText(context,"Error al intentar eliminar la tarjeta", Toast.LENGTH_LONG);
                            toast1.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MPTarjeta> call, Throwable t) {
                        Toast toast1 = Toast.makeText(context,"Hubo un problema", Toast.LENGTH_LONG);
                        toast1.show();
                    }
                });
            }
        });
    }

    private void selectCardColor(CardViewHolder holder, String prov) {
        switch (prov) {
            case "Visa":
                holder.card.setCardBackgroundColor(Color.parseColor("#4048BD"));
                break;
            case "Amex":
                holder.card.setCardBackgroundColor(Color.parseColor("#4d4f53"));
                break;
            case "Master Card":
                holder.card.setCardBackgroundColor(Color.parseColor("#EB001B"));
                break;
            default:
                holder.card.setCardBackgroundColor(Color.parseColor("#000000"));
                break;
        }
    }

    private String separateString(String numeroTarjeta) {
        String s1 = numeroTarjeta.substring(0, 4);
        String s2 = numeroTarjeta.substring(4, 8);
        String s3 = numeroTarjeta.substring(8, 12);
        String s4 = numeroTarjeta.substring(12, 16);
        return s1 + " " + s2 + " " + s3 + " " + s4;
    }

    @Override
    public int getItemCount() {
        return tarjetas.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder{
        TextView number;
        TextView name;
        TextView provider;
        CardView card;
        TextView statusCard;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.numberText);
            name = itemView.findViewById(R.id.nameText);
            provider = itemView.findViewById(R.id.proveedorText);
            card = itemView.findViewById(R.id.cardView);
            statusCard = itemView.findViewById(R.id.statusCard);
        }
    }
}
