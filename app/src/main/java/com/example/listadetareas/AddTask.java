package com.example.listadetareas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.listadetareas.db.DBTasks;

public class AddTask extends AppCompatActivity {

    private EditText etTitulo, etDescripcion, etFecha, etHora;
    private Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        etTitulo = findViewById(R.id.etTitulo);
        etDescripcion = findViewById(R.id.etDescripcion);
        etFecha = findViewById(R.id.etFecha);
        etHora = findViewById(R.id.etHora);
        btnGuardar = findViewById(R.id.btnEditarGuardar);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBTasks dbTasks = new DBTasks(AddTask.this);
                long id = dbTasks.insertarTarea(etTitulo.getText().toString(), etDescripcion.getText().toString(), etFecha.getText().toString(), etHora.getText().toString());

                if (id > 0) {
                    Toast.makeText(AddTask.this, "Tarea agregada correctamente", Toast.LENGTH_SHORT).show();
                    limpiar();
                } else {
                    Toast.makeText(AddTask.this, "Algo ha fallado exitosamente", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void limpiar() {
        Intent intent = new Intent(AddTask.this, MainActivity.class);
        startActivity(intent);
    }
}