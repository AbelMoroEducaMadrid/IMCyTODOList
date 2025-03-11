package com.ventura.imcytodolist.todolist

import android.content.res.ColorStateList
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ventura.imcytodolist.R
import android.graphics.Paint

/**
 * ViewHolder para mostrar tareas individuales en un RecyclerView.
 * Permite marcar tareas como completadas y eliminarlas.
 *
 * @param view La vista inflada para cada ítem del RecyclerView.
 */
class TasksViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    // Referencias a los elementos de la UI
    private val tvTask: TextView = view.findViewById(R.id.tvTask)
    private val cbTask: CheckBox = view.findViewById(R.id.cbTask)
    private val ivDelete: ImageView = view.findViewById(R.id.ivDelete)

    /**
     * Renderiza una tarea específica en la vista del ViewHolder.
     * Configura el texto, estado de selección y listeners para marcar o eliminar la tarea.
     *
     * @param task La tarea a renderizar.
     * @param onTaskSelected Callback para notificar cuando se marca/desmarca la tarea.
     * @param onTaskDeleted Callback para notificar cuando se elimina la tarea.
     */
    fun render(task: Task, onTaskSelected: (Int) -> Unit, onTaskDeleted: (Int) -> Unit) {
        // Aplica tachado al texto si la tarea está seleccionada (completada)
        if (task.isSelected) {
            tvTask.paintFlags = tvTask.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            ivDelete.visibility = View.VISIBLE // Muestra el ícono de eliminar
        } else {
            tvTask.paintFlags = tvTask.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            ivDelete.visibility = View.GONE // Oculta el ícono de eliminar
        }

        cbTask.isChecked = task.isSelected // Sincroniza el checkbox con el estado de la tarea
        tvTask.text = task.name // Muestra el nombre de la tarea

        // Asigna un color al checkbox según la categoría de la tarea
        val color = when (task.category) {
            TaskCategory.Trabajo -> R.color.lista_trabajo_category
            TaskCategory.Otros -> R.color.lista_otros_category
            TaskCategory.Personal -> R.color.lista_personal_category
        }
        cbTask.buttonTintList = ColorStateList.valueOf(
            ContextCompat.getColor(cbTask.context, color)
        )

        // Listener para marcar/desmarcar la tarea
        cbTask.setOnClickListener {
            onTaskSelected(adapterPosition) // Notifica la posición de la tarea seleccionada
        }

        // Listener para eliminar la tarea
        ivDelete.setOnClickListener {
            onTaskDeleted(adapterPosition) // Notifica la posición de la tarea eliminada
        }
    }
}