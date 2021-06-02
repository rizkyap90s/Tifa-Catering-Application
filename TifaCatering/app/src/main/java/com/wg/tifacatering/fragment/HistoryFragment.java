package com.wg.tifacatering.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.wg.tifacatering.R;
import com.wg.tifacatering.activity.DetailPaidActivity;
import com.wg.tifacatering.activity.PaymentActivity;
import com.wg.tifacatering.activity.WaitingApprovalActivity;
import com.wg.tifacatering.model.ModelHistory;

import static com.wg.tifacatering.activity.PaymentActivity.KEY_HISTORY;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {

    FirestoreRecyclerAdapter firestoreRecyclerAdapter;
    FirebaseFirestore db;
    FirebaseAuth auth;
    RecyclerView rvPending, rvRequest, rvPaid;
    ImageView bgHistory;
    ProgressBar progHistory;
    CardView cvPending, cvPaid, cvRequest;
    RelativeLayout clickPending, clickPaid, clickRequest;

    TextView pendingNama, pendingAlamat, pendingStatus, paidNama, paidAlamat, paidStatus, requestNama, requestAlamat, requestStatus;


    String [] option = {"Delete"};


    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        auth.getCurrentUser();
        pendingNama = view.findViewById(R.id.pending_nama_paket);
        pendingAlamat = view.findViewById(R.id.pending_alamat_paket);
        pendingStatus = view.findViewById(R.id.pending_status);
        paidNama = view.findViewById(R.id.paid_nama_paket);
        paidAlamat = view.findViewById(R.id.paid_alamat_paket);
        paidStatus = view.findViewById(R.id.paid_status);
        cvPaid = view.findViewById(R.id.card_paid);
//        rvPaid = view.findViewById(R.id.rv_paid);
        clickPending = view.findViewById(R.id.click_pending);
        clickPaid = view.findViewById(R.id.click_paid);
        clickRequest = view.findViewById(R.id.click_request);
        cvRequest = view.findViewById(R.id.card_request);
        requestNama = view.findViewById(R.id.request_nama_paket);
        requestAlamat = view.findViewById(R.id.request_alamat_paket);
        requestStatus = view.findViewById(R.id.request_status);
        progHistory = view.findViewById(R.id.prog_history);
        bgHistory = view.findViewById(R.id.bg_history);
//        rvPending = view.findViewById(R.id.rv_history);
//        rvRequest = view.findViewById(R.id.rv_request);
        cvPending = view.findViewById(R.id.card_pending);

        if (auth.getCurrentUser()!=null){
            showRequestOrder();
            showPendingOrder();
            showPaidOrder();
        }
    }

    private void showPaidOrder() {
        db.collection("approved_order").document(auth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot doc = task.getResult();

                final ModelHistory modelHistory = new ModelHistory();

                if (doc.exists()){
                    cvPaid.setVisibility(View.VISIBLE);
                    String nama_paket = doc.getString("nama_paket");
                    String jenis_paket = doc.getString("jenis_paket");
                    String isi_paket = doc.getString("isi_paket");
                    String tanggal_antar = doc.getString("tanggal_kirim");
                    String jam_antar = doc.getString("waktu_kirim");
                    int  harga_satuan = doc.getLong("harga_paket").intValue();
                    int jumlah_pesan = doc.getLong("jumlah_pesan").intValue();
                    int minimal_pesan = doc.getLong("minimal_pesan").intValue();
                    int total_bayar = doc.getLong("total_bayar").intValue();
                    String nama_customer = doc.getString("nama_customer");
                    String nomor_customer = doc.getString("nomor_customer");
                    String alamat_customer = doc.getString("alamat_kirim");
                    String status = doc.getString("status");

                    modelHistory.setNama_paket(nama_paket);
                    modelHistory.setJenis_paket(jenis_paket);
                    modelHistory.setIsi_paket(isi_paket);
                    modelHistory.setTanggal_kirim(tanggal_antar);
                    modelHistory.setWaktu_kirim(jam_antar);
                    modelHistory.setHarga_paket(harga_satuan);
                    modelHistory.setJumlah_pesan(jumlah_pesan);
                    modelHistory.setMinimal_pesan(minimal_pesan);
                    modelHistory.setTotal_bayar(total_bayar);
                    modelHistory.setAlamat_kirim(alamat_customer);
                    modelHistory.setStatus(status);
                    modelHistory.setNama_customer(nama_customer);
                    modelHistory.setNomor_customer(nomor_customer);

                    paidNama.setText(nama_paket);
                    paidAlamat.setText(alamat_customer);
                    paidStatus.setText(status);

                    clickPaid.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent pendingToDetail = new Intent(getActivity(), DetailPaidActivity.class);
                            pendingToDetail.putExtra(KEY_HISTORY, modelHistory);
                            startActivity(pendingToDetail);
                        }
                    });


                }

            }
        });


    }

    private void showRequestOrder() {
        db.collection("request_order").document(auth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot doc = task.getResult();

                final ModelHistory modelHistory = new ModelHistory();

                if (doc.exists()){
                    cvRequest.setVisibility(View.VISIBLE);
                    String nama_paket = doc.getString("nama_paket");
                    String jenis_paket = doc.getString("jenis_paket");
                    String isi_paket = doc.getString("isi_paket");
                    String tanggal_antar = doc.getString("tanggal_kirim");
                    String jam_antar = doc.getString("waktu_kirim");
                    int  harga_satuan = doc.getLong("harga_paket").intValue();
                    int jumlah_pesan = doc.getLong("jumlah_pesan").intValue();
                    int minimal_pesan = doc.getLong("minimal_pesan").intValue();
                    int total_bayar = doc.getLong("total_bayar").intValue();
                    String nama_customer = doc.getString("nama_customer");
                    String nomor_customer = doc.getString("nomor_customer");
                    String alamat_customer = doc.getString("alamat_kirim");
                    String status = doc.getString("status");

                    modelHistory.setNama_paket(nama_paket);
                    modelHistory.setJenis_paket(jenis_paket);
                    modelHistory.setIsi_paket(isi_paket);
                    modelHistory.setTanggal_kirim(tanggal_antar);
                    modelHistory.setWaktu_kirim(jam_antar);
                    modelHistory.setHarga_paket(harga_satuan);
                    modelHistory.setJumlah_pesan(jumlah_pesan);
                    modelHistory.setMinimal_pesan(minimal_pesan);
                    modelHistory.setTotal_bayar(total_bayar);
                    modelHistory.setAlamat_kirim(alamat_customer);
                    modelHistory.setStatus(status);
                    modelHistory.setNama_customer(nama_customer);
                    modelHistory.setNomor_customer(nomor_customer);

                    requestNama.setText(nama_paket);
                    requestAlamat.setText(alamat_customer);
                    requestStatus.setText(status);

                    clickRequest.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent pendingToDetail = new Intent(getActivity(), WaitingApprovalActivity.class);
                            pendingToDetail.putExtra(KEY_HISTORY, modelHistory);
                            startActivity(pendingToDetail);
                        }
                    });


                }

            }
        });


    }

    private void showPendingOrder() {

        db.collection("pending_order").document(auth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot doc = task.getResult();

                if (doc.exists()){
                    final ModelHistory modelHistory = new ModelHistory();

                    cvPending.setVisibility(View.VISIBLE);
                    String nama_paket = doc.getString("nama_paket");
                    String jenis_paket = doc.getString("jenis_paket");
                    String isi_paket = doc.getString("isi_paket");
                    String tanggal_antar = doc.getString("tanggal_kirim");
                    String jam_antar = doc.getString("waktu_kirim");
                    int  harga_satuan = doc.getLong("harga_paket").intValue();
                    int jumlah_pesan = doc.getLong("jumlah_pesan").intValue();
                    int minimal_pesan = doc.getLong("minimal_pesan").intValue();
                    int total_bayar = doc.getLong("total_bayar").intValue();
                    String nama_customer = doc.getString("nama_customer");
                    String nomor_customer = doc.getString("nomor_customer");
                    String alamat_customer = doc.getString("alamat_kirim");
                    String status = doc.getString("status");

                    modelHistory.setNama_paket(nama_paket);
                    modelHistory.setJenis_paket(jenis_paket);
                    modelHistory.setIsi_paket(isi_paket);
                    modelHistory.setTanggal_kirim(tanggal_antar);
                    modelHistory.setWaktu_kirim(jam_antar);
                    modelHistory.setHarga_paket(harga_satuan);
                    modelHistory.setJumlah_pesan(jumlah_pesan);
                    modelHistory.setMinimal_pesan(minimal_pesan);
                    modelHistory.setTotal_bayar(total_bayar);
                    modelHistory.setAlamat_kirim(alamat_customer);
                    modelHistory.setStatus(status);
                    modelHistory.setNama_customer(nama_customer);
                    modelHistory.setNomor_customer(nomor_customer);

                    pendingNama.setText(nama_paket);
                    pendingAlamat.setText(alamat_customer);
                    pendingStatus.setText(status);

                    clickPending.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent pendingToDetail = new Intent(getActivity(), PaymentActivity.class);
                            pendingToDetail.putExtra(KEY_HISTORY, modelHistory);
                            startActivity(pendingToDetail);
                        }
                    });
                }

            }
        });


//        progHistory.setVisibility(View.VISIBLE);
//        bgHistory.setVisibility(View.VISIBLE);
//
//        rvPending.setLayoutManager(new LinearLayoutManager(getActivity()));
//        DocumentReference query = db.collection("pending_order").document(auth.getUid());
//        FirestoreRecyclerOptions<ModelHistory> response = new FirestoreRecyclerOptions.Builder<ModelHistory>()
//                .build();
//
//        firestoreRecyclerAdapter = new FirestoreRecyclerAdapter<ModelHistory, FriendsHolder>(response) {
//            @Override
//            protected void onBindViewHolder(@NonNull final FriendsHolder holder, int position, @NonNull final ModelHistory model) {
//                holder.namaPaket.setText(model.getNama_paket());
//                holder.alamatPaket.setText(model.getAlamat_kirim());
//                holder.statusPaket.setText(model.getStatus());
//                holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(getActivity(), PaymentActivity.class);
//                        intent.putExtra(KEY_HISTORY, model);
//                        startActivity(intent);
//                    }
//                });
//                holder.relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
//                    @Override
//                    public boolean onLongClick(View v) {
//                        v.setOnCreateContextMenuListener(getActivity());
//                        return false;
//                    }
//                });
//
//            }
//
//            @NonNull
//            @Override
//            public FriendsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.item_custom_history, parent, false);
//                return new FriendsHolder(view1);
//            }
//        };
//        firestoreRecyclerAdapter.notifyDataSetChanged();
//        rvPending.setAdapter(firestoreRecyclerAdapter);
//        firestoreRecyclerAdapter.startListening();
//        progHistory.setVisibility(View.GONE);
//        bgHistory.setVisibility(View.GONE);
//        registerForContextMenu(rvPending);
    }

    public class FriendsHolder extends RecyclerView.ViewHolder {
        TextView namaPaket, alamatPaket, statusPaket;
        RelativeLayout relativeLayout;
        public FriendsHolder(View itemView) {
            super(itemView);
            namaPaket = itemView.findViewById(R.id.history_nama_paket);
            alamatPaket = itemView.findViewById(R.id.history_alamat_paket);
            statusPaket = itemView.findViewById(R.id.history_status);
            relativeLayout = itemView.findViewById(R.id.click);

        }
    }

}
