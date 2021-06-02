package com.wg.tifacatering.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.wg.tifacatering.R;
import com.wg.tifacatering.fragment.SignInFragment;

public class SignInSignUpActivity extends AppCompatActivity {

    Fragment fragment;
    TextView skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_sign_up);

        skip = findViewById(R.id.skip);

        if (savedInstanceState == null){
            fragment = new SignInFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                    .commit();
        }

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInSignUpActivity.this, MainActivity.class));
                SignInSignUpActivity.this.finishAffinity();
            }
        });


    }
}
