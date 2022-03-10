package com.example.raniaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity3 extends AppCompatActivity {

    AutoCompleteTextView autoCompleteTxt, autoCompleteTxt1;

    ArrayList<String> list;
    ArrayList<String> list1;
    ArrayAdapter<String> adapter1;
    DatabaseReference TerminDbRef;
    ArrayAdapter<String> adapter;
    TextView welcome;

    DatabaseReference BehandlungDB;
    DatabaseReference reference;

    FirebaseDatabase KundenData;
    DatabaseReference reference1;
    ProgressDialog pd;
    AlertDialog mdialog1;
    AlertDialog.Builder mdialogbuilder1;
    ImageButton calendarbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Button btnbestatigen = (Button)findViewById(R.id.btn_bestätigen);

        autoCompleteTxt1 = findViewById(R.id.autoCompleteTextView1);
        autoCompleteTxt = findViewById(R.id.autoCompleteTextView);
        welcome = findViewById(R.id.willkommen);
        calendarbtn = (ImageButton)findViewById(R.id.calendarview1);

        BehandlungDB = FirebaseDatabase.getInstance("https://raniaapp-593d2-default-rtdb.europe-west1.firebasedatabase.app").getReference("Behandlung");
        reference = BehandlungDB.child("Behandlung");

        list1 = new ArrayList<String>();
        adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,list1);
        autoCompleteTxt.setAdapter(adapter1);

        ValueEventListener listener1 = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    String behandlung = data.child("service").getValue(String.class);
                    list1.add(behandlung);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity3.this,"Keine Verbindung zu der Datenbank..",Toast.LENGTH_LONG).show();
            }
        }; BehandlungDB.addListenerForSingleValueEvent(listener1);

        calendarbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdialogbuilder1 = new AlertDialog.Builder(MainActivity3.this);
                final View popupper = getLayoutInflater().inflate(R.layout.calendar_popup, null);

                mdialogbuilder1.setView(popupper);
                mdialog1 = mdialogbuilder1.create();
                mdialog1.show();

            }
        });


        pd = new ProgressDialog(this);
        pd.setTitle("Datenbank Übertragung");
        pd.setCanceledOnTouchOutside(false);


        String VorNachnameKey = getIntent().getStringExtra("namekey");
        welcome.setText("Willkommen " + VorNachnameKey);

        TerminDbRef = FirebaseDatabase.getInstance("https://raniaapp-593d2-default-rtdb.europe-west1.firebasedatabase.app").getReference("Termine");
        reference1 = TerminDbRef.child("Termine");

        list = new ArrayList<String>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,list);
        autoCompleteTxt1.setAdapter(adapter);

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot ds : datasnapshot.getChildren()){
                    String von = ds.child("Von").getValue(String.class);
                    String bis = ds.child("Bis").getValue(String.class);
                    String tag = ds.child("Tag").getValue(String.class);
                    String monat = ds.child("Monat").getValue(String.class);
                    String jahr = ds.child("Jahr").getValue(String.class);
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
                    TerminDbRef = FirebaseDatabase.getInstance("https://raniaapp-593d2-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("KundenDaten");

                    String key = TerminDbRef.push().getKey();
                    ///KundenEmployee kundenEmployee = new KundenEmployee(VorNachnameKey, EmailKey, HandyKey, Behandlungs, Termin);

                    Map<String, Object> map = new HashMap<>();
                    map.put("Name", VorNachnameKey);
                    map.put("Email", EmailKey);
                    map.put("Handynummer", HandyKey);
                    map.put("Behandlung", Behandlungs);
                    map.put("Termin", Termin);

                    TerminDbRef.child(key)
                            .setValue(map)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    ///Toast.makeText(MainActivity3.this, key, Toast.LENGTH_LONG).show();
                                    Toast.makeText(MainActivity3.this, "Erfolgreiche Buchung", Toast.LENGTH_LONG).show();
                                    pd.setTitle("Datenbank Verbindung");
                                    pd.setMessage("lädt..");
                                    pd.show();
                                    startActivity(new Intent(MainActivity3.this, MainActivity.class));
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity3.this, "Verbindung fehlgeschlagen...", Toast.LENGTH_LONG).show();
                        }
                    });




                }
            }
        });
    }
}