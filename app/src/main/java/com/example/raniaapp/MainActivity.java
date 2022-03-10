package com.example.raniaapp;

import static com.example.raniaapp.R.id.location;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    ImageButton route;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        route = findViewById(R.id.location);
        Button btntermin = (Button)findViewById(R.id.btn_termin);
        Button admin = (Button)findViewById(R.id.btn_login);

        pd = new ProgressDialog(MainActivity.this);
        pd.setTitle("Authentifizierung");
        pd.setCanceledOnTouchOutside(false);


        btntermin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(register);
            }
        });

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(login);
                Toast.makeText(MainActivity.this, "Nur für das Personal", Toast.LENGTH_SHORT).show();
            }
        });


        route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View i) {
                Uri uri = Uri.parse("geo:51.131093,6.414827?q=Trompeterallee+16,+41189+Mönchengladbach");
                Intent maps = new Intent(Intent.ACTION_VIEW, uri);
                maps.setPackage("com.google.android.apps.maps");
                if (maps.resolveActivity(getPackageManager()) != null){
                    startActivity(maps);
                }
            }
        });


    }

}
