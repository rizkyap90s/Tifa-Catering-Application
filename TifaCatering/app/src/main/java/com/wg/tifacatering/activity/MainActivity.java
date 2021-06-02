package com.wg.tifacatering.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.wg.tifacatering.R;
import com.wg.tifacatering.fragment.HistoryFragment;
import com.wg.tifacatering.fragment.HomeFragment;
import com.wg.tifacatering.fragment.MenuFragment;
import com.wg.tifacatering.fragment.NotifFragment;
import com.wg.tifacatering.fragment.ProfilFragment;

public class MainActivity extends AppCompatActivity {

//    MeowBottomNavigation meowBottomNavigation;
    Toolbar toolbar;
    Fragment fragment;
    BottomNavigationView bottomNavigationView;

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.about:
//                break;
//            case R.id.help:
//                break;
//            case R.id.setting:
//                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.pesan:
//                openWhatsApp("6287855651181");
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        meowBottomNavigation = findViewById(R.id.meow);
        bottomNavigationView = findViewById(R.id.navigation);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        clickNavigation();

        if (savedInstanceState == null){
//            meowBottomNavigation.show(3, true);
            bottomNavigationView.setSelectedItemId(R.id.main_home);
            fragment = new HomeFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                    .commit();
            setTitle("Tifa Catering");
        }

//        meowBottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_restaurant2));
//        meowBottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_history));
//        meowBottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_home));
//        meowBottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ic_notifications));
//        meowBottomNavigation.add(new MeowBottomNavigation.Model(5, R.drawable.ic_person));
//
//        meowBottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
//            @Override
//            public Unit invoke(MeowBottomNavigation.Model model) {
//                int id = model.getId();
//                switch (id){
//                    case 1 :
//                        fragment = new MenuFragment();
//                        getSupportFragmentManager()
//                                .beginTransaction()
//                                .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
//                                .commit();
//                        setTitle("Menu");
//                        meowBottomNavigation.getSolidColor();
//
//                        break;
//                    case 2 :
//                        fragment = new HistoryFragment();
//                        getSupportFragmentManager()
//                                .beginTransaction()
//                                .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
//                                .commit();
//                        setTitle("History");
//                        break;
//                    case 3 :
//                        fragment = new HomeFragment();
//                        getSupportFragmentManager()
//                                .beginTransaction()
//                                .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
//                                .commit();
//                        setTitle("Tifa Catering");
//
//                        break;
//                    case 4 :
//                        fragment = new NotifFragment();
//                        getSupportFragmentManager()
//                                .beginTransaction()
//                                .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
//                                .commit();
//                        setTitle("Notification");
//
//                        break;
//                    case 5 :
//                        fragment = new ProfilFragment();
//                        getSupportFragmentManager()
//                                .beginTransaction()
//                                .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
//                                .commit();
//                        setTitle("Profil");
//                        break;
//                }
//
//                return Unit.INSTANCE;
//            }
//        });
//
    }
    public void clickNavigation(){
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.menu:
                        fragment = new MenuFragment();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                                .commit();
                        if (getSupportActionBar() != null){
                            getSupportActionBar().setTitle("Menu");
                        }
                        return true;
                    case R.id.history:
                        fragment = new HistoryFragment();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                                .commit();
                        if (getSupportActionBar() != null){
                            getSupportActionBar().setTitle("History");
                        }
                        return true;
                    case R.id.main_home:
                        fragment = new HomeFragment();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                                .commit();
                        if (getSupportActionBar() != null){
                            getSupportActionBar().setTitle("Tifa Catering");
                        }
                        return true;
                    case R.id.notification:
                        fragment = new NotifFragment();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                                .commit();
                        if (getSupportActionBar() != null){
                            getSupportActionBar().setTitle("Notification");
                        }
                        return true;
                    case R.id.profil:
                        fragment = new ProfilFragment();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                                .commit();
                        if (getSupportActionBar() != null){
                            getSupportActionBar().setTitle("Profil");
                        }
                        return true;
                }

                return false;
            }
        });
    }
}
