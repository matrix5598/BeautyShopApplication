package com.example.raniaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText Email;
    private TextInputEditText Password;
    private Button login;
    private ProgressDialog pd;

    TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        login = findViewById(R.id.btn_login);
        Email = findViewById(R.id.edit_email_log);
        Password = (TextInputEditText)findViewById(R.id.edit_password_log);
        forgotPassword = (TextView) findViewById(R.id.forgotPassword);

        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = Email.getText().toString().trim();
                String password = Password.getEditableText().toString().trim();

                if (email.isEmpty() && password.isEmpty()){
                    Toast.makeText(LoginActivity.this,"Bitte alle Felder ausfüllen!",Toast.LENGTH_LONG).show();
                    return;
                }
                else if (email.isEmpty()){
                    Email.setError("Üngültige Email-Adresse");
                    return;
                }
                else if (password.isEmpty()) {
                    Password.setError("Ungültiges Passwort");
                    return;
                }else if(email.length() < 6 && password.length() <6){
                    Email.setError("Ungültige Eingabe");
                    Password.setError("Ungültige Eingabe");
                }else{
                        userLogin();
                    }
            }
        });

        pd = new ProgressDialog(LoginActivity.this);
        pd.setTitle("Authentifizierung...");
        pd.setCanceledOnTouchOutside(false);

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText resetMail = new EditText(LoginActivity.this);

                new AlertDialog.Builder(LoginActivity.this)
                        .setTitle("Passwort zurücksetzen ? ")
                        .setMessage("Geben Sie Ihre Email-Adresse ein, um den Reset Link zu erhalten.")
                        .setView(resetMail)
                        .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String mail = resetMail.getText().toString();

                                mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(LoginActivity.this, "Reset Link wurde erfolgreich gesendet", Toast.LENGTH_LONG).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(LoginActivity.this, "Verbindung fehlgeschlagen..",Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Nein",null)
                        .show();
                return;
            }
        });

    }

    private void userLogin(){
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(LoginActivity.this,UserSuccess.class));
                    finish();
                    pd.setMessage("überprüft Daten...");
                    pd.show();
                    Toast.makeText(LoginActivity.this, "Login erfolgreich!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(LoginActivity.this,"Fehler bei Verbindung", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });



    }
}


