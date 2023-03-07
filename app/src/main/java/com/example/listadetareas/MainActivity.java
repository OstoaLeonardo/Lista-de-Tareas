package com.example.listadetareas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.listadetareas.adapters.ListaTareasAdapter;
import com.example.listadetareas.db.DBHelper;
import com.example.listadetareas.db.DBTasks;
import com.example.listadetareas.entities.Tasks;

import java.util.ArrayList;

public class MainActivity extends MenuTopBar {

    private EditText etBuscar;
    private Button btnAgregar, btnBuscar, btnCompletada;
    private RecyclerView rvTareas;
    private ArrayList<Tasks> listaArrayTareas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbarMain();

        btnAgregar = findViewById(R.id.btnAgregar);
        btnBuscar = findViewById(R.id.btnBuscar);
        etBuscar = findViewById(R.id.etBuscar);
        rvTareas = findViewById(R.id.rvTareas);
        rvTareas.setLayoutManager(new LinearLayoutManager(this));

        DBTasks dbTasks = new DBTasks(MainActivity.this);

        listaArrayTareas = new ArrayList<>();

        ListaTareasAdapter adapter = new ListaTareasAdapter(dbTasks.mostrarTareas());
        rvTareas.setAdapter(adapter);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*DBHelper dbHelper = new DBHelper(MainActivity.this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                if(db != null) {
                    Toast.makeText(MainActivity.this, "DB Creada", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "DB No Creada", Toast.LENGTH_LONG).show();
                }*/

                Intent intent = new Intent(MainActivity.this, AddTask.class);
                startActivity(intent);
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = etBuscar.getText().toString();

                DBTasks dbTasks = new DBTasks(MainActivity.this);

                listaArrayTareas = dbTasks.buscarTareas(query);

                ListaTareasAdapter adapter = new ListaTareasAdapter(listaArrayTareas);
                rvTareas.setAdapter(adapter);
            }
        });

        etBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = etBuscar.getText().toString();

                DBTasks dbTasks = new DBTasks(MainActivity.this);

                listaArrayTareas = dbTasks.buscarTareas(query);

                ListaTareasAdapter adapter = new ListaTareasAdapter(listaArrayTareas);
                rvTareas.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
}