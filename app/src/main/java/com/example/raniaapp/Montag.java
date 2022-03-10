package com.example.raniaapp;

import static android.R.layout.simple_list_item_1;
import static android.R.layout.simple_spinner_dropdown_item;
import static android.R.layout.simple_spinner_item;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.client.Firebase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Montag extends AppCompatActivity {


    ImageButton backbtnbtn, plusbutton, plus_be, calendarbutton;
    Spinner dropdown_layout1, dropdown_layout2, tage, monat, jahre;
    AlertDialog mdialog;
    AlertDialog.Builder mdialogbuilder;
    DatabaseReference reference;
    ArrayList<String> myArrayList = new ArrayList<>();
    DatabaseReference mRef;
    List<String> list1;
    EditText edit_behandlung;
    ProgressDialog pde;
    FirebaseDatabase TerminDB;
    Firebase firebase;

    ListView listView;
    FirebaseListAdapter firebaseListAdapter;
    FirebaseListOptions listAdapter;
    DatabaseReference  databaseReference;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_montag);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        TerminDB = FirebaseDatabase.getInstance();
        reference = TerminDB.getReference("KundenDaten");
        reference.keepSynced(true);

        List<String> uhrzeiten = Arrays.asList("8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00");
        List<String> day = Arrays.asList("01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31");
        List<String> month = Arrays.asList("01","02","03","04","05","06","07","08","09","10","11","12");
        List<String> year = Arrays.asList("2022","2023","2024","2025","2026","2027","2028","2029","2030","2031","2032","2033","2034","2035","2036","2037","2038","2039","2040","2041");

        // plus button mit listview verknüpfen..

        plusbutton = (ImageButton) findViewById(R.id.plusbtn);
        dropdown_layout1 = findViewById(R.id.dropdown_menuMoVon);
        dropdown_layout2 = findViewById(R.id.dropdown_menuMoBis);
        backbtnbtn = (ImageButton) findViewById(R.id.backterminemo_btn);
        listView = (ListView) findViewById(R.id.listview);
        tage = findViewById(R.id.tag);
        monat = findViewById(R.id.mon);
        jahre = findViewById(R.id.jahr);
        calendarbutton = findViewById(R.id.calendarview);
        plus_be = findViewById(R.id.imgbtn_behandlung);
        edit_behandlung = findViewById(R.id.edit_behandlung);
        ImageButton deletebtn = findViewById(R.id.loeschenbtn);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), simple_spinner_item, uhrzeiten);
        adapter.setDropDownViewResource(simple_spinner_dropdown_item);
        dropdown_layout1.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getApplicationContext(), simple_spinner_item, uhrzeiten);
        adapter2.setDropDownViewResource(simple_spinner_dropdown_item);
        dropdown_layout2.setAdapter(adapter2);

        ArrayAdapter<String> adaptertag = new ArrayAdapter<>(getApplicationContext(), simple_spinner_item, day);
        adaptertag.setDropDownViewResource(simple_spinner_dropdown_item);
        tage.setAdapter(adaptertag);

        ArrayAdapter<String> adaptermon = new ArrayAdapter<>(getApplicationContext(), simple_spinner_item,month);
        adaptermon.setDropDownViewResource(simple_spinner_dropdown_item);
        monat.setAdapter(adaptermon);

        ArrayAdapter<String> adapterjahr = new ArrayAdapter<>(getApplicationContext(), simple_spinner_item, year);
        adapterjahr.setDropDownViewResource(simple_spinner_dropdown_item);
        jahre.setAdapter(adapterjahr);

        listView = (ListView) findViewById(R.id.listview);
        list1 = new ArrayList<String>();
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(Montag.this, simple_list_item_1,myArrayList);
        listView.setAdapter(adapter1);

        mRef = FirebaseDatabase.getInstance("https://raniaapp-593d2-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("Termine");
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot ds, @Nullable String previousChildName) {
                String von = ds.child("Von").getValue(String.class);
                String bis = ds.child("Bis").getValue(String.class);
                String tag = ds.child("Tag").getValue(String.class);
                String monat = ds.child("Monat").getValue(String.class);
                String jahr = ds.child("Jahr").getValue(String.class);
                myArrayList.add(von + "-" + bis + " am " + tag + "/" + monat + "/" + jahr);
                adapter1.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                adapter1.notifyDataSetChanged(); }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) { }
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) { }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int which_item = position;
                String value = (String) parent.getItemAtPosition(position);

                new AlertDialog.Builder(Montag.this)
                        .setIcon(android.R.drawable.ic_menu_delete)
                        .setTitle("Sicher?")
                        .setMessage("Willst du diesen Termin wirklich löschen ?!")
                        .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                /////////
                                myArrayList.remove(which_item);
                                firebaseListAdapter.notifyDataSetChanged();
                                // mRef.child("Termine").child(mRef.getRef().getKey()).removeValue();
                                Toast.makeText(Montag.this,"Erfolgreich gelö scht!",Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("Nein", null)
                        .show();
                return true;
            }
        });

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(Montag.this)
                        .setIcon(android.R.drawable.ic_menu_delete)
                        .setTitle("Sicher?")
                        .setMessage("Wollen Sie wirklich alle Termine löschen?")
                        .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                myArrayList.clear();
                                mRef.removeValue();
                                //adapter1.notifyDataSetChanged();
                                Toast.makeText(Montag.this,"Alle Termine wurden erfolgreich gelöscht", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("Nein", null)
                        .show();
                return;

            }
        });


        plusbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String von = dropdown_layout1.getSelectedItem().toString().trim();
                String bis = dropdown_layout2.getSelectedItem().toString().trim();
                String tagesdatum = tage.getSelectedItem().toString().trim();
                String monatstag = monat.getSelectedItem().toString().trim();
                String jahresdatum = jahre.getSelectedItem().toString().trim();

                reference = FirebaseDatabase.getInstance("https://raniaapp-593d2-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("Termine");
                ///push Strategie
                String key = reference.push().getKey();
                //Employee employee = new Employee(von, bis, tagesdatum, monatstag, jahresdatum);

                Map<String, Object> map = new HashMap<>();
                map.put("Von", von);
                map.put("Bis", bis);
                map.put("Tag", tagesdatum);
                map.put("Monat",monatstag);
                map.put("Jahr", jahresdatum);

                reference
                        .child(key)
                        .setValue(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                ///Toast.makeText(Montag.this, key, Toast.LENGTH_LONG).show();
                                Toast.makeText(Montag.this, "Erfolgreich eingetragen!", Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Montag.this, "Keine Verbindung zur Datenbank..", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
        pde = new ProgressDialog(Montag.this);
        pde.setTitle("Datenbank - Übertragung");
        pde.setCanceledOnTouchOutside(false);

        plus_be.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String service = edit_behandlung.getText().toString().trim();

                reference = FirebaseDatabase.getInstance("https://raniaapp-593d2-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("Behandlung");
                ///push Strategie

                String keybe = reference.push().getKey();
                //Behandlung behandlung = new Behandlung(service);

                Map<String, Object> map = new HashMap<>();
                map.put("service", service);

                reference.child(keybe)
                        .setValue(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                ///Toast.makeText(Montag.this,keybe,Toast.LENGTH_LONG).show();
                                Toast.makeText(Montag.this, "Erfolgreich eingetragen!", Toast.LENGTH_LONG).show();
                                edit_behandlung.setText("");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Montag.this, "Fehler bei Datenübertragung!",Toast.LENGTH_LONG).show();
                            }
                        });

                //reference.child(keybe).setValue(behandlung);

            }
        });

        backbtnbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Montag.this, UserSuccess.class));
                finish();
            }
        });

        calendarbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mdialogbuilder = new AlertDialog.Builder(Montag.this);
                final View popupper = getLayoutInflater().inflate(R.layout.calendar_popup, null);

                mdialogbuilder.setView(popupper);
                mdialog = mdialogbuilder.create();
                mdialog.show();
            }
        });
    }
    private void deleteItems(){
        listView.clearChoices();
    }
}