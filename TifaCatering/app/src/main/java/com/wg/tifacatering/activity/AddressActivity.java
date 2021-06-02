package com.wg.tifacatering.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.wg.tifacatering.R;

import java.util.HashMap;
import java.util.Map;

public class AddressActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    MaterialCardView alamat1, alamat2;
    TextView namaAlamat1, jalanAlamat1, keckabAlamat1, note1;
    TextView namaAlamat2, jalanAlamat2, keckabAlamat2, note2;
    SwipeRefreshLayout swipeRefreshLayout;
    ImageView bgalamat;
    ProgressBar progAlamat;
    TextView kosong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        setTitle("Alamat");

        init();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                readAlamat();
                readAlamat2();

            }
        });

        readAlamat();
        readAlamat2();
        isiAlamat();
    }

    private void init() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();
        alamat1 = findViewById(R.id.alamat1);
        alamat2 = findViewById(R.id.alamat2);
        namaAlamat1 = findViewById(R.id.nama_alamat_1);
        jalanAlamat1 = findViewById(R.id.jalan_alamat_1);
        keckabAlamat1 = findViewById(R.id.keckab_alamat_1);
        namaAlamat2 = findViewById(R.id.nama_alamat_2);
        jalanAlamat2 = findViewById(R.id.jalan_alamat_2);
        keckabAlamat2 = findViewById(R.id.keckab_alamat_2);

        note1 = findViewById(R.id.note);
        note2 = findViewById(R.id.note2);
        swipeRefreshLayout = findViewById(R.id.ref);
        bgalamat = findViewById(R.id.bg_alamat);
        progAlamat = findViewById(R.id.prog_alamat);
        kosong = findViewById(R.id.kosong);
    }

    private void readAlamat() {
        firestore.collection("users").document(firebaseAuth.getUid()).collection("alamat").document("alamat1").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                String nama = documentSnapshot.getString("nama");
                String jalan = documentSnapshot.getString("jalan");
                String rtrw = documentSnapshot.getString("rtrw");
                String keckab = documentSnapshot.getString("keckab");
                String note = documentSnapshot.getString("note");

                namaAlamat1.setText(nama);
                jalanAlamat1.setText(jalan + ", " + rtrw);
                keckabAlamat1.setText(keckab);
                note1.setText(note);

            }
        });
    }

    private void readAlamat2() {
        firestore.collection("users").document(firebaseAuth.getUid()).collection("alamat").document("alamat2").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                String nama = documentSnapshot.getString("nama");
                String jalan = documentSnapshot.getString("jalan");
                String rtrw = documentSnapshot.getString("rtrw");
                String keckab = documentSnapshot.getString("keckab");
                String note = documentSnapshot.getString("note");

                namaAlamat2.setText(nama);
                jalanAlamat2.setText(jalan + ", " + rtrw);
                keckabAlamat2.setText(keckab);
                note2.setText(note);
                bgalamat.setVisibility(View.GONE);
                progAlamat.setVisibility(View.GONE);

            }
        });
    }

    private void isiAlamat() {
        alamat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertIsiAlamat("alamat1");
            }
        });
        alamat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertIsiAlamat("alamat2");
            }
        });
    }

    private void alertIsiAlamat(final String alamat) {

        firestore.collection("users")
                .document(firebaseAuth.getUid())
                .collection("alamat")
                .document(alamat)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                final String getNama = documentSnapshot.getString("nama");
                String getJalan = documentSnapshot.getString("jalan");
                String getrtrw = documentSnapshot.getString("rtrw");
                String getKec = documentSnapshot.getString("kec");
                String getKab = documentSnapshot.getString("kab");
                String getNote = documentSnapshot.getString("note");

                final EditText editNama, editJalan, editRtRw, editKec, editKab, note;
                final Spinner spinKota;

                AlertDialog.Builder builder = new AlertDialog.Builder(AddressActivity.this);
                LayoutInflater get = getLayoutInflater();
                View view = get.inflate(R.layout.alert_add_address, null, false);
                editNama = view.findViewById(R.id.editnama);
                editJalan = view.findViewById(R.id.editjalan);
                editRtRw = view.findViewById(R.id.editrt);
                editKec = view.findViewById(R.id.editkec);
                note = view.findViewById(R.id.note);
                spinKota = view.findViewById(R.id.kota);

                editNama.setText(getNama);
                editJalan.setText(getJalan);
                editRtRw.setText(getrtrw);
                editKec.setText(getKec);
                note.setText(getNote);
                builder
                        .setView(view)
                        .setPositiveButton("save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String nama = editNama.getText().toString();
                        final String jalan = editJalan.getText().toString();
                        final String rtrw = editRtRw.getText().toString();
                        final String kec = editKec.getText().toString();
                        final String noted = note.getText().toString();
                        final String getKota = spinKota.getSelectedItem().toString();

//    <string-array name="spin_kota">
//        <item>Kota Yogyakarta</item>
//        <item>Kab. Sleman</item>
//        <item>Kab. Gunungkidul</item>
//        <item>Kab. Kulonprogo</item>
//        <item>Kab. Bantul</item>
//    </string-array>
//
                        try {
                            if (getKota.equals("Kota Yogyakarta")){
                                Map<String, Object> user = new HashMap<>();
                                user.put("nama", nama);
                                user.put("jalan", jalan);
                                user.put("rtrw", rtrw);
                                user.put("kec", kec);
                                user.put("kab", "Kota Yogyakarta");
                                user.put("note", noted);
                                user.put("ongkir", "20000");

                                firestore.collection("users")
                                        .document(firebaseAuth.getUid())
                                        .collection("alamat")
                                        .document(alamat)
                                        .set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            progAlamat.setVisibility(View.VISIBLE);
                                            bgalamat.setVisibility(View.VISIBLE);
                                            readAlamat();
                                            readAlamat2();
                                        }
                                        else{
                                            Toast.makeText(AddressActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                                else if (getKota.equals("Kab. Sleman") || getKota.equals("Kab. Bantul")){
                                Map<String, Object> user = new HashMap<>();
                                user.put("nama", nama);
                                user.put("jalan", jalan);
                                user.put("rtrw", rtrw);
                                user.put("kec", kec);
                                user.put("kab", "Kota Yogyakarta");
                                user.put("note", noted);
                                user.put("ongkir", "100000");

                                firestore.collection("users")
                                        .document(firebaseAuth.getUid())
                                        .collection("alamat")
                                        .document(alamat)
                                        .set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            progAlamat.setVisibility(View.VISIBLE);
                                            bgalamat.setVisibility(View.VISIBLE);
                                            readAlamat();
                                            readAlamat2();
                                        }
                                        else{
                                            Toast.makeText(AddressActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                            else {
                                Map<String, Object> user = new HashMap<>();
                                user.put("nama", nama);
                                user.put("jalan", jalan);
                                user.put("rtrw", rtrw);
                                user.put("kec", kec);
                                user.put("kab", spinKota.getSelectedItem().toString());
                                user.put("note", noted);
                                user.put("ongkir", "150000");

                                firestore.collection("users")
                                        .document(firebaseAuth.getUid())
                                        .collection("alamat")
                                        .document(alamat)
                                        .set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            progAlamat.setVisibility(View.VISIBLE);
                                            bgalamat.setVisibility(View.VISIBLE);
                                            readAlamat();
                                            readAlamat2();
                                        }
                                        else{
                                            Toast.makeText(AddressActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                Dialog dialog = builder.create();
                dialog.show();
            }
        });
    }

}





//        final EditText editNama, editJalan, editRtRw, editKecKab, note;
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(AddressActivity.this);
//        LayoutInflater get = getLayoutInflater();
//        View view = get.inflate(R.layout.alert_add_address, null, false);
//        editNama = view.findViewById(R.id.editnama);
//        editJalan = view.findViewById(R.id.editjalan);
//        editRtRw = view.findViewById(R.id.editrt);
//        editKecKab = view.findViewById(R.id.editkec);
//        note = view.findViewById(R.id.note);
//        builder
//                .setView(view)
//                .setPositiveButton("save", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        String nama = editNama.getText().toString();
//                        String jalan = editJalan.getText().toString();
//                        String rtrw = editRtRw.getText().toString();
//                        String keckab = editKecKab.getText().toString();
//                        String noted = note.getText().toString();
//
//                        Map<String, Object> user = new HashMap<>();
//                        user.put("nama", nama);
//                        user.put("jalan", jalan);
//                        user.put("rtrw", rtrw);
//                        user.put("keckab", keckab);
//                        user.put("note", noted);
//
//                        firestore.collection("users")
//                                .document(firebaseAuth.getUid())
//                                .collection("alamat")
//                                .document(alamat)
//                                .set(user)
//                                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        if (!task.isSuccessful()){
//                                            Toast.makeText(AddressActivity.this, "Error", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                });
//                    }
//                });
//        Dialog dialog = builder.create(); dialog.show();


//        firestore.collection("users")
//                .document(firebaseAuth.getUid())
//                .collection("alamat")
//                .document("kiki")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        DocumentSnapshot documentSnapshot = task.getResult();
//                        String nama = documentSnapshot.getString("nama");
//                        String jalan = documentSnapshot.getString("jalan");
//                        String rtrw = documentSnapshot.getString("rtrw");
//                        String keckab = documentSnapshot.getString("keckab");
//
//                        Toast.makeText(AddressActivity.this, nama, Toast.LENGTH_SHORT).show();
//
//                        addresses = new ArrayList<>();
//                        adapterAddress = new AdapterAddress(addresses, AddressActivity.this);
//
//                        addresses.add(new Address(nama, jalan, rtrw, keckab));
//                    }
//                });


//        fabadd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final EditText editNama, editJalan, editRtRw, editKecKab;
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(AddressActivity.this);
//                LayoutInflater get = getLayoutInflater();
//                View view = get.inflate(R.layout.alert_add_address, null, false);
//                editNama = view.findViewById(R.id.editnama);
//                editJalan = view.findViewById(R.id.editjalan);
//                editRtRw = view.findViewById(R.id.editrt);
//                editKecKab = view.findViewById(R.id.editkec);
//                builder
//                        .setView(view)
//                        .setPositiveButton("save", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                String nama = editNama.getText().toString();
//                                String jalan = editJalan.getText().toString();
//                                String rtrw = editRtRw.getText().toString();
//                                String keckab = editKecKab.getText().toString();
//
//                                Map<String, Object> user = new HashMap<>();
//                                user.put("nama", nama);
//                                user.put("jalan", jalan);
//                                user.put("rtrw", rtrw);
//                                user.put("keckab", keckab);
//
//                                firestore.collection("users")
//                                        .document(firebaseAuth.getUid())
//                                        .collection("alamat")
//                                        .document(nama)
//                                        .set(user)
//                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<Void> task) {
//                                                if (!task.isSuccessful()){
//                                                    Toast.makeText(AddressActivity.this, "Error", Toast.LENGTH_SHORT).show();
//                                                }
//                                            }
//                                        });
//
//
//
//                            }
//                        });
//                Dialog dialog = builder.create(); dialog.show();
//            }
//        });


//    @Override
//    public boolean onCreateOptionsMenu(Menuu menu) {
//        getMenuInflater().inflate(R.menu.menu_add_alamat, menu);
//        return super.onCreateOptionsMenu(menu);
//
//
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.add_alamat:
//
//        }
//        return super.onOptionsItemSelected(item);
//    }





