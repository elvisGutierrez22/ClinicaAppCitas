package com.example.appclinicacitas.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.appclinicacitas.R;
import com.example.appclinicacitas.views.MainActivity;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class VerCitas extends AppCompatActivity {

    private RecyclerView  recyclerView;
    private ImageButton menuBtn;
    private Spinner spinnerTime;
    CitaAdapter citaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_citas);

        recyclerView = findViewById(R.id.recyler_view);
        menuBtn = findViewById(R.id.btnSalirCitas);
        spinnerTime = findViewById(R.id.spinnerTime);

        String[] horario = {
                "Seleccione un horario",
                "6:00 am - 7:00 am",
                "7:15 am - 8:15 am",
                "8:30 am - 9:30 am",
                "10:00 am - 11:00 am",
                "11:15 am - 12:15 pm",
                "1:00 pm - 2:00 pm",
                "2:15 pm - 3:15 pm",
                "3:30 pm - 4:30 pm",
                "4:45 pm - 5:45 pm",
                "6:00 pm - 7:00 pm"
        };

        ArrayAdapter<String> adapterTime = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, horario);
        adapterTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTime.setAdapter(adapterTime);
        spinnerTime.setSelection(0);

        menuBtn.setOnClickListener((v)->showMenu());
        setupRecyclerView();
    }

    void showMenu(){
        PopupMenu popMenu = new PopupMenu(VerCitas.this, menuBtn);

    }

    void setupRecyclerView(){
        Query query = Utility.getCollectionReferenceForAppointment().orderBy("timestamp",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Cita> options = new FirestoreRecyclerOptions.Builder<Cita>()
                .setQuery(query, Cita.class).build();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        citaAdapter = new CitaAdapter(options, this);
        recyclerView.setAdapter(citaAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        citaAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        citaAdapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        citaAdapter.notifyDataSetChanged();
    }
}