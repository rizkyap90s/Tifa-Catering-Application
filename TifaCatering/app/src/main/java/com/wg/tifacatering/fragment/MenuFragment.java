package com.wg.tifacatering.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wg.tifacatering.R;
import com.wg.tifacatering.activity.SearchActivity;
import com.wg.tifacatering.adapter.AdapterMenu;
import com.wg.tifacatering.model.ModelMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment implements View.OnClickListener {

    AdapterMenu adapterMenu;
    List<ModelMenu> modelMenu;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    ImageView imageView;
    Button btnAll, btnNasiBox, btnBuffet, btnCoffe, btnSeminar, btnKenduri, btnAter, btnTumpeng, btnKambing;

    public MenuFragment() {
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progmenu);
        imageView = view.findViewById(R.id.bgmenu);
        recyclerView = view.findViewById(R.id.rv_menu);
        modelMenu = new ArrayList<>();

        btnAll = view.findViewById(R.id.btn_all);
        btnNasiBox = view.findViewById(R.id.btn_nasibox);
        btnBuffet = view.findViewById(R.id.btn_buffet);
        btnCoffe = view.findViewById(R.id.btn_coffebreak);
        btnSeminar = view.findViewById(R.id.btn_seminar);
        btnKenduri = view.findViewById(R.id.btn_kenduri);
        btnAter = view.findViewById(R.id.btn_ater);
        btnTumpeng = view.findViewById(R.id.btn_tumpeng);
        btnKambing = view.findViewById(R.id.btn_kambing);

        showPackage("allmenu");

        clickButton();



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_all:
                showPackage("allmenu");
                break;
            case R.id.btn_nasibox:
                showPackage("nasibox");
                break;
            case R.id.btn_buffet:
                showPackage("buffet");
                break;
            case R.id.btn_coffebreak:
                showPackage("coffebreak");
                break;
            case R.id.btn_seminar:
                showPackage("seminar");
                break;
            case R.id.btn_kenduri:
                showPackage("kenduri");
                break;
            case R.id.btn_ater:
                showPackage("aterater");
                break;
            case R.id.btn_tumpeng:
                showPackage("tumpeng");
                break;
            case R.id.btn_kambing:
                showPackage("kambingguling");
                break;
        }
    }

    private  void clickButton(){
        btnAll.setOnClickListener(this);
        btnNasiBox.setOnClickListener(this);
        btnBuffet.setOnClickListener(this);
        btnCoffe.setOnClickListener(this);
        btnSeminar.setOnClickListener(this);
        btnKenduri.setOnClickListener(this);
        btnAter.setOnClickListener(this);
        btnTumpeng.setOnClickListener(this);
        btnKambing.setOnClickListener(this);
    }

    private void showPackage(String key) {
        try {
            modelMenu.clear();
            progressBar.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.VISIBLE);

            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(key);
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            ModelMenu m = snapshot.getValue(ModelMenu.class);
                            modelMenu.add(m);
                        }
                        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
                        adapterMenu = new AdapterMenu(modelMenu, getContext());
                        recyclerView.setAdapter(adapterMenu);
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.tosearch, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.tosearch:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}