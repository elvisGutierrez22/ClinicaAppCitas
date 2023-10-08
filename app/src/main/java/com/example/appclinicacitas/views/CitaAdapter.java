package com.example.appclinicacitas.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appclinicacitas.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class CitaAdapter extends FirestoreRecyclerAdapter<Cita, CitaAdapter.CitaViewHolder> {

    Context context;

    public CitaAdapter(@NonNull FirestoreRecyclerOptions<Cita> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull CitaViewHolder holder, int position, @NonNull Cita cita) {
        holder.serviceTextView.setText(cita.services);
        holder.nameTextView.setText(cita.name);
        holder.phoneTextView.setText(cita.number);
        holder.timeTextView.setText(cita.time);
        holder.dateTextView.setText(cita.date);
        holder.timestampTextView.setText(Utility.timestampToString(cita.timestamp));
    }

    @NonNull
    @Override
    public CitaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cita_item, parent, false);
        return new CitaViewHolder(view);
    }

    class CitaViewHolder extends RecyclerView.ViewHolder{

        TextView serviceTextView, nameTextView, phoneTextView, timeTextView, dateTextView, timestampTextView;

        public CitaViewHolder(@NonNull View itemView) {
            super(itemView);

            serviceTextView = itemView.findViewById(R.id.cita_service);
            nameTextView = itemView.findViewById(R.id.cita_name);
            phoneTextView = itemView.findViewById(R.id.cita_number);
            timeTextView = itemView.findViewById(R.id.cita_time);
            dateTextView = itemView.findViewById(R.id.cita_date);
            timestampTextView = itemView.findViewById(R.id.cita_timestamp_text_view);



        }
    }
}
