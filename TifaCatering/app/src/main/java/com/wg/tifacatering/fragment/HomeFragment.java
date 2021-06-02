package com.wg.tifacatering.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.wg.tifacatering.R;
import com.wg.tifacatering.activity.KustomActivity;
import com.wg.tifacatering.adapter.AdapterMenu;
import com.wg.tifacatering.adapter.AdapterNasiBox;
import com.wg.tifacatering.adapter.AdapterRekomendasi;
import com.wg.tifacatering.model.ModelMenu;
import com.wg.tifacatering.model.ModelNasiBox;
import com.wg.tifacatering.model.ModelRekomendasi;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    RecyclerView recyclerView, recyclerView3;
    List<ModelRekomendasi> list;
    List<ModelNasiBox> list2;
    CarouselView carouselView;
    MaterialButton btnViewCustom;
    AdapterRekomendasi adapterRekomendasi;
    AdapterNasiBox adapterNasiBox;
    ProgressBar progressBar;
    ImageView imageView;


    int[] sampleImages = {R.drawable.satu, R.drawable.dua, R.drawable.tiga};


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list = new ArrayList<>();
        list2 = new ArrayList<>();
        recyclerView = view.findViewById(R.id.rv_recomen);
        recyclerView3 = view.findViewById(R.id.rv_nasibox);
        btnViewCustom = view.findViewById(R.id.btnclick);
        progressBar = view.findViewById(R.id.prog_home);
        imageView = view.findViewById(R.id.bg_home);
        carouselView = view.findViewById(R.id.carouselView);
        carouselView.setImageListener(imageListener);
        carouselView.setPageCount(sampleImages.length);

        showPackage("rekomendasi");
        showPackage2("nasibox");
        btnViewCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(getContext(), KustomActivity.class));
            }
        });
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

    private void showPackage(String key) {
        progressBar.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.VISIBLE);
        try {
            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(key);
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            ModelRekomendasi m = snapshot.getValue(ModelRekomendasi.class);
                            list.add(m);
                        }
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                        adapterRekomendasi = new AdapterRekomendasi(getContext(), list);
                        recyclerView.setAdapter(adapterRekomendasi);
                        progressBar.setVisibility(View.GONE);
                        imageView.setVisibility(View.GONE);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void showPackage2(String key) {
        progressBar.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.VISIBLE);
        try {
            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(key);
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            ModelNasiBox m = snapshot.getValue(ModelNasiBox.class);
                            list2.add(m);
                        }
                        recyclerView3.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                        adapterNasiBox = new AdapterNasiBox(getContext(),list2);
                        recyclerView3.setAdapter(adapterNasiBox);
                        progressBar.setVisibility(View.GONE);
                        imageView.setVisibility(View.GONE);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
