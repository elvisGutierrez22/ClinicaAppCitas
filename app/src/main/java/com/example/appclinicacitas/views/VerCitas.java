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
import androidx.appcompat.widget.SearchView;

import com.example.appclinicacitas.R;
import com.example.appclinicacitas.views.MainActivity;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class VerCitas extends AppCompatActivity {

    private RecyclerView  recyclerView;
    private SearchView searchView;
    CitaAdapter citaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_citas);

        recyclerView = findViewById(R.id.recyler_view);
        searchView = findViewById(R.id.searchViewhome);

        setupRecyclerView();
        setupSearchView();
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

    void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String queryText) {
                Toast.makeText(VerCitas.this,"Resultados...", Toast.LENGTH_SHORT).show();
                buscarcita(queryText);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText){
                if (newText.isEmpty()) {
                    buscarcita(newText);
                }
                return false;
            }
        });
    }

    void buscarcita(String queryText){
        FirestoreRecyclerOptions<Cita> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Cita>()
                .setQuery(Utility.getCollectionReferenceForAppointment().orderBy("name")
                        .startAt(queryText).endAt(queryText+"\uf8ff"), Cita.class).build();
        citaAdapter = new CitaAdapter(firestoreRecyclerOptions, this);
        citaAdapter.startListening() ;
        recyclerView.setAdapter(citaAdapter);
    }


}