package com.example.raniaapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.Logger;

import java.sql.Ref;
import java.util.ArrayList;
import java.util.List;

public class UserSuccess extends AppCompatActivity {

    private Button mo;
    ListView myListView;

    ArrayList<String> myArrayList = new ArrayList<>();
    DatabaseReference mRef;
    ArrayList<String> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_success);

        ImageButton logoutbtn = findViewById(R.id.logout);
        mo = (Button)findViewById(R.id.btn_mo);
        mo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mon = new Intent(UserSuccess.this, Montag.class);
                startActivity(mon);
                finish();
            }
        });


        myListView = (ListView) findViewById(R.id.listviewKunden);
        list = new ArrayList<String>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(UserSuccess.this,android.R.layout.simple_list_item_1,myArrayList);
        myListView.setAdapter(adapter);

        mRef = FirebaseDatabase.getInstance("https://raniaapp-593d2-default-rtdb.europe-west1.firebasedatabase.app").getReference("KundenDaten");

        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot ds, @Nullable String previousChildName) {
                String behandlung = ds.child("behandlungs").getValue(String.class);
                String email = ds.child("emailKey").getValue(String.class);
                String handy = ds.child("handyKey").getValue(String.class);
                String name = ds.child("vorNachnameKey").getValue(String.class);
                String termin = ds.child("termins").getValue(String.class);
                myArrayList.add(name + " , " + email + " , " + handy + " , " + behandlung + " , " + termin);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                adapter.notifyDataSetChanged(); }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) { }
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) { }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent logo = new Intent(UserSuccess.this, MainActivity.class);
                startActivity(logo);
                finish();
            }

        });
    }

}



