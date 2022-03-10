package com.example.raniaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity2 extends AppCompatActivity {
    EditText name, email, handy;

    private Button weiterveri;
    private ProgressDialog pd;
    private static final String TAG = "MainActivity2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        pd = new ProgressDialog(MainActivity2.this);
        pd.setTitle("Übertragung");
        pd.setCanceledOnTouchOutside(false);

        weiterveri = findViewById(R.id.btn_weiter);
        name = findViewById(R.id.edit_name);
        email = findViewById(R.id.edit_email);
        handy = findViewById(R.id.etVerificationId);

        weiterveri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = name.getText().toString().trim();
                String Email = email.getText().toString().trim();
                String Handy = handy.getText().toString().trim();


                if (Name.isEmpty() && Email.isEmpty() && Handy.isEmpty()){
                    name.setError("Fehler");
                    email.setError("Fehler");
                    handy.setError("Fehler");
                }else if (Name.isEmpty()){
                    Toast.makeText(MainActivity2.this, "Fehler!", Toast.LENGTH_SHORT).show();
                    return;
                }else if (Email.isEmpty()){
                    Toast.makeText(MainActivity2.this, "Fehler!", Toast.LENGTH_SHORT).show();
                    return;
                }else if (Handy.isEmpty()){
                    Toast.makeText(MainActivity2.this, "Fehler!", Toast.LENGTH_SHORT).show();
                    return;
                }else if (Name.isEmpty() && Email.isEmpty()){
                    Toast.makeText(MainActivity2.this, "Fehler!", Toast.LENGTH_SHORT).show();
                    return;
                }else if (Name.isEmpty() && Handy.isEmpty()){
                    Toast.makeText(MainActivity2.this, "Fehler!", Toast.LENGTH_SHORT).show();
                    return;
                }else if (Email.isEmpty() && Handy.isEmpty()){
                    Toast.makeText(MainActivity2.this, "Fehler!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                    intent.putExtra("namekey",Name);
                    intent.putExtra("emailkey",Email);
                    intent.putExtra("handykey",Handy);
                    startActivity(intent);
                    name.setText("");
                    email.setText("");
                    handy.setText("");



                }

            }
        });

    }
}