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
import com.wg.tifacatering.R;
import com.wg.tifacatering.activity.MainActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {

    TextView tosignup;
    TextInputEditText email, password;
    Button btnMasuk;
    ProgressBar progressBar;
    ImageView bgLoading;
    FirebaseAuth firebaseAuth;

    public SignInFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        email = view.findViewById(R.id.masuk_email);
        password = view.findViewById(R.id.masuk_password);
        btnMasuk = view.findViewById(R.id.btn_masuk);
        progressBar = view.findViewById(R.id.masuk_progSignup);
        bgLoading = view.findViewById(R.id.masuk_bgLoading);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.getCurrentUser();

        if(firebaseAuth.getCurrentUser() != null){
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }

         tosignup = view.findViewById(R.id.tosignup);
         tosignup.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Fragment fragment = new SignUpFragment();
                 getFragmentManager()
                         .beginTransaction()
                         .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                         .commit();
             }
         });

         btnMasuk.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 final String getEmail = email.getText().toString();
                 final String getpassword = password.getText().toString();

                 if (getEmail.equals("") || getpassword.equals("")){
                     Toast.makeText(getContext(), "Kolom tidak boleh kosong", Toast.LENGTH_SHORT).show();
                 }
                 else {
                     progressBar.setVisibility(View.VISIBLE);
                     bgLoading.setVisibility(View.VISIBLE);
                     firebaseAuth.signInWithEmailAndPassword(getEmail, getpassword)
                             .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                 @Override
                                 public void onComplete(@NonNull Task<AuthResult> task) {
                                     if (task.isSuccessful()){
                                         startActivity(new Intent(getActivity(), MainActivity.class));
                                         progressBar.setVisibility(View.GONE);
                                         bgLoading.setVisibility(View.GONE);
                                         getActivity().finishAffinity();
                                     }
                                     else{
                                         progressBar.setVisibility(View.GONE);
                                         bgLoading.setVisibility(View.GONE);
                                         Toast.makeText(getActivity(), "Anda belum terdaftar", Toast.LENGTH_SHORT).show();
                                     }
                                 }
                             });
                 }
             }
         });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

}
