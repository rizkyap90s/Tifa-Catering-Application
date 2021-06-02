package com.wg.tifacatering.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.wg.tifacatering.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotifFragment extends Fragment {


    public NotifFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notif, container, false);
    }

}
