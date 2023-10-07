package com.example.appclinicacitas.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.appclinicacitas.R;
import com.example.appclinicacitas.views.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    private EditText editTextFirstName, editTextLastName, editTextEdad,
            editTextPhone, editTextEmailAddress, editTextPassword1, editTextPassword2;

    private Button buttonCreateAcount;
    private Button botonIrAVistaLogin2;

    private ProgressBar progressBar;

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
        buttonCreateAcount = findViewById(R.id.btnRegister);
        botonIrAVistaLogin2 = findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.progressBar);

        buttonCreateAcount.setOnClickListener(v->createAccount());

        botonIrAVistaLogin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
    }

    private void showRegistrationConfirmation() {
        Toast.makeText(this, "¡Registro exitoso! Gracias por registrarte.", Toast.LENGTH_LONG).show();
    }

    void createAccount(){
        String name = editTextFirstName.getText().toString();
        String name2 = editTextLastName.getText().toString();
        String age = editTextEdad.getText().toString();
        String phone = editTextPhone.getText().toString();
        String email = editTextEmailAddress.getText().toString();
        String password = editTextPassword1.getText().toString();
        String confirmPassword = editTextPassword2.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)){
            Toast.makeText(Register.this, "Los campos están vacíos", Toast.LENGTH_SHORT).show();
        } else {
            boolean isValid = validateData(name, name2, age, phone, email, password, confirmPassword);
            if(isValid){
                createAccountinFirebase(name, name2, age, phone, email, password);
            }
        }
    }


    void createAccountinFirebase(String name, String name2, String age, String phone, String email, String password) {
        changeInProgress(true);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, password).
                addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                changeInProgress(false);
                if (task.isSuccessful()) {
                    // El usuario se ha registrado correctamente.
                    Toast.makeText(Register.this,"Cuenta creada correctamente, verificar email", Toast.LENGTH_SHORT).show();;
                    firebaseAuth.getCurrentUser().sendEmailVerification();
                    firebaseAuth.signOut();
                    finish();
                    // Ahora, puedes guardar los detalles adicionales del usuario en Firestore o Realtime Database.
                } else {
                    Toast.makeText(Register.this, task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
                changeInProgress(false);
            }
        });
    }

    boolean validateData(String firstName, String lastName, String age, String phone, String email, String password1, String password2) {
        String namePattern = "^[a-zA-Z]{1,30}$";
        String phonePattern = "^[0-9]{8}$";
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";

        boolean isValid = true;

        // Validación de primer nombre
        if (!firstName.matches(namePattern)) {
            editTextFirstName.setError("El primer nombre solo debe contener letras y tener un máximo de 30 caracteres.");
            isValid = false;
        }

        // Validación de apellido
        if (!lastName.matches(namePattern)) {
            editTextLastName.setError("El apellido solo debe contener letras y tener un máximo de 30 caracteres.");
            isValid = false;
        }

        // Validación de teléfono
        if (!phone.matches(phonePattern)) {
            editTextPhone.setError("El número de teléfono debe contener exactamente 8 dígitos numéricos.");
            isValid = false;
        }

        // Validación de correo electrónico
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmailAddress.setError("El correo ingresado es invalido");
            return false;
        }

        // Validación de contraseña
        if(password1.length() < 6){
            editTextPassword1.setError("Debe ingresar mas de 6 caracteres");
            return false;
        }

        // Validación de contraseña
        if(!password2.equals(password1)){
            editTextPassword2.setError("Las contraseñas no son iguales");
            return false;
        }
        return true;
    }

    void changeInProgress(boolean inProgress) {
        if(inProgress){
            progressBar.setVisibility(View.VISIBLE);
            buttonCreateAcount.setVisibility(View.GONE);
        }else{
            progressBar.setVisibility(View.GONE);
            buttonCreateAcount.setVisibility(View.VISIBLE);
        }
    }
}