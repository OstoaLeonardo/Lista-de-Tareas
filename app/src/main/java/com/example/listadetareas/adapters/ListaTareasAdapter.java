package com.example.listadetareas.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listadetareas.OpenTask;
import com.example.listadetareas.R;
import com.example.listadetareas.db.DBTasks;
import com.example.listadetareas.entities.Tasks;

import java.util.ArrayList;

public class ListaTareasAdapter extends RecyclerView.Adapter<ListaTareasAdapter.TareasViewHolder> {

    ArrayList<Tasks> listaTareas;

    public ListaTareasAdapter(ArrayList<Tasks> listaTareas) {
        this.listaTareas = listaTareas;
    }

    @NonNull
    @Override
    public TareasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_task, parent, false);
        return new TareasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TareasViewHolder holder, int position) {
        holder.tvTituloCard.setText(listaTareas.get(position).getTitulo());
        holder.tvDescripcionCard.setText(listaTareas.get(position).getDescripcion());
        holder.tvFechaCard.setText(listaTareas.get(position).getFecha());
        holder.tvHoraCard.setText(listaTareas.get(position).getHora());

        holder.btnCompletada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("¿Marcar como completada?");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int id = listaTareas.get(position).getId();
                        DBTasks dbTasks = new DBTasks(view.getContext());
                        dbTasks.eliminarTarea(id);
                        listaTareas.remove(position);
                        notifyItemRemoved(position);
                    }
                });
                builder.setNegativeButton("No", null).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaTareas.size();
    }

    public class TareasViewHolder extends RecyclerView.ViewHolder {

        TextView tvTituloCard, tvDescripcionCard, tvFechaCard, tvHoraCard;
        Button btnCompletada;

        public TareasViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTituloCard = itemView.findViewById(R.id.tvTituloCard);
            tvDescripcionCard = itemView.findViewById(R.id.tvDescripcionCard);
            tvFechaCard = itemView.findViewById(R.id.tvFechaCard);
            tvHoraCard = itemView.findViewById(R.id.tvHoraCard);
            btnCompletada = itemView.findViewById(R.id.btnCompletada);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, OpenTask.class);
                    intent.putExtra("id", listaTareas.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });
        }
    }
}
