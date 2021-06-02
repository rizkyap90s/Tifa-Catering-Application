package com.wg.tifaadmin.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.wg.tifaadmin.R;
import com.wg.tifaadmin.model.ModelApprovedOrder;
import com.wg.tifaadmin.model.ModelDoneOrder;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoneFragment extends Fragment {

    RecyclerView rvDone;
    FirestoreRecyclerAdapter adapter;
    FirebaseFirestore db;
    FirebaseAuth auth;

    public DoneFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_done, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvDone = view.findViewById(R.id.rv_done);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();


        rvDone.setLayoutManager(new LinearLayoutManager(getContext()));
        Query query = db.collection("done_order");
        FirestoreRecyclerOptions<ModelDoneOrder> respone = new FirestoreRecyclerOptions.Builder<ModelDoneOrder>()
                .setQuery(query, ModelDoneOrder.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<ModelDoneOrder, FriendsHolder>(respone) {
            @NonNull
            @Override
            public FriendsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(getContext()).inflate(R.layout.custom_item_request, parent, false);
                return new FriendsHolder(v);
            }

            @Override
            protected void onBindViewHolder(@NonNull FriendsHolder holder, int position, @NonNull ModelDoneOrder model) {
                holder.customerName.setText(model.getNama_customer());
                holder.click.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "yes", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        };
        adapter.notifyDataSetChanged();
        rvDone.setAdapter(adapter);
        adapter.startListening();

    }
    public class FriendsHolder extends RecyclerView.ViewHolder {
        TextView customerName;
        CardView click;
        public FriendsHolder(View itemView) {
            super(itemView);
            customerName = itemView.findViewById(R.id.nama_customer);
            click = itemView.findViewById(R.id.cardPick);

        }
    }
}
