package com.example.listadetareas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.listadetareas.db.DBTasks;
import com.example.listadetareas.entities.Tasks;

public class EditTask extends AppCompatActivity {

    private EditText etTitulo, etDescripcion, etFecha, etHora;
    private Button btnEditarGuardar;
    private TextView tvOpenTask, btnTareaCompletada;
    boolean correcto = false;

    Tasks task;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_task);

        etTitulo = findViewById(R.id.etTitulo);
        etDescripcion = findViewById(R.id.etDescripcion);
        etFecha = findViewById(R.id.etFecha);
        etHora = findViewById(R.id.etHora);
        btnEditarGuardar = findViewById(R.id.btnEditarGuardar);
        btnTareaCompletada = findViewById(R.id.btnTareaCompletada);
        tvOpenTask = findViewById(R.id.tvOpenTask);

        tvOpenTask.setText(R.string.editar_tarea);
        btnEditarGuardar.setText(R.string.actualizar_tarea);

        if(savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                id = Integer.parseInt(null);
            } else {
                id = extras.getInt("id");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("id");
        }

        final DBTasks dbTasks = new DBTasks(EditTask.this);
        task = dbTasks.abrirTarea(id);

        if(task != null) {
            etTitulo.setText(task.getTitulo());
            etDescripcion.setText(task.getDescripcion());
            etFecha.setText(task.getFecha());
            etHora.setText(task.getHora());
        }

        btnEditarGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etTitulo.getText().toString().isEmpty() || !etDescripcion.getText().toString().isEmpty() || !etFecha.getText().toString().isEmpty() || !etHora.getText().toString().isEmpty()) {
                    correcto = dbTasks.editarTarea(id, etTitulo.getText().toString(), etDescripcion.getText().toString(), etFecha.getText().toString(), etHora.getText().toString());

                    if(correcto) {
                        Toast.makeText(EditTask.this, "Tarea modificada", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(EditTask.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(EditTask.this, "No se ha podido modificar esta tarea", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(EditTask.this, "No se puede dejar campos vacios", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void verTarea() {
        Intent intent = new Intent(EditTask.this, OpenTask.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}