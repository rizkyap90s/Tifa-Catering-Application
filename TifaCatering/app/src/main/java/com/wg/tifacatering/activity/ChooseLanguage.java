package com.wg.tifacatering.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.wg.tifacatering.R;

public class ChooseLanguage extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    Button btnindo, btneng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_language);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.getCurrentUser();

        if(firebaseAuth.getCurrentUser() != null){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        btnindo = findViewById(R.id.saya_indo);
        btneng = findViewById(R.id.i_eng);

        btnindo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseLanguage.this, SignInSignUpActivity.class));
                ChooseLanguage.this.finishAffinity();
            }
        });
        btneng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseLanguage.this, SignInSignUpActivity.class));
                ChooseLanguage.this.finishAffinity();

            }
        });
    }
}
