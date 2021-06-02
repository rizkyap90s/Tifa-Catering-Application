package com.wg.tifacatering.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.wg.tifacatering.R;
import com.wg.tifacatering.fragment.DatePickerFragment;
import com.wg.tifacatering.fragment.TimePickerFragment;
import com.wg.tifacatering.model.ModelPickAddress;
import com.wg.tifacatering.model.ModelDateTime;
import com.wg.tifacatering.model.ModelMenu;
import com.wg.tifacatering.model.ModelTotalBayar;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wg.tifacatering.activity.PaymentActivity.KEY_ADDRESS;
import static com.wg.tifacatering.activity.PaymentActivity.KEY_JUMLAH;
import static com.wg.tifacatering.activity.PaymentActivity.KEY_MENU;
import static com.wg.tifacatering.activity.PaymentActivity.KEY_TIME;
import static com.wg.tifacatering.activity.PaymentActivity.KEY_TOTAL_BAYAR;


public class DetailOrderActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    public static final String KEY_DETAIL = "key";

    TextView namaPesanan, jenisPesanan, hargaSatuanPesanan, isiPesanan, getJalan, getDate, getTime, totalBayar, biayaKirim, minimalPesan;
    EditText jumlah;
    ImageButton tambah, kurang;
    ImageView bgDetail, imgOrder;
    ProgressBar progDetail;
    LinearLayout clickInputAddress;
    LinearLayout linToPayment, datePicker, timePicker;
    ModelDateTime modelDateTime = new ModelDateTime();
    FirebaseStorage storage;
    List<ModelPickAddress> listPickAddress = new ArrayList<>();
    List<ModelTotalBayar> modelTotalBayar = new ArrayList<>();


    FirebaseFirestore db;
    private FirestoreRecyclerAdapter adapter;
    FirebaseAuth auth;
    RecyclerView rvPickAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);

        init();
        setDataOrder();
        inputJumlah();
        toPickAddress();
        setDateTime();
        toPayment();
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

    private void toPayment() {
        linToPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mJalan = getJalan.getText().toString();
                String mDate = getDate.getText().toString();
                String mTime = getTime.getText().toString();
                String mJumlah = jumlah.getText().toString();
                int intJumlah = Integer.parseInt(mJumlah);
                final ModelMenu modelMenu = getIntent().getExtras().getParcelable(KEY_DETAIL);

                if (mJalan.equals("Tap to input address") || mDate.equals("Tap to input date") || mTime.equals("Tap to input time")){
                    Toast.makeText(DetailOrderActivity.this, "Gagal, harap memasukan data dengan benar", Toast.LENGTH_LONG).show();
                }
                else {
                    if (intJumlah < modelMenu.getMinimal_pesan()){
                        Toast.makeText(DetailOrderActivity.this, "Gagal, periksa minimal pesanan", Toast.LENGTH_LONG).show();
                    }
                    else {
                        AlertDialog.Builder sureAlert = new AlertDialog.Builder(DetailOrderActivity.this);
                        sureAlert
                                .setMessage("Anda tidak dapat mengubah pesanan kembali didalam Pembayaran")
                                .setTitle("Apakah anda yakin ?")
                                .setPositiveButton("Yakin", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        //get namaCust. di users
                                        db.collection("users")
                                                .document(auth.getUid())
                                                .collection("data")
                                                .document("profil")
                                                .get()
                                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()){
                                                            final DocumentSnapshot doc = task.getResult();

                                                            //ngecek order -> nameCust -> apakah sudah ada?
                                                            db.collection("pending_order")
                                                                    .document(auth.getUid())
                                                                    .get()
                                                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                            if (task.isSuccessful()){
                                                                                DocumentSnapshot doc2 = task.getResult();
                                                                                //KALO ADA
                                                                                if (doc2.getString("nama_paket") != null){
                                                                                    Toast.makeText(DetailOrderActivity.this, "Anda belum menyelesaikan transaksi sebelumnya", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                                else {
                                                                                    //KALO BELUM ADA
                                                                                    String getJumlah = jumlah.getText().toString();
                                                                                    int intJumlah = Integer.parseInt(getJumlah);
                                                                                    Intent toPay = new Intent(DetailOrderActivity.this, PaymentActivity.class);
                                                                                    toPay.putExtra(KEY_MENU, modelMenu);
                                                                                    toPay.putExtra(KEY_TIME, modelDateTime);
                                                                                    toPay.putExtra(KEY_ADDRESS, listPickAddress.get(listPickAddress.size()-1));
                                                                                    toPay.putExtra(KEY_JUMLAH, intJumlah);
                                                                                    toPay.putExtra(KEY_TOTAL_BAYAR, modelTotalBayar.get(modelTotalBayar.size()-1).getTotalBayar());


                                                                                    //add data order to db
                                                                                    final Map<String, Object> user = new HashMap<>();
                                                                                    user.put("nama_paket", modelMenu.getNama_paket());
                                                                                    user.put("jenis_paket", modelMenu.getGrup_paket());
                                                                                    user.put("harga_paket", modelMenu.getHarga_paket());
                                                                                    user.put("isi_paket", modelMenu.getIsi_paket());
                                                                                    user.put("jumlah_pesan", intJumlah);
                                                                                    user.put("minimal_pesan", modelMenu.getMinimal_pesan());
                                                                                    user.put("waktu_kirim", modelDateTime.getTime());
                                                                                    user.put("tanggal_kirim", modelDateTime.getDate());
                                                                                    user.put("total_bayar", modelTotalBayar.get(modelTotalBayar.size()-1).getTotalBayar());
                                                                                    user.put("nama_customer", doc.getString("name"));
                                                                                    user.put("nomor_customer", doc.getString("number"));
                                                                                    user.put("uid_customer", auth.getUid());
                                                                                    String alamatKirim = listPickAddress.get(listPickAddress.size()-1).getJalan()
                                                                                            +", "+listPickAddress.get(listPickAddress.size()-1).getRtrw()
                                                                                            +", "+listPickAddress.get(listPickAddress.size()-1).getKec()
                                                                                            +", "+listPickAddress.get(listPickAddress.size()-1).getKab()
                                                                                            +", "+listPickAddress.get(listPickAddress.size()-1).getNote();
                                                                                    user.put("alamat_kirim", alamatKirim);
                                                                                    user.put("status", "waiting payment");

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
                                                                                                        db.collection("pending_order")
                                                                                                                .document(auth.getUid())
                                                                                                                .set(user);
                                                                                                    }
                                                                                                }
                                                                                            });
                                                                                    startActivity(toPay);
                                                                                    DetailOrderActivity.this.finish();
                                                                                }
                                                                            }
                                                                        }
                                                                    });
                                                        }
                                                    }
                                                });

                                    }
                                });
                        Dialog dialog = sureAlert.create(); dialog.show();
                    }
                }
            }
        });
    }

    private void toPickAddress() {

        clickInputAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder alertPickAddress = new AlertDialog.Builder(DetailOrderActivity.this);
                final View view = getLayoutInflater().inflate(R.layout.alert_choose_address, null);

                db  = FirebaseFirestore.getInstance();
                auth = FirebaseAuth.getInstance();
                rvPickAddress = view.findViewById(R.id.rv_choose_address);
                rvPickAddress.setLayoutManager(new LinearLayoutManager(DetailOrderActivity.this));

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

                        holder.fee.setText(ongkir);
                        holder.nama.setText(nama);
                        holder.jalan.setText(jalan+", "+rtrw+", "+kec+", "+kab);
                        holder.cardPick.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                modelTotalBayar.clear();
                                getJalan.setText(jalan+", "+rtrw+", "+kec+", "+kab);
                                biayaKirim.setText(" :"+ongkir);
                                int ongkos = Integer.parseInt(ongkir);
                                int total = ongkos + hitungJumlah();
                                totalBayar.setText(" :"+total);
                                modelTotalBayar.add(new ModelTotalBayar(total));
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
                        .setPositiveButton("Pilih", null)
                        .setNeutralButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(DetailOrderActivity.this, AddressActivity.class));
                            }
                        });

                Dialog dialog = alertPickAddress.create(); dialog.show();
            }
        });
    }

    public class FriendsHolder extends RecyclerView.ViewHolder {
        TextView nama, jalan, fee;
        CardView cardPick;
        RelativeLayout relativeLayout;

        public FriendsHolder(View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.input_nama_alamat);
            jalan = itemView.findViewById(R.id.input_jalan_alamat);
            cardPick = itemView.findViewById(R.id.cardPick);
            relativeLayout = itemView.findViewById(R.id.relative_row);
            fee = itemView.findViewById(R.id.ongkir);
        }
    }

    private void setDataOrder() {
        try {
            ModelMenu modelMenu = getIntent().getExtras().getParcelable(KEY_DETAIL);

            namaPesanan.setText(": "+modelMenu.getNama_paket());
            jenisPesanan.setText(": "+modelMenu.getGrup_paket());
            isiPesanan.setText(": "+modelMenu.getIsi_paket());
            hargaSatuanPesanan.setText(": "+modelMenu.getHarga_paket());
            biayaKirim.setText(": 0");
            minimalPesan.setText(": "+modelMenu.getMinimal_pesan());
            jumlah.setText(""+modelMenu.getMinimal_pesan());

            final StorageReference ref = storage.getReferenceFromUrl("gs://tifacatering-8828a.appspot.com/menu_utama_tifa/")
                    .child(modelMenu.getImage_paket());
            try {
                final File file = File.createTempFile("menu_utama_tifa",".jpg");
                ref.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        final Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                        Glide.with(DetailOrderActivity.this).load(bitmap).into(imgOrder);
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int hitungJumlah(){
        ModelMenu modelMenu = getIntent().getExtras().getParcelable(KEY_DETAIL);
        String getJumlah = jumlah.getText().toString();
        int getJumlahInt = Integer.parseInt(getJumlah);
        final int total = (getJumlahInt * modelMenu.getHarga_paket());
        totalBayar.setText(": "+total);
        return total;

    }

    private void inputJumlah() {
        hitungJumlah();

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getJumlah = jumlah.getText().toString();
                int intJumlah = Integer.parseInt(getJumlah);
                int jumlahSekarang = intJumlah + 1;
                jumlah.setText(jumlahSekarang+"");
            }
        });

        kurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getJumlah = jumlah.getText().toString();
                int intJumlah = Integer.parseInt(getJumlah);
                int jumlahSekarang = intJumlah - 1;
                jumlah.setText(jumlahSekarang+"");
            }
        });

        jumlah.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String getJumlah = jumlah.getText().toString();
                if(getJumlah.isEmpty()){
                    totalBayar.setText(": 0");
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
        storage = FirebaseStorage.getInstance();
        getJalan = findViewById(R.id.get_jalan);
        namaPesanan = findViewById(R.id.detail_nama_pesanan);
        jenisPesanan = findViewById(R.id.detail_jenis_pesanan);
        isiPesanan = findViewById(R.id.detail_isi_pesanan);
        hargaSatuanPesanan = findViewById(R.id.detail_harga_satuan);
        jumlah = findViewById(R.id.detail_jumlah);
        tambah = findViewById(R.id.tambah);
        kurang = findViewById(R.id.kurang);
        clickInputAddress = findViewById(R.id.clickInputAddress);
        linToPayment = findViewById(R.id.lin_to_pay);
        datePicker = findViewById(R.id.pick_date);
        timePicker = findViewById(R.id.pick_time);
        getDate = findViewById(R.id.get_date);
        getTime = findViewById(R.id.get_time);
        totalBayar = findViewById(R.id.detail_total_bayar);
        biayaKirim = findViewById(R.id.detail_biaya_kirim);
        minimalPesan = findViewById(R.id.detail_minimal_pesan);
        bgDetail = findViewById(R.id.bg_detail);
        progDetail = findViewById(R.id.prog_detail);
        imgOrder = findViewById(R.id.img_order);
    }
}