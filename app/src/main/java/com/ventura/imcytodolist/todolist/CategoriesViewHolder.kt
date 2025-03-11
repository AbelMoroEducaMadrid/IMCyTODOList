package com.ventura.imcytodolist.todolist

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ventura.imcytodolist.R

class CategoriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val ivCategoryIcon : ImageView = view.findViewById(R.id.ivCategoryIcon)
    private val tvCategoryName: TextView = view.findViewById(R.id.tvCategoryName)
    private val divider: View = view.findViewById(R.id.divider)
    private val viewContainer: CardView = view.findViewById(R.id.viewContainer)

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
        }

        val color: Int
        val textColor: Int


        if (taskCategory.isSelected) {
            color = R.color.primary
            textColor = R.color.white
        } else {
            color = R.color.card_background
            textColor = R.color.black
        }

        viewContainer.setCardBackgroundColor(ContextCompat.getColor(viewContainer.context, color))
        tvCategoryName.setTextColor(ContextCompat.getColor(tvCategoryName.context, textColor))
        ivCategoryIcon.setColorFilter(ContextCompat.getColor(ivCategoryIcon.context, textColor))
    }
}
