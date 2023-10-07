package com.example.appclinicacitas.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appclinicacitas.R;

public class Login extends AppCompatActivity {

    EditText editTextEmail, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        Button botonIrAVistaLogin = findViewById(R.id.btnLogin);
        botonIrAVistaLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //if(validateLogin()){
                   // showLoginConfirmation();
                   // Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                   // startActivity(intent);
                //}
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }

        });

        Button botonIrAVistaRegister = findViewById(R.id.btnNewRegister);
        botonIrAVistaRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });
    }
    private boolean validateLogin() {
        String email1 = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        // Validacion de todos los campos vacios
        if (email1.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios.", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validación de correo electrónico
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (!email1.matches(emailPattern)) {
            Toast.makeText(this, "Por favor, introduce una dirección de correo electrónico válida.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void showLoginConfirmation() {
        Toast.makeText(this, "¡Bienvenido!", Toast.LENGTH_LONG).show();
    }
}