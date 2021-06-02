package com.wg.tifacatering.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wg.tifacatering.R;
import com.wg.tifacatering.model.ModelSetAddress;

import java.util.List;


public class AdapterAddress extends RecyclerView.Adapter<AdapterAddress.MyHolder> {
    List<ModelSetAddress> modelSetAddresses;
    Context context;

    public AdapterAddress(List<ModelSetAddress> modelSetAddresses, Context context) {
        this.modelSetAddresses = modelSetAddresses;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custom_address, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.txtnama.setText(modelSetAddresses.get(holder.getAdapterPosition()).getNama());
    }

    @Override
    public int getItemCount() {
        return modelSetAddresses.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView txtnama, txttempat;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            txtnama = itemView.findViewById(R.id.nama_alamat);
            txttempat = itemView.findViewById(R.id.tempat_alamat);
        }
    }
}
