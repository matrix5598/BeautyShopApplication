package com.example.raniaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class OnlineShop extends AppCompatActivity {

    ImageButton vor, warenkorb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_shop);

        vor = findViewById(R.id.btn_geh);
        warenkorb = findViewById(R.id.btn_warenkorb);

        vor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gehzuruck = new Intent(OnlineShop.this, MainActivity.class);
                startActivity(gehzuruck);

            }
        });

        warenkorb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ware = new Intent(OnlineShop.this, PayActivity.class);
                startActivity(ware);
            }
        });



    }
}