package com.wg.tifacatering.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.wg.tifacatering.R;
import com.wg.tifacatering.activity.DetailOrderActivity;
import com.wg.tifacatering.activity.SignInSignUpActivity;
import com.wg.tifacatering.model.ModelRekomendasi;

import java.io.File;
import java.util.List;

public class AdapterRekomendasi extends RecyclerView.Adapter<AdapterRekomendasi.MyHolder> {

    Context context;
    List<ModelRekomendasi> list;
    FirebaseAuth firebaseAuth;
    FirebaseStorage storage;

    public AdapterRekomendasi(Context context, List<ModelRekomendasi> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AdapterRekomendasi.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_custom_home, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterRekomendasi.MyHolder holder, int position) {
        firebaseAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();

        holder.imgMenu.setImageResource(R.drawable.tifa_logo2);
        holder.namaMenu.setText(list.get(position).getNama_paket());

        final StorageReference ref = storage.getReferenceFromUrl("gs://tifacatering-8828a.appspot.com/menu_utama_tifa/")
                .child(list.get(holder.getAdapterPosition()).getImage_paket());
        try {
            final File file = File.createTempFile("menu_utama_tifa",".jpg");
            ref.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    final Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    Glide.with(context).load(bitmap).into(holder.imgMenu);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alerDetail = new AlertDialog.Builder(context);
                View view = LayoutInflater.from(context).inflate(R.layout.alert_detail_paket,null, false);

                TextView namaPaket, detailPaket, hargapaket, minimaml;
                final ImageView imagePaket;

                detailPaket = view.findViewById(R.id.alert_komposisi_detail);
                namaPaket = view.findViewById(R.id.alert_name_detail);
                hargapaket = view.findViewById(R.id.alert_harga_detail);
                minimaml = view.findViewById(R.id.alert_minimal_detail);
                imagePaket = view.findViewById(R.id.alert_detail_image);

                namaPaket.setText(list.get(holder.getAdapterPosition()).getNama_paket());
                detailPaket.setText(list.get(holder.getAdapterPosition()).getIsi_paket());
                hargapaket.setText("Rp."+list.get(holder.getAdapterPosition()).getHarga_paket());
                minimaml.setText("Minimal pesan: "+list.get(holder.getAdapterPosition()).getMinimal_pesan());

                try {
                    final File file = File.createTempFile("menu_utama_tifa",".jpg");
                    ref.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap1 = BitmapFactory.decodeFile(file.getAbsolutePath());
                            Glide.with(context).load(bitmap1).into(imagePaket);
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }


                alerDetail
                        .setView(view)
                        .setCancelable(true)
                        .setPositiveButton("Buy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (firebaseAuth.getCurrentUser() == null){
                                    AlertDialog.Builder masukDulu = new AlertDialog.Builder(context);
                                    masukDulu
                                            .setMessage("Anda belum Login, Login sekarang ?")
                                            .setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    context.startActivity(new Intent(context, SignInSignUpActivity.class));
                                                }
                                            })
                                            .setCancelable(true);
                                    Dialog masuk = masukDulu.create(); masuk.show();
                                }
                                else {
                                    
                                }
                            }
                        });
                Dialog dialog = alerDetail.create(); dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView imgMenu;
        TextView namaMenu;
        CardView cardView;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            cardView =itemView.findViewById(R.id.cardDetail);
            imgMenu = itemView.findViewById(R.id.img_menu);
            namaMenu = itemView.findViewById(R.id.nama_menu);
        }
    }
}
