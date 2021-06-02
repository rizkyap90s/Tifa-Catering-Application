package com.wg.tifacatering.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.wg.tifacatering.R;
import com.wg.tifacatering.fragment.DatePickerFragment;
import com.wg.tifacatering.fragment.TimePickerFragment;
import com.wg.tifacatering.model.ModelCustomOrder;
import com.wg.tifacatering.model.ModelDateTime;
import com.wg.tifacatering.model.ModelPickAddress;
import com.wg.tifacatering.model.ModelTotalBayar;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wg.tifacatering.activity.KustomActivity.KEY_CUSTOM_REQ;

public class CustomDetailOrderActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    TextView customIsiPesanan, jumlahMinimal,hargaSatuan, biayaKirim, getJalan, jumlahBayar, tanggalKirim, waktuKirim, getDate,getTime;
    EditText jumlahPesanan;
    ImageButton tambah,kurang;
    LinearLayout datePicker, timePicker, clickInputAddress, linToPayment;
    ModelDateTime modelDateTime = new ModelDateTime();
    List<ModelPickAddress> listPickAddress = new ArrayList<>();
    List<ModelTotalBayar> modelTotalBayarArrayList = new ArrayList<>();

    FirebaseFirestore db;
    FirebaseAuth auth;
    RecyclerView rvPickAddress;
    FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_detail_order);
        init();
        setDataRequestOrder();
        inputJumlah();
        setDateTime();
        toPickAddress();
        toPayment();
    }

    private void toPayment() {
        linToPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getAlamat = getJalan.getText().toString();
                String getDate = tanggalKirim.getText().toString();
                String getTime = waktuKirim.getText().toString();
                String getJumlah = jumlahPesanan.getText().toString();
                int intGetJumlah = Integer.parseInt(getJumlah);
                int MinimalPesan = 10;

                if (getAlamat.equals("Tap to input address") || getDate.equals("Tap to input date") || getTime.equals("Tap to input time")){
                    Toast.makeText(CustomDetailOrderActivity.this, "Gagal, harap memasukan data dengan benar", Toast.LENGTH_LONG).show();
                }
                else {
                    if (MinimalPesan > intGetJumlah){
                        Toast.makeText(CustomDetailOrderActivity.this, "Gagal, periksa minimal pesanan", Toast.LENGTH_LONG).show();
                    }
                    else {
                        AlertDialog.Builder isAgree = new AlertDialog.Builder(CustomDetailOrderActivity.this);
                        isAgree
                                .setTitle("Apakah anda yakin?")
                                .setMessage("Anda tidak dapat mengubah pesanan kembali didalam Pembayaran")
                                .setPositiveButton("Yakin", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                            //gett namaCust.
                                            db.collection("users")
                                                .document(auth.getUid())
                                                .collection("data")
                                                .document("profil")
                                                .get()
                                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()){
                                                            //ngecek order->nameCust. -> apakah sudah ada
                                                            final DocumentSnapshot doc = task.getResult();
                                                            db.collection("request_order")
                                                                    .document(auth.getUid())
                                                                    .get()
                                                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                            DocumentSnapshot doc2 = task.getResult();
                                                                            //KALO ADA
                                                                            if (doc2.getString("nama_paket") != null){
                                                                                Toast.makeText(CustomDetailOrderActivity.this, "Anda belum menyelesaikan transaksi sebelumnya", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                            else {
                                                                                //KALO BELUM ADA
                                                                                ModelCustomOrder modelCustomOrder = getIntent().getExtras().getParcelable(KEY_CUSTOM_REQ);
                                                                                String getJumlah = jumlahPesanan.getText().toString();
                                                                                int intJumlah = Integer.parseInt(getJumlah);
                                                                                Intent toPay = new Intent(CustomDetailOrderActivity.this, WaitingApprovalActivity.class);
//                                                                                toPay.putExtra(CUSTOM_KEY_MENU, modelCustomOrder);
//                                                                                toPay.putExtra( CUSTOM_KEY_TIME, modelDateTime);
//                                                                                toPay.putExtra(CUSTOM_KEY_ADDRESS, listPickAddress.get(listPickAddress.size()-1));
//                                                                                toPay.putExtra(CUSTOM_KEY_JUMLAH, intJumlah);
//                                                                                toPay.putExtra(CUSTOM_KEY_CUSTOMER, doc.getString("name"));
//                                                                                toPay.putExtra(CUSTOM_KEY_NUMBER, doc.getString("number"));

                                                                                //add data order to db
                                                                                final Map<String, Object> user = new HashMap<>();
                                                                                user.put("nama_paket", "Custom Order");
                                                                                user.put("jenis_paket", "Custom Order");
                                                                                user.put("harga_paket", modelCustomOrder.getHargaPesanan());
                                                                                user.put("isi_paket", modelCustomOrder.getIsiPesanan());
                                                                                user.put("jumlah_pesan", intJumlah);
                                                                                user.put("minimal_pesan", 10);
                                                                                user.put("waktu_kirim", modelDateTime.getTime());
                                                                                user.put("tanggal_kirim", modelDateTime.getDate());
                                                                                user.put("nama_customer", doc.getString("name"));
                                                                                user.put("nomor_customer", doc.getString("number"));
                                                                                user.put("uid_customer", auth.getUid());
                                                                                String alamatKirim = listPickAddress.get(listPickAddress.size()-1).getJalan()
                                                                                        +", "+listPickAddress.get(listPickAddress.size()-1).getRtrw()
                                                                                        +", "+listPickAddress.get(listPickAddress.size()-1).getKec()
                                                                                        +", "+listPickAddress.get(listPickAddress.size()-1).getKab()
                                                                                        +", "+listPickAddress.get(listPickAddress.size()-1).getNote();
                                                                                user.put("alamat_kirim", alamatKirim);
                                                                                user.put("status", "Waiting Approval");
                                                                                int totalBayar = (intJumlah * modelCustomOrder.getHargaPesanan()) + 20000;
                                                                                user.put("total_bayar", modelTotalBayarArrayList.get(modelTotalBayarArrayList.size()-1).getTotalBayar());

                                                                                //get namacusst.
                                                                                db.collection("users")
                                                                                        .document(auth.getUid())
                                                                                        .collection("data")
                                                                                        .document("profil")
                                                                                        .get()
                                                                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                                                if (task.isSuccessful()){
                                                                                                    DocumentSnapshot doc = task.getResult();
                                                                                                    //push
                                                                                                    db.collection("request_order")
                                                                                                            .document(auth.getUid())
                                                                                                            .set(user);
                                                                                                }
                                                                                            }
                                                                                        });
                                                                                startActivity(toPay);
                                                                                CustomDetailOrderActivity.this.finish();
                                                                            }
                                                                        }
                                                                    });
                                                        }
                                                    }
                                                });
                                    }
                                });
                        Dialog dialog = isAgree.create();dialog.show();
                    }
                }
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String getCurrentDate = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        modelDateTime.setDate(getCurrentDate);
        getDate.setText(modelDateTime.getDate());

    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        modelDateTime.setTime(hourOfDay+":"+ minute);
        getTime.setText(modelDateTime.getTime());
    }
    private void setDateTime() {
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "Date Picker");
            }
        });
        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(),"Time Picker");
            }
        });
    }

    private void toPickAddress() {

        clickInputAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder alertPickAddress = new AlertDialog.Builder(CustomDetailOrderActivity.this);
                final View view = getLayoutInflater().inflate(R.layout.alert_choose_address, null);

                rvPickAddress = view.findViewById(R.id.rv_choose_address);
                rvPickAddress.setLayoutManager(new LinearLayoutManager(CustomDetailOrderActivity.this));

                Query query = db.collection("users").document(auth.getUid()).collection("alamat");

                FirestoreRecyclerOptions<ModelPickAddress> response = new FirestoreRecyclerOptions.Builder<ModelPickAddress>()
                        .setQuery(query, ModelPickAddress.class)
                        .build();

                adapter = new FirestoreRecyclerAdapter<ModelPickAddress, FriendsHolder>(response) {
                    @Override
                    public void onBindViewHolder(final FriendsHolder holder, final int position, final ModelPickAddress model) {
                        final String nama = model.getNama();
                        final String jalan = model.getJalan();
                        final String rtrw = model.getRtrw();
                        final String kec = model.getKec();
                        final String kab = model.getKab();
                        final String note = model.getNote();
                        final String ongkir = model.getOngkir();

                        holder.nama.setText(nama);
                        holder.jalan.setText(jalan+", "+rtrw+", "+kec+", "+kab);
                        holder.cardPick.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                modelTotalBayarArrayList.clear();
                                getJalan.setText(jalan+", "+rtrw+", "+kec+", "+kab);
                                biayaKirim.setText(" :"+ongkir);
                                int ongkos = Integer.parseInt(ongkir);
                                int total = ongkos + hitungJumlah();
                                jumlahBayar.setText(" :"+total);
                                modelTotalBayarArrayList.add(new ModelTotalBayar(total));
                                listPickAddress.add(new ModelPickAddress(nama, jalan, rtrw, kec, kab, note, ongkir));
                            }
                        });
                    }

                    @Override
                    public FriendsHolder onCreateViewHolder(ViewGroup group, int i) {
                        View view = LayoutInflater.from(group.getContext())
                                .inflate(R.layout.item_custom_input_address, group, false);
                        return new FriendsHolder(view);
                    }

                    @Override
                    public void onError(FirebaseFirestoreException e) {
                        Log.e("error", e.getMessage());
                    }
                };
                adapter.notifyDataSetChanged();
                rvPickAddress.setAdapter(adapter);
                adapter.startListening();

                alertPickAddress
                        .setMessage("Pilih alamat Tujuan")
                        .setView(view)
                        .setCancelable(false)
                        .setPositiveButton("Pilih", null);
                Dialog dialog = alertPickAddress.create(); dialog.show();
            }
        });
    }

    public class FriendsHolder extends RecyclerView.ViewHolder {
        TextView nama, jalan;
        CardView cardPick;
        RelativeLayout relativeLayout;

        public FriendsHolder(View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.input_nama_alamat);
            jalan = itemView.findViewById(R.id.input_jalan_alamat);
            cardPick = itemView.findViewById(R.id.cardPick);
            relativeLayout = itemView.findViewById(R.id.relative_row);
        }
    }

    private void setDataRequestOrder() {
        ModelCustomOrder modelCustomOrder = getIntent().getExtras().getParcelable(KEY_CUSTOM_REQ);
        customIsiPesanan.setText(modelCustomOrder.getIsiPesanan());
        jumlahMinimal.setText(": "+10);
        biayaKirim.setText(": "+20000);
        hargaSatuan.setText(": "+modelCustomOrder.getHargaPesanan());

    }
    private int hitungJumlah(){
        ModelCustomOrder modelCustomOrder = getIntent().getExtras().getParcelable(KEY_CUSTOM_REQ);
        String getJumlah = jumlahPesanan.getText().toString();
        int getJumlahInt = Integer.parseInt(getJumlah);
        int total = (getJumlahInt * modelCustomOrder.getHargaPesanan());
        jumlahBayar.setText(": "+total);
        return total;
    }

    private void inputJumlah() {
        hitungJumlah();
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getJumlah = jumlahPesanan.getText().toString();
                int intJumlah = Integer.parseInt(getJumlah);
                int jumlahSekarang = intJumlah + 1;
                jumlahPesanan.setText(jumlahSekarang+"");
            }
        });

        kurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getJumlah = jumlahPesanan.getText().toString();
                int intJumlah = Integer.parseInt(getJumlah);
                int jumlahSekarang = intJumlah - 1;
                jumlahPesanan.setText(jumlahSekarang+"");
            }
        });

        jumlahPesanan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String getJumlah = jumlahPesanan.getText().toString();
                if(getJumlah.isEmpty()){
                    jumlahBayar.setText(": 0");
                }
                else {
                    hitungJumlah();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void init() {
        db  = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        auth.getCurrentUser();
        linToPayment = findViewById(R.id.custom_lin_to_pay);
        tambah = findViewById(R.id.custom_tambah);
        kurang = findViewById(R.id.custom_kurang);
        customIsiPesanan = findViewById(R.id.custom_isi);
        jumlahMinimal = findViewById(R.id.custom_minimal_pesan);
        biayaKirim = findViewById(R.id.custom_biaya_kirim);
        jumlahBayar = findViewById(R.id.custom_total_bayar);
        tanggalKirim = findViewById(R.id.custom_get_date);
        waktuKirim = findViewById(R.id.custom_get_time);
        jumlahPesanan = findViewById(R.id.custom_jumlah);
        hargaSatuan = findViewById(R.id.custom_harga_satuan);
        datePicker = findViewById(R.id.custom_pick_date);
        timePicker = findViewById(R.id.custom_pick_time);
        getDate  = findViewById(R.id.custom_get_date);
        getTime = findViewById(R.id.custom_get_time);
        clickInputAddress = findViewById(R.id.custom_clickInputAddress);
        getJalan = findViewById(R.id.custom_get_jalan);
    }
}
