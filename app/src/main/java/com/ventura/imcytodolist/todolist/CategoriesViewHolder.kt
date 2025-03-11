package com.ventura.imcytodolist.todolist

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ventura.imcytodolist.R

/**
 * ViewHolder para mostrar las categorías de tareas en un RecyclerView.
 * Maneja la representación visual de cada categoría, incluyendo nombre, ícono y colores.
 *
 * @param view La vista inflada para cada ítem del RecyclerView.
 */
class CategoriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    // Referencias a los elementos de la UI en el layout del ítem
    private val ivCategoryIcon: ImageView = view.findViewById(R.id.ivCategoryIcon)
    private val tvCategoryName: TextView = view.findViewById(R.id.tvCategoryName)
    private val divider: View = view.findViewById(R.id.divider)
    private val viewContainer: CardView = view.findViewById(R.id.viewContainer)

    /**
     * Renderiza una categoría específica en la vista del ViewHolder.
     * Configura el nombre, color del divisor y colores de fondo/texto según el estado de selección.
     *
     * @param taskCategory La categoría de tarea a renderizar.
     */
    fun render(taskCategory: TaskCategory) {
        // Configura el nombre y color del divisor según la categoría
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
        }

        // Determina los colores según si la categoría está seleccionada
        val color: Int
        val textColor: Int
        if (taskCategory.isSelected) {
            color = R.color.primary // Color primario para categoría seleccionada
            textColor = R.color.white // Texto blanco para destacar
        } else {
            color = R.color.card_background // Fondo por defecto
            textColor = R.color.black // Texto negro por defecto
        }

        // Aplica los colores a los elementos de la UI
        viewContainer.setCardBackgroundColor(ContextCompat.getColor(viewContainer.context, color))
        tvCategoryName.setTextColor(ContextCompat.getColor(tvCategoryName.context, textColor))
        ivCategoryIcon.setColorFilter(ContextCompat.getColor(ivCategoryIcon.context, textColor))
    }
}