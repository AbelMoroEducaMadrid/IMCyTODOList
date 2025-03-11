package com.ventura.imcytodolist.todolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ventura.imcytodolist.R

/**
 * Adaptador para el RecyclerView que muestra la lista de tareas.
 * Permite marcar tareas como completadas y eliminarlas.
 *
 * @param tasks Lista mutable de tareas a mostrar.
 * @param onTaskSelected Callback para notificar cuando se marca/desmarca una tarea.
 * @param onTaskDeleted Callback para notificar cuando se elimina una tarea.
 */
class TasksAdapter(
    var tasks: List<Task>,
    private val onTaskSelected: (Int) -> Unit,
    private val onTaskDeleted: (Int) -> Unit
) : RecyclerView.Adapter<TasksViewHolder>() {

    /**
     * Crea un nuevo ViewHolder inflando el layout de un ítem de tarea.
     *
     * @param parent El ViewGroup padre (RecyclerView).
     * @param viewType Tipo de vista (no usado aquí).
     * @return Un nuevo TasksViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lista_task, parent, false)
        return TasksViewHolder(view)
    }

    /**
     * Vincula los datos de una tarea al ViewHolder en la posición especificada.
     *
     * @param holder El ViewHolder a vincular.
     * @param position La posición de la tarea en la lista.
     */
    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        holder.render(tasks[position], onTaskSelected, onTaskDeleted) // Renderiza la tarea
    }

    /**
     * Devuelve el número total de tareas en la lista.
     *
     * @return Tamaño de la lista de tareas.
     */
    override fun getItemCount() = tasks.size
}