package com.wg.tifaadmin.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.wg.tifaadmin.R;
import com.wg.tifaadmin.model.ModelApprovedOrder;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ApprovedFragment extends Fragment {

    FirebaseFirestore db;
    FirebaseAuth auth;
    RecyclerView rvApproved;
    FirestoreRecyclerAdapter adapter;

    public ApprovedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_approved, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvApproved = view.findViewById(R.id.rv_approve);
        db = FirebaseFirestore.getInstance();
        rvApproved.setLayoutManager(new LinearLayoutManager(getContext()));

        Query query = db.collection("approved_order");
        FirestoreRecyclerOptions<ModelApprovedOrder> respone = new FirestoreRecyclerOptions.Builder<ModelApprovedOrder>()
               .setQuery(query, ModelApprovedOrder.class)
               .build();
        adapter = new FirestoreRecyclerAdapter<ModelApprovedOrder, MyHolder>(respone) {
            @Override
            protected void onBindViewHolder(@NonNull MyHolder holder, int position, @NonNull final ModelApprovedOrder model) {
                holder.namaCustomer.setText(model.getNama_customer());
                holder.clickMe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder detailOrder = new AlertDialog.Builder(getContext());
                        View view1 = getLayoutInflater().inflate(R.layout.alert_detail_order, null);

                        final TextView namaPaket, jenisPaket, isiPaket,alamatAntar, waktuAntar, tanggalAntar, minimalPesan, hargaSatuan, jumlahPesan, biayaKirim, totalbayar;

                        namaPaket = view1.findViewById(R.id.detail_nama_pesanan);
                        jenisPaket = view1.findViewById(R.id.detail_jenis_pesanan);
                        isiPaket = view1.findViewById(R.id.detail_isi_pesanan);
                        waktuAntar = view1.findViewById(R.id.detail_time);
                        tanggalAntar = view1.findViewById(R.id.detail_date);
                        alamatAntar = view1.findViewById(R.id.detail_alamat);
                        minimalPesan = view1.findViewById(R.id.detail_minimal_pesan);
                        hargaSatuan = view1.findViewById(R.id.detail_harga_satuan);
                        jumlahPesan = view1.findViewById(R.id.detail_jumlah_pesan);
                        biayaKirim = view1.findViewById(R.id.detail_biaya_kirim);
                        totalbayar = view1.findViewById(R.id.detail_total_bayar);

                        Toast.makeText(getActivity(), model.getNama_customer(), Toast.LENGTH_SHORT).show();

                        namaPaket.setText(model.getNama_paket());
                        jenisPaket.setText(model.getJenis_paket());
                        isiPaket.setText(model.getIsi_paket());
                        alamatAntar.setText(model.getAlamat_kirim());
                        waktuAntar.setText(model.getWaktu_kirim());
                        tanggalAntar.setText(model.getTanggal_kirim());
                        minimalPesan.setText(model.getMinimal_pesan()+"");
                        hargaSatuan.setText(model.getHarga_paket()+"");
                        jumlahPesan.setText(model.getJumlah_pesan()+"");
                        biayaKirim.setText("20000");
                        int total = (model.getHarga_paket() * model.getJumlah_pesan()) + 20000;
                        totalbayar.setText(""+total);

                        detailOrder
                                .setView(view1)
                                .setTitle("Detail")
                                .setPositiveButton("Selesai", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Map<String, Object> user = new HashMap<>();
                                        user.put("nama_paket", model.getNama_paket());
                                        user.put("jenis_paket", model.getJenis_paket());
                                        user.put("harga_paket", model.getHarga_paket());
                                        user.put("isi_paket", model.getIsi_paket());
                                        user.put("jumlah_pesan", model.getJumlah_pesan());
                                        user.put("minimal_pesan", model.getMinimal_pesan());
                                        user.put("waktu_kirim", model.getWaktu_kirim());
                                        user.put("tanggal_kirim", model.getTanggal_kirim());
                                        user.put("nama_customer", model.getNama_customer());
                                        user.put("nomor_customer", model.getNomor_customer());
                                        user.put("uid_customer", model.getUid_customer());
                                        user.put("alamat_kirim", model.getAlamat_kirim());
                                        user.put("status", "Paid");
                                        user.put("total_bayar", model.getTotal_bayar());

                                        int random = (int) (Math.random() * 100);
                                        db.collection("done_order")
                                                .document(model.getUid_customer()+random)
                                                .set(user);

                                        db.collection("approved_order")
                                                .document(model.getUid_customer())
                                                .delete();
                                    }
                                });
                        Dialog dialog = detailOrder.create(); dialog.show();

                    }
                });
            }

            @NonNull
            @Override
            public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view1 = LayoutInflater.from(getContext()).inflate(R.layout.custom_item_request, parent, false);
                return new MyHolder(view1);
            }
        };
        adapter.notifyDataSetChanged();
        rvApproved.setAdapter(adapter);
        adapter.startListening();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        TextView namaCustomer;
        CardView clickMe;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            namaCustomer = itemView.findViewById(R.id.nama_customer);
            clickMe = itemView.findViewById(R.id.cardPick);

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.startListening();
    }
}
