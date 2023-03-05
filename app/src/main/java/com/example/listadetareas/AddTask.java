package com.example.listadetareas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.listadetareas.db.DBTasks;

import java.util.Calendar;

public class AddTask extends AppCompatActivity {

    private EditText etTitulo, etDescripcion, etFecha, etHora;
    private Button btnGuardar;
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        etTitulo = findViewById(R.id.etTitulo);
        etDescripcion = findViewById(R.id.etDescripcion);
        etFecha = findViewById(R.id.etFecha);
        etHora = findViewById(R.id.etHora);
        btnGuardar = findViewById(R.id.btnEditarGuardar);

        etFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(AddTask.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        etFecha.setText(day + "/" + (month + 1) + "/" + year);
                    }
                }, year, day, month);

                datePickerDialog.show();
            }
        });

        etHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(AddTask.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        etHora.setText(hour + ":" + minute);
                    }
                }, hour, minute, false);

                timePickerDialog.show();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBTasks dbTasks = new DBTasks(AddTask.this);
                long id = dbTasks.insertarTarea(etTitulo.getText().toString(), etDescripcion.getText().toString(), etFecha.getText().toString(), etHora.getText().toString());

                if (id > 0) {
                    Toast.makeText(AddTask.this, "Tarea agregada correctamente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddTask.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(AddTask.this, "Algo ha fallado exitosamente", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}