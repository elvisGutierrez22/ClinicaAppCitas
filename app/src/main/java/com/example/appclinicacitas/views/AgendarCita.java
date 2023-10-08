package com.example.appclinicacitas.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.appclinicacitas.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AgendarCita extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextPhone;
    private Spinner spinnerType;
    private Spinner spinnerTime;
    private EditText editTextDate;
    private Button botonIrAVistaPrincipal;
    private Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendar_cita);

        spinnerType = findViewById(R.id.spinnerType);
        spinnerTime = findViewById(R.id.spinnerTime);
        editTextName = findViewById(R.id.editTextName);
        editTextPhone = findViewById(R.id.editTextPhone);
        botonIrAVistaPrincipal = findViewById(R.id.btnAgendarCita);
        // Referencia al EditText para la fecha
        editTextDate = findViewById(R.id.editTextDate);
        calendar = Calendar.getInstance();

        String[] elementos = {"Seleccione un servicio","Limpieza dental", "Extraccion dental", "Empastes dental", "Endodoncia",
                                "Corona dental", "Implante dental", "Ortodoncia", "Blanqueamiento dental", "Tratamiento de encía",
                                "Prótesis dental", "Radiografía dental", "Cirugía oral y maxilofacial", "Tratamiento de ortodoncia invisible",
                                "Tratamiento de odontopediatría", "Tratamiento de periodoncia"};
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

        ArrayAdapter<String> adapterType = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, elementos);
        ArrayAdapter<String> adapterTime = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, horario);

        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerType.setAdapter(adapterType);
        spinnerType.setSelection(0);
        spinnerTime.setAdapter(adapterTime);
        spinnerTime.setSelection(0);

        // Configura un OnClickListener para mostrar el DatePickerDialog cuando se hace clic en el EditText
        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        botonIrAVistaPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cita cita = new Cita();
                String selectedService = spinnerType.getSelectedItem().toString();
                String selectedTime = spinnerTime.getSelectedItem().toString();
                String selectedDate = editTextDate.getText().toString();
                String name = editTextName.getText().toString();
                String phone = editTextPhone.getText().toString();

                if (name.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Por favor, Ingrese un nombre", Toast.LENGTH_SHORT).show();
                } else if (!name.matches("[a-zA-Z]+")) {
                    Toast.makeText(getApplicationContext(), "El nombre solo debe contener letras", Toast.LENGTH_SHORT).show();
                }else if (phone.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Por favor, Ingrese un numero de telefono", Toast.LENGTH_SHORT).show();
                }else if (phone.length() != 8) {
                    Toast.makeText(getApplicationContext(), "El número de teléfono debe tener exactamente 8 dígitos", Toast.LENGTH_SHORT).show();
                }else if (selectedService.equals("Seleccione un servicio")) {
                    Toast.makeText(getApplicationContext(), "Por favor, seleccione un servicio válido", Toast.LENGTH_SHORT).show();
                } else if (selectedTime.equals("Seleccione un horario")) {
                    Toast.makeText(getApplicationContext(), "Por favor, seleccione un horario válido", Toast.LENGTH_SHORT).show();
                } else if (selectedDate.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Por favor, seleccione una fecha válida", Toast.LENGTH_SHORT).show();
                } else {

                    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                        // El usuario está autenticado, puedes llamar a saveAppointmentToFirebase() aquí
                        saveAppointment();
                    } else {
                        // El usuario no está autenticado, muestra un mensaje o toma alguna acción apropiada
                        Utility.showToast(AgendarCita.this, "Usuario no autenticado");
                    }

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });

// ...

    }

    private void showDatePickerDialog() {
        // Obtén la fecha actual
        Calendar currentDate = Calendar.getInstance();

        // Crea el DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar selectedCalendar = Calendar.getInstance();
                selectedCalendar.set(year, month, day);

                // Compara la fecha seleccionada con la fecha actual
                if (selectedCalendar.before(currentDate)) {
                    // Si la fecha seleccionada es anterior a la fecha actual, muestra un mensaje de error
                    Toast.makeText(getApplicationContext(), "Seleccione una fecha válida", Toast.LENGTH_SHORT).show();
                } else {
                    // Si la fecha seleccionada es válida, actualiza el campo de fecha
                    calendar.set(year, month, day);
                    updateEditTextDate();
                }
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }


    private void updateEditTextDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        editTextDate.setText(sdf.format(calendar.getTime()));
    }

    void saveAppointment(){
        String namePatient = editTextName.getText().toString();
        String numberPatient = editTextPhone.getText().toString();
        String service = spinnerType.getSelectedItem().toString();
        String date = editTextDate.getText().toString();
        String time = spinnerTime.getSelectedItem().toString();

        Cita cita = new Cita();
        cita.setName(namePatient);
        cita.setNumber(numberPatient);
        cita.setServices(service);
        cita.setDate(date);
        cita.setTime(time);
        cita.setTimestamp(Timestamp.now());

        saveAppointmentToFirebase(cita);
    }

    void saveAppointmentToFirebase(Cita cita){
        DocumentReference documentReference;

        documentReference = Utility.getCollectionReferenceForAppointment().document();

        documentReference.set(cita).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Utility.showToast(AgendarCita.this, "Cita agendada correctamente");
                    finish();
                }else{
                    Utility.showToast(AgendarCita.this, "Fallo al agendar cita");
                }
            }
        });
    }
}
