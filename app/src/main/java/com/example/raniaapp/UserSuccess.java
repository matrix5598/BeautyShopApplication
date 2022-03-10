package com.example.raniaapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.AdapterView;
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

        ImageButton del = findViewById(R.id.btn_del);



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

        mRef = FirebaseDatabase.getInstance("https://raniaapp-593d2-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("KundenDaten");
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot ds, @Nullable String previousChildName) {
                String behandlung = ds.child("Behandlung").getValue(String.class);
                String email = ds.child("Email").getValue(String.class);
                String handy = ds.child("Handynummer").getValue(String.class);
                String name = ds.child("Name").getValue(String.class);
                String termin = ds.child("Termin").getValue(String.class);
                myArrayList.add("Kunde: " + name + ",\n" + "Email: " + email + " ,\n" + "Nr.: " + handy + " ,\n"  + "Art: " +  behandlung + " ,\n" + "Datum: " + termin);
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

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(UserSuccess.this)
                        .setIcon(android.R.drawable.ic_menu_delete)
                        .setTitle("Sicher?")
                        .setMessage("Wollen Sie wirklich alle Kunden Termine löschen?")
                        .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                myArrayList.clear();
                                mRef.removeValue();
                                adapter.notifyDataSetChanged();
                                Toast.makeText(UserSuccess.this,"Alle Kunden Termine wurden erfolgreich gelöscht", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("Nein", null)
                        .show();
                return;

            }
        });

        myListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int which_item = position;

                new AlertDialog.Builder(UserSuccess.this)
                        .setIcon(android.R.drawable.ic_menu_delete)
                        .setTitle("Sicher?")
                        .setMessage("Ist dieser Termin schon erledigt? Wirklich Löschen ?!")
                        .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                myArrayList.remove(which_item);
                                adapter.notifyDataSetChanged();
                                Toast.makeText(UserSuccess.this, "Erfolgreich gelöscht!", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("Nein", null)
                        .show();
                return true;
            }
        });


        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int position = i;
                final int ID = (int) myListView.getItemIdAtPosition(i);

                new AlertDialog.Builder(UserSuccess.this)
                        .setIcon(android.R.drawable.stat_sys_speakerphone)
                        .setTitle("Anruf")
                        .setMessage("Wollen Sie diesen Kunden wirklich anrufen ?")
                        .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mRef = FirebaseDatabase.getInstance("https://raniaapp-593d2-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("KundenDaten");
                                Toast.makeText(UserSuccess.this, String.valueOf(position), Toast.LENGTH_SHORT).show();

                            }
                        })
                        .setNegativeButton("Nein", null)
                        .show();
                return;


            }
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



