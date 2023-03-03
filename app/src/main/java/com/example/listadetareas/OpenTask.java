package com.example.listadetareas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.listadetareas.db.DBTasks;
import com.example.listadetareas.entities.Tasks;

public class OpenTask extends AppCompatActivity {

    private EditText etTitulo, etDescripcion, etFecha, etHora;
    private Button btnEditarGuardar, btnTareaCompletada;
    private TextView tvOpenTask;

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

        DBTasks dbTasks = new DBTasks(OpenTask.this);
        task = dbTasks.abrirTarea(id);

        tvOpenTask.setText(task.getTitulo());

        if(task != null) {
            etTitulo.setText(task.getTitulo());
            etDescripcion.setText(task.getDescripcion());
            etFecha.setText(task.getFecha());
            etHora.setText(task.getHora());

            etTitulo.setInputType(InputType.TYPE_NULL);
            etDescripcion.setInputType(InputType.TYPE_NULL);
            etFecha.setInputType(InputType.TYPE_NULL);
            etHora.setInputType(InputType.TYPE_NULL);
        }

        btnEditarGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OpenTask.this, EditTask.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        btnTareaCompletada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(OpenTask.this);
                builder.setMessage("Marcar como completada").setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(dbTasks.eliminarTarea(id)) {
                            Intent intent = new Intent(OpenTask.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                }) .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
            }
        });
    }
}