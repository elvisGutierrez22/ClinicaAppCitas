package com.example.appclinicacitas.views;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

        holder.itemView.setOnClickListener((v) -> {
            Intent intent = new Intent(context, AgendarCita.class);
            //mando a llamar los datos ya agregndados en
            intent.putExtra("name", cita.name);
            intent.putExtra("number", cita.number);
            intent.putExtra("services", cita.services);
            intent.putExtra("date", cita.date);
            intent.putExtra("time", cita.time);
            intent.putExtra("timestamp", cita.timestamp);
            String id = this.getSnapshots().getSnapshot(position).getId();
            intent.putExtra("id", id);
            context.startActivity(intent);

        });

        //Eliminar cita
        holder.deleteButton.setOnClickListener(v -> {

            String id = this.getSnapshots().getSnapshot(position).getId();

            Utility.getCollectionReferenceForAppointment().document(id).delete().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Utility.showToast(context, "Cita eliminada");
                    // Actualiza el RecyclerView después de la eliminación
                    notifyDataSetChanged();
                } else {
                    Utility.showToast(context, "Error al eliminar cita");
                }
            });

        });
    }

    @NonNull
    @Override
    public CitaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cita_item, parent, false);
        return new CitaViewHolder(view);
    }

    class CitaViewHolder extends RecyclerView.ViewHolder{

        TextView serviceTextView, nameTextView, phoneTextView, timeTextView, dateTextView, timestampTextView;

        ImageView deleteButton;
        public CitaViewHolder(@NonNull View itemView) {
            super(itemView);

            serviceTextView = itemView.findViewById(R.id.cita_service);
            nameTextView = itemView.findViewById(R.id.cita_name);
            phoneTextView = itemView.findViewById(R.id.cita_number);
            timeTextView = itemView.findViewById(R.id.cita_time);
            dateTextView = itemView.findViewById(R.id.cita_date);
            timestampTextView = itemView.findViewById(R.id.cita_timestamp_text_view);
            deleteButton = itemView.findViewById(R.id.deleteButton);



        }
    }
}
