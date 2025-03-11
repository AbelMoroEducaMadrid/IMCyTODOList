package com.ventura.imcytodolist.todolist

/**
 * Modelo de datos que representa una tarea en la lista de tareas.
 *
 * @property name Nombre de la tarea.
 * @property category Categoría a la que pertenece la tarea.
 * @property isSelected Indica si la tarea está marcada como completada. Por defecto es `false`.
 */
data class Task(
    val name: String,
    val category: TaskCategory,
    var isSelected: Boolean = false
)