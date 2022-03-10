package com.example.raniaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity3 extends AppCompatActivity {

    String[] items = {"Micro-Needling", "Eyebrowlifting", "Anti-Falten Therapie", "Anti-Hässlich", "Extra-Behandlung"};

    AutoCompleteTextView autoCompleteTxt, autoCompleteTxt1;
    Spinner SpinnerViewer;
    ArrayAdapter<String> adapterItems;

    ArrayList<String> list;
    DatabaseReference TerminDbRef;
    ArrayAdapter<String> adapter;
    TextView welcome;

    FirebaseDatabase KundenData;
    DatabaseReference reference1;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Button btnbestatigen = (Button)findViewById(R.id.btn_bestätigen);

        autoCompleteTxt1 = findViewById(R.id.autoCompleteTextView1);
        autoCompleteTxt = findViewById(R.id.autoCompleteTextView);
        welcome = findViewById(R.id.willkommen);

        adapterItems = new ArrayAdapter<String>(this,R.layout.list_item,items);
        autoCompleteTxt.setAdapter(adapterItems);
        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), ""+item+" ausgewählt", Toast.LENGTH_SHORT).show();
            }
        });

        pd = new ProgressDialog(this);
        pd.setTitle("Datenbank Übertragung");
        pd.setCanceledOnTouchOutside(false);


        String VorNachnameKey = getIntent().getStringExtra("namekey");
        welcome.setText("Willkommen " + VorNachnameKey);

        list = new ArrayList<String>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,list);
        autoCompleteTxt1.setAdapter(adapter);

        TerminDbRef = FirebaseDatabase.getInstance("https://raniaapp-593d2-default-rtdb.europe-west1.firebasedatabase.app").getReference("Termine");
        reference1 = TerminDbRef.child("Termine");

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot ds : datasnapshot.getChildren()){
                    String von = ds.child("von").getValue(String.class);
                    String bis = ds.child("bis").getValue(String.class);
                    String tag = ds.child("tag").getValue(String.class);
                    String monat = ds.child("monat").getValue(String.class);
                    String jahr = ds.child("jahr").getValue(String.class);
                    list.add(von + "-" + bis + " am " + tag + "/" + monat + "/" + jahr);
            }
        }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity3.this,"Keine Verbindung mit der Datenbank...",Toast.LENGTH_LONG).show();
            }
        }; TerminDbRef.addListenerForSingleValueEvent(listener);


        btnbestatigen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Behandlungs = autoCompleteTxt.getText().toString();
                String Termin = autoCompleteTxt1.getText().toString();
                String VorNachnameKey = getIntent().getStringExtra("namekey");
                String EmailKey = getIntent().getStringExtra("emailkey");
                String HandyKey = getIntent().getStringExtra("handykey");

                if (Behandlungs.isEmpty() && Termin.isEmpty()) {
                    autoCompleteTxt.setError("Fehler!Bitte Behandlung aussuchen");
                } else {
                    KundenData = FirebaseDatabase.getInstance("https://raniaapp-593d2-default-rtdb.europe-west1.firebasedatabase.app");
                    TerminDbRef = KundenData.getReference("KundenDaten");

                    KundenEmployee kundenEmployee = new KundenEmployee(VorNachnameKey, EmailKey, HandyKey, Behandlungs, Termin);
                    TerminDbRef.push().setValue(kundenEmployee);
                    Toast.makeText(MainActivity3.this, "Erfolgreiche Buchung!", Toast.LENGTH_LONG).show();

                    pd.setTitle("Datenbank Verbindung");
                    pd.setMessage("lädt..");
                    pd.show();

                    startActivity(new Intent(MainActivity3.this, MainActivity.class));


                }
            }
        });
    }
}