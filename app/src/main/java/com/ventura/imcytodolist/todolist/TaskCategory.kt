package com.ventura.imcytodolist.todolist

sealed class TaskCategory(var isSelected: Boolean = true) {
    object Personal : TaskCategory()
    object Trabajo : TaskCategory()
    object Otros : TaskCategory()
    object Estudio : TaskCategory()
    object Hogar : TaskCategory()
    object Salud : TaskCategory()
}