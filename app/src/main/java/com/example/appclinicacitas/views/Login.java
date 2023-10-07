package com.example.appclinicacitas.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.appclinicacitas.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.rpc.context.AttributeContext;

public class Login extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button botonIrAVistaPrincipal;
    private Button botonIrAVistaRegister;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        botonIrAVistaPrincipal = findViewById(R.id.btnLogin);
        botonIrAVistaRegister = findViewById(R.id.btnNewRegister);
        progressBar = findViewById(R.id.progressBar);

        botonIrAVistaPrincipal.setOnClickListener((v)->loginUser());
        botonIrAVistaRegister.setOnClickListener((v)->startActivity(new Intent(Login.this, Register.class)));
    }

    void loginUser(){
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        boolean isValidated = validateData(email, password);
        if (!isValidated) {//Si los datos de inicio de sesión son válidos
            return;
        }
        //Se llama a una función llamada loginAccountInFirebase(email, password) con los valores de email y password como argumentos
        loginAccountInFirebase(email, password);
    }

    void loginAccountInFirebase(String email, String password){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        changeInProgress(true);
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                changeInProgress(false);
                if (task.isSuccessful()){

                    if(firebaseAuth.getCurrentUser().isEmailVerified()){
                        startActivity(new Intent(Login.this, MainActivity.class));
                        showLoginConfirmation();
                    }else{
                        Utility.showToast(Login.this, "Email no verificado");
                    }
                }else{
                    Utility.showToast(Login.this, task.getException().getLocalizedMessage());
                    showLoginConfirmation();
                }
            }
        });
    }

    void changeInProgress(boolean inProgress) {
        if(inProgress){
            progressBar.setVisibility(View.VISIBLE);
            botonIrAVistaPrincipal.setVisibility(View.GONE);
        }else{
            progressBar.setVisibility(View.GONE);
            botonIrAVistaPrincipal.setVisibility(View.VISIBLE);
        }
    }

    boolean validateData(String email, String password) {

        // Validación de correo electrónico
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("El correo ingresado es invalido");
            return false;
        }

        // Validación de contraseña
        if(password.length() < 6){
            editTextPassword.setError("Debe ingresar mas de 6 caracteres");
            return false;
        }
        return true;
    }


    private void showLoginConfirmation() {
        Toast.makeText(this, "¡Bienvenido!", Toast.LENGTH_LONG).show();
    }
}