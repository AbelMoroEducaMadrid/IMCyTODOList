package com.ventura.imcytodolist.todolist

/**
 * Clase sellada que representa las categorías disponibles para las tareas en la lista de tareas.
 * Una clase sellada permite definir un conjunto restringido de subclases, lo que es útil para modelar
 * un conjunto finito de opciones. Cada categoría puede estar seleccionada o no.
 *
 * @property isSelected Indica si la categoría está actualmente seleccionada. Por defecto es `false`.
 */
sealed class TaskCategory(var isSelected: Boolean = false) {
    /**
     * Categoría que agrupa tareas relacionadas con la vida personal del usuario.
     */
    object Personal : TaskCategory()

    /**
     * Categoría que agrupa tareas relacionadas con el trabajo o actividades profesionales.
     */
    object Trabajo : TaskCategory()

    /**
     * Categoría que agrupa tareas misceláneas que no encajan en Personal o Trabajo.
     */
    object Otros : TaskCategory()
}