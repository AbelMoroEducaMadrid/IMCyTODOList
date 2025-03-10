package com.ventura.imcytodolist.todolist

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ventura.imcytodolist.R

class CategoriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val tvCategoryName: TextView = view.findViewById(R.id.tvCategoryName)
    private val divider: View = view.findViewById(R.id.divider)

    fun render(taskCategory: TaskCategory) {
        when (taskCategory) {
            TaskCategory.Trabajo -> {
                tvCategoryName.text = "Trabajo"
                divider.setBackgroundColor(
                    ContextCompat.getColor(divider.context, R.color.lista_trabajo_category)
                )
            }
            TaskCategory.Otros -> {
                tvCategoryName.text = "Otros"
                divider.setBackgroundColor(
                    ContextCompat.getColor(divider.context, R.color.lista_otros_category)
                )
            }
            TaskCategory.Personal -> {
                tvCategoryName.text = "Personal"
                divider.setBackgroundColor(
                    ContextCompat.getColor(divider.context, R.color.lista_personal_category)
                )
            }
            TaskCategory.Estudio -> {
                tvCategoryName.text = "Estudio"
                divider.setBackgroundColor(
                    ContextCompat.getColor(divider.context, R.color.lista_estudio_category)
                )
            }
            TaskCategory.Hogar -> {
                tvCategoryName.text = "Hogar"
                divider.setBackgroundColor(
                    ContextCompat.getColor(divider.context, R.color.lista_hogar_category)
                )
            }
            TaskCategory.Salud -> {
                tvCategoryName.text = "Salud"
                divider.setBackgroundColor(
                    ContextCompat.getColor(divider.context, R.color.lista_salud_category)
                )
            }
        }
    }
}
