package com.example.appclinicacitas.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.appclinicacitas.R;

public class MainActivity extends AppCompatActivity {


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

        Button botonIrAVistaLogin = findViewById(R.id.btnSalirMenu);
        botonIrAVistaLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
    }
}