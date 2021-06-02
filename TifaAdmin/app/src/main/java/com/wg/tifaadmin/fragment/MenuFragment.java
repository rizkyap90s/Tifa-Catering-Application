package com.wg.tifaadmin.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.wg.tifaadmin.R;
import com.wg.tifaadmin.adapter.MenuAdapter;
import com.wg.tifaadmin.model.ModelMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {

    List<ModelMenu> modelMenu;
    RecyclerView recyclerView;
    MenuAdapter menuAdapter;
    ImageView bgMenu;
    ProgressBar progMenu;

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv_menu);
        bgMenu = view.findViewById(R.id.bgmenu);
        progMenu = view.findViewById(R.id.progmenu);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        modelMenu = new ArrayList<>();
        Toast.makeText(getActivity(), "Menu", Toast.LENGTH_SHORT).show();

        try {
            bgMenu.setVisibility(View.VISIBLE);
            progMenu.setVisibility(View.VISIBLE);

            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("allmenu");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            ModelMenu m = snapshot.getValue(ModelMenu.class);
                            modelMenu.add(m);
                        }
                        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
                        menuAdapter = new MenuAdapter(getContext(), modelMenu);
                        recyclerView.setAdapter(menuAdapter);

                        bgMenu.setVisibility(View.GONE);
                        progMenu.setVisibility(View.GONE);
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
