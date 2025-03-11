package com.ventura.imcytodolist.todolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ventura.imcytodolist.R

/**
 * Adaptador para el RecyclerView que muestra las categorías de tareas.
 * Permite seleccionar una categoría y notificar la selección al componente padre.
 *
 * @param categories Lista de categorías a mostrar.
 * @param onItemSelected Callback que se ejecuta al seleccionar una categoría, pasando su posición.
 */
class CategoriesAdapter(
    private val categories: List<TaskCategory>,
    private val onItemSelected: (Int) -> Unit
) : RecyclerView.Adapter<CategoriesViewHolder>() {

    /**
     * Crea un nuevo ViewHolder inflando el layout de un ítem de categoría.
     *
     * @param parent El ViewGroup padre (RecyclerView).
     * @param viewType Tipo de vista (no usado aquí).
     * @return Un nuevo CategoriesViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task_category, parent, false)
        return CategoriesViewHolder(view)
    }

    /**
     * Vincula los datos de una categoría al ViewHolder en la posición especificada.
     * Configura un listener para notificar la selección.
     *
     * @param holder El ViewHolder a vincular.
     * @param position La posición de la categoría en la lista.
     */
    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.render(categories[position]) // Renderiza la categoría
        holder.itemView.setOnClickListener {
            onItemSelected(position) // Notifica la selección al componente padre
        }
    }

    /**
     * Devuelve el número total de categorías en la lista.
     *
     * @return Tamaño de la lista de categorías.
     */
    override fun getItemCount() = categories.size
}