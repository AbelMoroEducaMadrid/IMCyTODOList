package com.ventura.imcytodolist.todolist

sealed class TaskCategory(var isSelected: Boolean = false) {
    object Personal : TaskCategory()
    object Trabajo : TaskCategory()
    object Otros : TaskCategory()
}