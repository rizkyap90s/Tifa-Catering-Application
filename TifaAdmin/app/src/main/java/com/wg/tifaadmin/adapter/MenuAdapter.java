package com.wg.tifaadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wg.tifaadmin.R;
import com.wg.tifaadmin.model.ModelMenu;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyHolder> {

    Context context;
    List<ModelMenu> list;

    public MenuAdapter(Context context, List<ModelMenu> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.name.setText(list.get(position).getNama_paket());
        holder.img.setImageResource(R.drawable.tifa_logo2);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView img;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nama);
            img = itemView.findViewById(R.id.img_menu);

        }
    }
}
