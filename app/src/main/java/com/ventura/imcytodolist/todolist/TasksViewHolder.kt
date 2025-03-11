package com.ventura.imcytodolist.todolist

import android.content.res.ColorStateList
import android.graphics.Paint
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ventura.imcytodolist.R

class TasksViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val tvTask: TextView = view.findViewById(R.id.tvTask)
    private val cbTask: CheckBox = view.findViewById(R.id.cbTask)
    private val ivDelete: ImageView = view.findViewById(R.id.ivDelete)

    fun render(task: Task, onTaskSelected: (Int) -> Unit, onTaskDeleted: (Int) -> Unit) {
        if (task.isSelected) {
            tvTask.paintFlags = tvTask.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            ivDelete.visibility = View.VISIBLE // Mostrar la "X" cuando la tarea está completada
        } else {
            tvTask.paintFlags = tvTask.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            ivDelete.visibility = View.GONE // Ocultar la "X" cuando la tarea no está completada
        }

        cbTask.isChecked = task.isSelected
        tvTask.text = task.name

        val color = when (task.category) {
            TaskCategory.Trabajo -> R.color.lista_trabajo_category
            TaskCategory.Otros -> R.color.lista_otros_category
            TaskCategory.Personal -> R.color.lista_personal_category
        }
        cbTask.buttonTintList = ColorStateList.valueOf(
            ContextCompat.getColor(cbTask.context, color)
        )

        // Listener para marcar/desmarcar la tarea como completada
        cbTask.setOnClickListener {
            onTaskSelected(adapterPosition)
        }

        // Listener para eliminar la tarea cuando se hace clic en la "X"
        ivDelete.setOnClickListener {
            onTaskDeleted(adapterPosition)
        }
    }
}