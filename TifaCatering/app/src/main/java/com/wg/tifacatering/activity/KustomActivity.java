package com.wg.tifacatering.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.wg.tifacatering.R;
import com.wg.tifacatering.fragment.ChooseChat;
import com.wg.tifacatering.model.ModelCustomOrder;

public class KustomActivity extends AppCompatActivity {

    public static final String KEY_CUSTOM_REQ = "nnhdufgd";

    Button chatAdmin;
    ChooseChat chooseChat;
    MaterialButton btnAjukan;
    TextInputEditText customHarga, customIsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kustom);
        init();
        bottomSheetChooseChat();
        clickAjukan();


    }

    private void init() {
        btnAjukan = findViewById(R.id.custom_ajukan);
        customHarga  = findViewById(R.id.nominal);
        customIsi  = findViewById(R.id.pesanan);
    }

    private void clickAjukan() {
        btnAjukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customIsi.getText().toString().isEmpty() || customHarga.getText().toString().isEmpty()){
                    Toast.makeText(KustomActivity.this, "Gagal, Periksa kembali data", Toast.LENGTH_LONG).show();
                }
                else{
                    String hargaSatuan = customHarga.getText().toString();
                    int intHargaSatuan = Integer.parseInt(hargaSatuan);
                    String isiPesananCustom = customIsi.getText().toString();

                    ModelCustomOrder modelCustomOrder = new ModelCustomOrder();
                    modelCustomOrder.setHargaPesanan(intHargaSatuan);
                    modelCustomOrder.setIsiPesanan(isiPesananCustom);

                    Intent ajukan = new Intent(KustomActivity.this, CustomDetailOrderActivity.class);
                    ajukan.putExtra(KEY_CUSTOM_REQ, modelCustomOrder);
                    startActivity(ajukan);
                }


            }
        });

    }

    private void bottomSheetChooseChat() {
        chatAdmin = findViewById(R.id.chatAdmin);
        chooseChat = ChooseChat.newInstance("Bottom Sheet Dialog");

        chatAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseChat.show(getSupportFragmentManager(), chooseChat.getTag());

            }
        });
    }
}
