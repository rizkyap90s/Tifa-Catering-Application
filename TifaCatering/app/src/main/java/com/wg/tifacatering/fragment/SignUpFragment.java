package com.wg.tifacatering.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.wg.tifacatering.R;
import com.wg.tifacatering.activity.MainActivity;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {

    TextView tologin;
    FirebaseAuth firebaseAuth;
    TextInputEditText email, nama, password, password2, number;
    Button buttonDaftar;
    ProgressBar progressBar;
    ImageView bgLoading;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        email = view.findViewById(R.id.edit_email);
        nama = view.findViewById(R.id.edit_nama);
        password = view.findViewById(R.id.edit_password);
        password2 = view.findViewById(R.id.edit_password2);
        buttonDaftar = view.findViewById(R.id.btn_daftar);
        progressBar = view.findViewById(R.id.progSignup);
        bgLoading = view.findViewById(R.id.bgLoading);
        number = view.findViewById(R.id.edit_number);

        tologin = view.findViewById(R.id.tologin);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.getCurrentUser();

        tologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new SignInFragment();
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                        .commit();
            }
        });

        buttonDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String getEmail = email.getText().toString();
                final String getNama = nama.getText().toString();
                final String getPassword = password.getText().toString();
                final String getPassword2 = password2.getText().toString();
                final String getNumber = number.getText().toString();
                if (getEmail.equals("") || getNama.equals("") || getPassword.equals("") || getPassword2.equals("") || getNumber.equals("")){
                    Toast.makeText(getActivity(), "Kolom tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }
                else if (getPassword.length() < 8){
                    Toast.makeText(getActivity(), "Masukan password 8 digit atau lebih", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (!getPassword.equals(getPassword2)){
                        Toast.makeText(getActivity(), "Konfirmasi password salah", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //push to auth
                        bgLoading.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.VISIBLE);
                        firebaseAuth.createUserWithEmailAndPassword(getEmail, getPassword)
                                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()){
                                            firebaseAuth.signInWithEmailAndPassword(getEmail, getPassword)
                                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                                            if(task.isSuccessful()){
                                                                progressBar.setVisibility(View.GONE);
                                                                bgLoading.setVisibility(View.GONE);
                                                            }
                                                        }
                                                    });
                                            Intent sigunp = new Intent(new Intent(getContext(), MainActivity.class));

                                            FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                                            Map<String, Object> user = new HashMap<>();
                                            user.put("email", getEmail);
                                            user.put("name", getNama);
                                            user.put("password", getPassword);
                                            user.put("number", getNumber);
                                            user.put("uid", firebaseAuth.getUid());

                                            firestore.collection("users")
                                                    .document(firebaseAuth.getUid())
                                                    .collection("data")
                                                    .document("profil")
                                                    .set(user)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (!task.isSuccessful()){
                                                                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                            startActivity(sigunp);
                                            getActivity().finishAffinity();
                                        }
                                    }
                                });
                    }
                }
            }
        });
    }

    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

}
