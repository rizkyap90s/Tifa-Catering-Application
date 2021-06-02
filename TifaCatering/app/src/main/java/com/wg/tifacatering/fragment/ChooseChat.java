package com.wg.tifacatering.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.wg.tifacatering.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseChat extends BottomSheetDialogFragment {


    CardView cardViewtoWA, cardViewtoSMS;

    public static ChooseChat newInstance(String string){
        ChooseChat f = new ChooseChat();
        Bundle args = new Bundle();
        args.putString("string", string);

        f.setArguments(args);
        return f;
    }


    public ChooseChat() {
        // Required empty public constructor
    }

    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME,0);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choose_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cardViewtoWA = view.findViewById(R.id.cardToWA);
        cardViewtoSMS = view.findViewById(R.id.cardToSMS);

        cardViewtoWA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWhatsApp("6287855651181");
            }
        });
        cardViewtoSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSMS("087855651181");
            }
        });

    }

    private void openWhatsApp(String number) {
        String url = "https://api.whatsapp.com/send?phone=" + number;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
    private void openSMS(String number){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("smsto:"+number));
        startActivity(intent);
    }

}
