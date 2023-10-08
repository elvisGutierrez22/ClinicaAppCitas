package com.example.appclinicacitas.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.appclinicacitas.R;
import com.example.appclinicacitas.views.MainActivity;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class VerCitas extends AppCompatActivity {

    private RecyclerView  recyclerView;
    private ImageButton menuBtn;

    CitaAdapter citaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_citas);

        recyclerView = findViewById(R.id.recyler_view);
        menuBtn = findViewById(R.id.btnSalirCitas);

        menuBtn.setOnClickListener((v)->showMenu());
        setupRecyclerView();
    }

    void showMenu(){

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