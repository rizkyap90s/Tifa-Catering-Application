package com.wg.tifacatering.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.wg.tifacatering.R;
import com.wg.tifacatering.activity.AddressActivity;
import com.wg.tifacatering.activity.SignInSignUpActivity;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilFragment extends Fragment implements View.OnClickListener {

    CardView cardAddress, cardMasukDaftar, cardNama, cardEmail, cardNumber, cardKeluar;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ImageView bgWhite;
    ProgressBar progProfil;
    TextView profilName, profilNumber, profilEmail;

    public ProfilFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cardAddress = view.findViewById(R.id.card_address);
        cardNama = view.findViewById(R.id.card_name);
        cardEmail = view.findViewById(R.id.card_email);
        cardMasukDaftar = view.findViewById(R.id.card_signupsignin);
        cardNumber = view.findViewById(R.id.card_number);
        cardKeluar = view.findViewById(R.id.card_logout);
        profilName = view.findViewById(R.id.profil_name);
        profilNumber = view.findViewById(R.id.profil_number);
        profilEmail = view.findViewById(R.id.profil_email);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        bgWhite = view.findViewById(R.id.bg_white_profil);
        progProfil = view.findViewById(R.id.prog_profil);
        isLogin();
        buttonClick();

    }

    private void showProfil() {
        bgWhite.setVisibility(View.VISIBLE);
        progProfil.setVisibility(View.VISIBLE);
        firebaseFirestore.collection("users")
                .document(firebaseAuth.getUid())
                .collection("data")
                .document("profil")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot doc = task.getResult();
                            String getNameProfil = doc.getString("name");
                            String getNumberProfil = doc.getString("number");
                            String getEmailProfil = doc.getString("email");

                            profilName.setText(getNameProfil);
                            profilEmail.setText(getEmailProfil);
                            profilNumber.setText(getNumberProfil);

                            bgWhite.setVisibility(View.GONE);
                            progProfil.setVisibility(View.GONE);
                        }
                    }
                });
    }

    private void buttonClick() {
        cardNama.setOnClickListener(this);
        cardAddress.setOnClickListener(this);
        cardEmail.setOnClickListener(this);
        cardMasukDaftar.setOnClickListener(this);
        cardNumber.setOnClickListener(this);
        cardKeluar.setOnClickListener(this);

    }

    private void isLogin() {
        if (firebaseAuth.getCurrentUser() != null){
            cardMasukDaftar.setVisibility(View.GONE);
            showProfil();
        }
        else {
            cardNama.setVisibility(View.GONE);
            cardEmail.setVisibility(View.GONE);
            cardNumber.setVisibility(View.GONE);
            cardAddress.setVisibility(View.GONE);
            cardKeluar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.card_signupsignin:
                startActivity(new Intent(getActivity(), SignInSignUpActivity.class));
                break;
            case R.id.card_logout:
                alertLogout();
                break;
            case R.id.card_address:
                startActivity(new Intent(getContext(), AddressActivity.class));
                break;
            case R.id.card_name:
                alertEditData("name");
                break;
            case R.id.card_email:
                alertEditData("email");
                break;
            case R.id.card_number:
                alertEditData("number");
                break;
        }
    }

    private void alertLogout() {
        AlertDialog.Builder alertOut = new AlertDialog.Builder(getActivity());
        alertOut
                .setMessage("Yakin ingin keluar?")
                .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        firebaseAuth.signOut();
                        startActivity(new Intent(getActivity(), SignInSignUpActivity.class));
                    }
                });
        Dialog show = alertOut.create();show.show();
    }

    public void alertEditData (final String getValue){
        bgWhite.setVisibility(View.VISIBLE);
        progProfil.setVisibility(View.VISIBLE);


        firebaseFirestore.collection("users")
                .document(firebaseAuth.getUid())
                .collection("data")
                .document("profil")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            AlertDialog.Builder edit = new AlertDialog.Builder(getContext());
                            View view = getLayoutInflater().inflate(R.layout.alert_edit_profil, null);
                            final TextInputEditText editProfil;
                            editProfil = view.findViewById(R.id.edit);

                            DocumentSnapshot doc = task.getResult();
                            String getValueProfil = doc.getString(getValue);
                            editProfil.setText(getValueProfil);
                            bgWhite.setVisibility(View.GONE);
                            progProfil.setVisibility(View.GONE);
                            edit
                                    .setView(view)
                                    .setTitle("Edit :")
                                    .setCancelable(false)
                                    .setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            final String getEditProfil = editProfil.getText().toString();
                                            Map<String, Object> user = new HashMap<>();
                                            user.put(getValue, getEditProfil);
                                            firebaseFirestore.collection("users")
                                                    .document(firebaseAuth.getUid())
                                                    .collection("data")
                                                    .document("profil")
                                                    .update(user)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                showProfil();
                                                            }
                                                        }
                                                    });
                                        }
                                    });
                            Dialog dialog = edit.create();dialog.show();
                        }
                    }
                });


    }



}
