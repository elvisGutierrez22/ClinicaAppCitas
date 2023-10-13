package com.example.appclinicacitas.views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.appclinicacitas.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    private Button botonIrAVistaLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button botonIrAVistaAgendarCIta = findViewById(R.id.btnCita);
        botonIrAVistaAgendarCIta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AgendarCita.class);
                startActivity(intent);
            }
        });

        Button botonIrAVistaVerCitas = findViewById(R.id.btnVerCitas);
        botonIrAVistaVerCitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), VerCitas.class);
                startActivity(intent);
            }
        });

        botonIrAVistaLogin = findViewById(R.id.btnSalirMenu);
        botonIrAVistaLogin.setOnClickListener((v)->showMenu());



    }
    void showMenu() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Confirmar Logout");
        builder.setMessage("¿Está seguro de que desea cerrar sesión?");

        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish(); // Cierra la actividad actual
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // El usuario seleccionó "No", no se hace nada
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
}
}