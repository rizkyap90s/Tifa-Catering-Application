package com.wg.tifacatering.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.wg.tifacatering.R;
import com.wg.tifacatering.model.ModelCustomOrder;
import com.wg.tifacatering.model.ModelDateTime;
import com.wg.tifacatering.model.ModelHistory;
import com.wg.tifacatering.model.ModelMenu;
import com.wg.tifacatering.model.ModelPickAddress;

public class PaymentActivity extends AppCompatActivity {
    public static final String KEY_MENU = "gegrgrgv";
    public static final String KEY_ADDRESS = "kfefesy";
    public static final String KEY_TIME = "khhasefa";
    public static final String KEY_JUMLAH = "ffhasefa";
    public static final String KEY_TOTAL_BAYAR = "ffhawhuwheuwesefa";


    public static final String KEY_HISTORY = "ffdhwasefa";


    MaterialCardView cardViewDetailOrder;
    LinearLayout linDetail, linBack;
    TextView customerName, customerPhone, customerAddress;
    TextView tvDetail, namaPaket, jenisPaket, isiPaket, txtMinimalPesan, tanggalAntar, jamAntar, minimalPesan, hargaSatuan,jumlahPesan, biayaKirim, totalBayar, tfrBayar;
    ImageView imgDetail, bgWhite;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        setTitle("Payment");
        init();
        showHideDetailOrder();
        getDataIntent();
        backToMenu();
    }

    private void backToMenu() {
        linBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PaymentActivity.this, MainActivity.class));
            }
        });
    }

    private void getDataIntent() {
        progressBar.setVisibility(View.VISIBLE);
        bgWhite.setVisibility(View.VISIBLE);

        ModelDateTime modelDateTime = getIntent().getParcelableExtra(KEY_TIME);
        ModelMenu modelMenu = getIntent().getParcelableExtra(KEY_MENU);
        ModelPickAddress modelPickAddress = getIntent().getParcelableExtra(KEY_ADDRESS);
        int jumlah = getIntent().getIntExtra(KEY_JUMLAH, 0);
        int jumlahBayar = getIntent().getIntExtra(KEY_TOTAL_BAYAR, 1);

        ModelHistory modelHistory = getIntent().getParcelableExtra(KEY_HISTORY);

        if (getIntent().getParcelableExtra(KEY_TIME) != null){
            namaPaket.setText(": "+modelMenu.getNama_paket());
            jenisPaket.setText(": "+modelMenu.getGrup_paket());
            isiPaket.setText(": "+modelMenu.getIsi_paket());
            tanggalAntar.setText(": "+modelDateTime.getDate());
            jamAntar.setText(": "+modelDateTime.getTime());
            hargaSatuan.setText(": "+modelMenu.getHarga_paket());
            jumlahPesan.setText(": "+jumlah);
            minimalPesan.setText(": "+modelMenu.getMinimal_pesan());
            totalBayar.setText(": "+jumlahBayar);
            tfrBayar.setText("Rp."+jumlahBayar);
            customerAddress.setText(": "+modelPickAddress.getJalan()+", "+modelPickAddress.getRtrw()+", "+modelPickAddress.getKec()+", "+modelPickAddress.getKab());
        }
        else {
            namaPaket.setText(": "+modelHistory.getNama_paket());
            jenisPaket.setText(": "+modelHistory.getJenis_paket());
            isiPaket.setText(": "+modelHistory.getIsi_paket());
            tanggalAntar.setText(": "+modelHistory.getTanggal_kirim());
            jamAntar.setText(": "+modelHistory.getWaktu_kirim());
            hargaSatuan.setText(": "+modelHistory.getHarga_paket());
            jumlahPesan.setText(": "+modelHistory.getJumlah_pesan());
            minimalPesan.setText(": "+modelHistory.getMinimal_pesan());
            totalBayar.setText(": "+jumlahBayar);
            tfrBayar.setText("Rp."+jumlahBayar);
            customerAddress.setText(": "+modelHistory.getAlamat_kirim());
        }
        //FB
        firebaseFirestore.collection("users")
                .document(firebaseAuth.getUid())
                .collection("data")
                .document("profil")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                   DocumentSnapshot doc = task.getResult();
                   String nama = doc.getString("name");
                   String number = doc.getString("number");
                   customerName.setText(": "+nama);
                   customerPhone.setText(": "+number);

                    progressBar.setVisibility(View.GONE);
                    bgWhite.setVisibility(View.GONE);
                }
            }
        });
    }

    private void showHideDetailOrder() {
        linDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardViewDetailOrder.getVisibility() == View.VISIBLE){
                    cardViewDetailOrder.setVisibility(View.GONE);
                    tvDetail.setText("Tampilkan Detail");
                    imgDetail.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
                }
                else {
                    cardViewDetailOrder.setVisibility(View.VISIBLE);
                    tvDetail.setText("Sembunyikan Detail");
                    imgDetail.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
                }
            }
        });
    }

    private void init() {
        tvDetail = findViewById(R.id.tv_detail);
        cardViewDetailOrder = findViewById(R.id.card_order);
        linDetail = findViewById(R.id.lin_detail);
        namaPaket = findViewById(R.id.payment_nama_pesanan);
        jenisPaket = findViewById(R.id.payment_jenis_pesanan);
        isiPaket = findViewById(R.id.payment_isi_pesanan);
        tanggalAntar = findViewById(R.id.payment_date);
        jamAntar = findViewById(R.id.payment_time);
        minimalPesan = findViewById(R.id.payment_minimal_pesan);
        hargaSatuan = findViewById(R.id.payment_harga_satuan);
        totalBayar = findViewById(R.id.payment_total_bayar);
        jumlahPesan = findViewById(R.id.payment_jumlah_pesan);
        tfrBayar = findViewById(R.id.trf_jumlah_bayar);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        customerName = findViewById(R.id.payment_pemesan);
        customerPhone = findViewById(R.id.payment_hp);
        progressBar = findViewById(R.id.prog_pay);
        bgWhite = findViewById(R.id.bg_white_pay);
        customerAddress = findViewById(R.id.payment_alamat);
        imgDetail = findViewById(R.id.img_detail);
        txtMinimalPesan = findViewById(R.id.txt_minimal_pesan);
        linBack = findViewById(R.id.lin_to_pay);
    }


}
