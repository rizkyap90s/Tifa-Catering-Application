package com.wg.tifacatering.activity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.wg.tifacatering.R;

public class WaitingApprovalActivity extends AppCompatActivity {

    TextView namaPaket, isiPaket, jenisPaket, tanggalAntar, jamAntar, hargaSatuan, jumlahPesan, minimalPesan, totalBayar, customerNumber, customerNama, customerAddress;
    FirebaseFirestore db;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_approval);






//        String nama_paket = doc.getString("nama_paket");
//        String jenis_paket = doc.getString("jenis_paket");
//        String isi_paket = doc.getString("isi_paket");
//        String tanggal_antar = doc.getString("tanggal_kirim");
//        String jam_antar = doc.getString("waktu_kirim");
//        String harga_satuan = doc.getString("harga_paket");
//        String jumlah_pesan = doc.getString("jumlah_pesan");
//        String minimal_pesan = doc.getString("minimal_pesan");
//        String total_bayar = doc.getString("total_bayar");
//        String nama_customer = doc.getString("nama_customer");
//        String nomor_customer = doc.getString("nomor_customer");
//        String alamat_customer = doc.getString("alamat_kirim");
//
//        namaPaket.setText(": "+nama_paket);
//        jenisPaket.setText(": "+jenis_paket);
//        isiPaket.setText(": "+isi_paket);
//        tanggalAntar.setText(": "+tanggal_antar);
//        jamAntar.setText(": "+jam_antar);
//        hargaSatuan.setText(": "+harga_satuan);
//        jumlahPesan.setText(": "+jumlah_pesan);
//        minimalPesan.setText(": "+minimal_pesan);
//        totalBayar.setText(": "+total_bayar);
//        customerNama.setText(": "+ nama_customer);
//        customerNumber.setText(": "+nomor_customer);
//        customerAddress.setText(": "+alamat_customer);
    }



}









