package com.wg.tifaadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.wg.tifaadmin.fragment.ApprovedFragment;
import com.wg.tifaadmin.fragment.DoneFragment;
import com.wg.tifaadmin.fragment.MenuFragment;
import com.wg.tifaadmin.fragment.PendingFragment;
import com.wg.tifaadmin.fragment.RequestFragment;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.main_nav);

        if (savedInstanceState == null){
            fragment = new RequestFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment, fragment.getClass().getSimpleName())
                    .commit();
        }
        clicknavigation();
    }

    private void clicknavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.request:
                        fragment = new RequestFragment();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, fragment, fragment.getClass().getSimpleName())
                                .commit();
                        return true;
                    case R.id.pending:
                        fragment = new PendingFragment();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, fragment, fragment.getClass().getSimpleName())
                                .commit();
                        return true;
                    case R.id.approved:
                        fragment = new ApprovedFragment();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, fragment, fragment.getClass().getSimpleName())
                                .commit();
                        return true;
                    case R.id.done:
                        fragment = new DoneFragment();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, fragment, fragment.getClass().getSimpleName())
                                .commit();
                        return true;
                }

                return false;
            }
        });

    }

}
