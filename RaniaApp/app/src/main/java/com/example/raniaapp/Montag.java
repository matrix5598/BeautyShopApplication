package com.example.raniaapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Montag extends AppCompatActivity {

    ListView listView;
    ImageButton backbtnbtn, plusbutton, minusbutton, calendarbutton;
    Spinner dropdown_layout1, dropdown_layout2, tage, monat, jahre;
    AlertDialog mdialog;
    AlertDialog.Builder mdialogbuilder;
    FirebaseDatabase Termine;
    DatabaseReference reference;

    ArrayList<String> myArrayList = new ArrayList<>();
    DatabaseReference mRef;
    ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_montag);

        List<String> uhrzeiten = Arrays.asList("8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00");
        List<String> day = Arrays.asList("01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31");
        List<String> month = Arrays.asList("01","02","03","04","05","06","07","08","09","10","11","12");
        List<String> year = Arrays.asList("2022","2023","2024","2025","2026","2027","2028","2029","2030","2031","2032","2033","2034","2035","2036","2037","2038","2039","2040","2041");

        // plus button mit listview verknüpfen...

        plusbutton = (ImageButton) findViewById(R.id.plusbtn);
        dropdown_layout1 = findViewById(R.id.dropdown_menuMoVon);
        dropdown_layout2 = findViewById(R.id.dropdown_menuMoBis);
        backbtnbtn = (ImageButton) findViewById(R.id.backterminemo_btn);
        listView = (ListView) findViewById(R.id.listview);
        minusbutton = findViewById(R.id.minusbtn);
        tage = findViewById(R.id.tag);
        monat = findViewById(R.id.mon);
        jahre = findViewById(R.id.jahr);
        calendarbutton = findViewById(R.id.calendarview);

        ArrayList<String> arrayList = new ArrayList<>();

        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, uhrzeiten);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown_layout1.setAdapter(adapter);

        ArrayAdapter adapter2 = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, uhrzeiten);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown_layout2.setAdapter(adapter2);

        ArrayAdapter adaptertag = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, day);
        adaptertag.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tage.setAdapter(adaptertag);

        ArrayAdapter adaptermon = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item,month);
        adaptermon.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monat.setAdapter(adaptermon);

        ArrayAdapter adapterjahr = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, year);
        adapterjahr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jahre.setAdapter(adapterjahr);


        mRef = FirebaseDatabase.getInstance("https://raniaapp-593d2-default-rtdb.europe-west1.firebasedatabase.app").getReference("Termine");
        reference = mRef.child("Termine");
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,arrayList);
        listView.setAdapter(adapter);

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot ds : datasnapshot.getChildren()){
                    String von = ds.child("von").getValue(String.class);
                    String bis = ds.child("bis").getValue(String.class);
                    String tag = ds.child("tag").getValue(String.class);
                    String monat = ds.child("monat").getValue(String.class);
                    String jahr = ds.child("jahr").getValue(String.class);
                    arrayList.add(von + "-" + bis + " am " + tag + "/" + monat + "/" + jahr);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Montag.this,"Keine Verbindung mit der Datenbank...",Toast.LENGTH_LONG).show();
            }
        }; mRef.addListenerForSingleValueEvent(listener);

        plusbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String von = dropdown_layout1.getSelectedItem().toString().trim();
                String bis = dropdown_layout2.getSelectedItem().toString().trim();
                String tagesdatum = tage.getSelectedItem().toString().trim();
                String monatstag = monat.getSelectedItem().toString().trim();
                String jahresdatum = jahre.getSelectedItem().toString().trim();

                Termine = FirebaseDatabase.getInstance("https://raniaapp-593d2-default-rtdb.europe-west1.firebasedatabase.app");
                reference = Termine.getReference("Termine");
                ///push Strategie
                Employee employee = new Employee(von, bis, tagesdatum, monatstag, jahresdatum);
                reference.push().setValue(employee);
            }
        });

        minusbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItems();
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