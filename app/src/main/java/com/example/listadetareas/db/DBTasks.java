package com.example.listadetareas.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.listadetareas.entities.Tasks;

import java.util.ArrayList;

public class DBTasks extends DBHelper {

    Context context;

    public DBTasks(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarTarea(String titulo, String descripcion, String fecha, String hora) {

        long id = 0;

        try {
            DBHelper dbHelper = new DBHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("titulo", titulo);
            values.put("descripcion", descripcion);
            values.put("fecha", fecha);
            values.put("hora", hora);

            id = db.insert(TABLE_TASKS, null, values);
        } catch (Exception ex) {
            ex.toString();
        }

        return id;
    }

    public ArrayList<Tasks> mostrarTareas() {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Tasks> listaTareas = new ArrayList<>();
        Tasks tarea = null;
        Cursor cursorTareas = null;

        cursorTareas = db.rawQuery("SELECT * FROM " + TABLE_TASKS + " ORDER BY id ASC", null);

        if (cursorTareas.moveToFirst()) {
            do {
                tarea = new Tasks();
                tarea.setId(cursorTareas.getInt(0));
                tarea.setTitulo(cursorTareas.getString(1));
                tarea.setDescripcion(cursorTareas.getString(2));
                tarea.setFecha(cursorTareas.getString(3));
                tarea.setHora(cursorTareas.getString(4));
                listaTareas.add(tarea);
            } while (cursorTareas.moveToNext());
        }

        cursorTareas.close();

        return listaTareas;
    }

    public Tasks abrirTarea(int id) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Tasks tarea = null;
        Cursor cursorTareas = null;

        cursorTareas = db.rawQuery("SELECT * FROM " + TABLE_TASKS + " WHERE id = " + id + " LIMIT 1", null);

        if (cursorTareas.moveToFirst()) {
            tarea = new Tasks();
            tarea.setId(cursorTareas.getInt(0));
            tarea.setTitulo(cursorTareas.getString(1));
            tarea.setDescripcion(cursorTareas.getString(2));
            tarea.setFecha(cursorTareas.getString(3));
            tarea.setHora(cursorTareas.getString(4));
        }

        cursorTareas.close();

        return tarea;
    }

    public boolean editarTarea(int id, String titulo, String descripcion, String fecha, String hora) {

        boolean correcto = false;

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLE_TASKS + " SET titulo = '"+ titulo + "', descripcion = '"+ descripcion + "', fecha = '"+ fecha + "', hora = '"+ hora + "' WHERE id = '" + id + "'");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }

    public boolean eliminarTarea(int id) {

        boolean correcto = false;

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLE_TASKS + " WHERE id = '" + id + "'");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }
}
