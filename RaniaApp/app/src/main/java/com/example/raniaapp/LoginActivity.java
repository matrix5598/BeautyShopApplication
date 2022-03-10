package com.example.raniaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText Email;
    private TextInputEditText Password;
    private Button back, login;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        login = findViewById(R.id.btn_login);
        back = findViewById(R.id.btn_back);
        Email = findViewById(R.id.edit_email_log);
        Password = (TextInputEditText)findViewById(R.id.edit_password_log);

        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent zuruck = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(zuruck);
                finish();
            }
        });

        pd = new ProgressDialog(LoginActivity.this);
        pd.setTitle("Authentifizierung...");
        pd.setCanceledOnTouchOutside(false);
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


