package com.example.appclinicacitas.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appclinicacitas.R;
import com.example.appclinicacitas.views.Login;

public class Register extends AppCompatActivity {

    private EditText editTextFirstName, editTextLastName, editTextEdad,
            editTextPhone, editTextEmailAddress, editTextPassword1, editTextPassword2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextEdad = findViewById(R.id.editTextEdad);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextEmailAddress = findViewById(R.id.editTextEmailAddress);
        editTextPassword1 = findViewById(R.id.editTextPassword1);
        editTextPassword2 = findViewById(R.id.editTextPassword2);

        Button botonIrAVistaLogin = findViewById(R.id.btnRegister);
        Button botonIrAVistaLogin2 = findViewById(R.id.btnLogin);

        botonIrAVistaLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateForm()) {
                    showRegistrationConfirmation();
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                }
            }
        });

        botonIrAVistaLogin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
    }


    private boolean validateForm() {
        String firstName = editTextFirstName.getText().toString();
        String lastName = editTextLastName.getText().toString();
        String age = editTextEdad.getText().toString();
        String phone = editTextPhone.getText().toString();
        String email = editTextEmailAddress.getText().toString();
        String password1 = editTextPassword1.getText().toString();
        String password2 = editTextPassword2.getText().toString();

        // Validacion de todos los campos vacios
        if (firstName.isEmpty() || lastName.isEmpty() || age.isEmpty() || email.isEmpty() || password1.isEmpty() || password2.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios.", Toast.LENGTH_SHORT).show();
            return false;
        }

        String firstName2 = editTextFirstName.getText().toString();
        String lastName2 = editTextLastName.getText().toString();

        // Validación de nombre y apellido
        String namePattern = "^[a-zA-Z]{1,30}$";

        if (!firstName2.matches(namePattern)) {
            Toast.makeText(this, "El primer nombre solo debe contener letras y tener un máximo de 30 caracteres.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!lastName2.matches(namePattern)) {
            Toast.makeText(this, "El apellido solo debe contener letras y tener un máximo de 30 caracteres.", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validación de teléfono
        String phonePattern = "^[0-9]{8}$";

        if (!phone.matches(phonePattern)) {
            Toast.makeText(this, "El número de teléfono debe contener exactamente 8 dígitos numéricos.", Toast.LENGTH_SHORT).show();
            return false;
        }

        String email1 = editTextEmailAddress.getText().toString();

        // Validación de correo electrónico
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (!email1.matches(emailPattern)) {
            Toast.makeText(this, "Por favor, introduce una dirección de correo electrónico válida.", Toast.LENGTH_SHORT).show();
            return false;
        }

        String password = editTextPassword1.getText().toString();
        String confirmpassword = editTextPassword2.getText().toString();

        // Validación de contraseña
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";

        if (!password.matches(passwordPattern)) {
            Toast.makeText(this, "La contraseña debe tener al menos 8 caracteres y contener al menos una letra, un número y un carácter especial.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!confirmpassword.equals(password)) {
            Toast.makeText(this, "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void showRegistrationConfirmation() {
        Toast.makeText(this, "¡Registro exitoso! Gracias por registrarte.", Toast.LENGTH_LONG).show();
    }
}