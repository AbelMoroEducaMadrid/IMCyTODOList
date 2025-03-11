package com.ventura.imcytodolist.todolist

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ventura.imcytodolist.R

class ToDoList : AppCompatActivity() {
    private lateinit var rvCategories: RecyclerView
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var rvTasks: RecyclerView
    private lateinit var tasksAdapter: TasksAdapter
    private lateinit var fabAddTask: FloatingActionButton

    private val categories = listOf(
        TaskCategory.Personal,
        TaskCategory.Trabajo,
        TaskCategory.Otros
    )

    private val tasks = mutableListOf(
        // Tareas personales
        Task("Revisar correos", TaskCategory.Trabajo),
        Task("Llamar a un amigo", TaskCategory.Personal),
        Task("Hacer ejercicio", TaskCategory.Personal),
        Task("Leer un libro", TaskCategory.Personal),
        Task("Meditar 10 minutos", TaskCategory.Personal),
        Task("Salir a caminar", TaskCategory.Personal),
        Task("Aprender una nueva receta", TaskCategory.Personal),

        // Tareas de trabajo
        Task("Redactar informe mensual", TaskCategory.Trabajo),
        Task("Preparar presentación para la reunión", TaskCategory.Trabajo),
        Task("Actualizar la base de datos", TaskCategory.Trabajo),
        Task("Revisar código en pull requests", TaskCategory.Trabajo),
        Task("Responder mensajes pendientes", TaskCategory.Trabajo),

        // Otras tareas
        Task("Comprar un regalo", TaskCategory.Otros),
        Task("Investigar sobre un nuevo hobby", TaskCategory.Otros),
        Task("Organizar archivos", TaskCategory.Otros),
        Task("Limpiar el escritorio", TaskCategory.Otros),
        Task("Ver un documental interesante", TaskCategory.Otros)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_list)

        initComponent()
        initUI()
        initListeners()
    }

    private fun initComponent() {
        rvCategories = findViewById(R.id.rvCategories)
        rvTasks = findViewById(R.id.rvTasks)
        fabAddTask = findViewById(R.id.fabAddTask)
    }

    private fun initUI() {
        categoriesAdapter = CategoriesAdapter(categories) { position -> updateCategories(position) }
        rvCategories.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvCategories.adapter = categoriesAdapter

        tasksAdapter = TasksAdapter(tasks, { position -> onTaskSelected(position) }, { position -> onTaskDeleted(position) })
        rvTasks.layoutManager = LinearLayoutManager(this)
        rvTasks.adapter = tasksAdapter
    }

    private fun initListeners() {
        fabAddTask.setOnClickListener { showDialog() }
    }

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_task)

        val btnAddTask: Button = dialog.findViewById(R.id.btnAddTask)
        val etTask: EditText = dialog.findViewById(R.id.etTask)
        val rgCategories: RadioGroup = dialog.findViewById(R.id.rgCategories)

        btnAddTask.setOnClickListener {
            val currentTask = etTask.text.toString()

            if (currentTask.isNotEmpty()) {
                val selectedId = rgCategories.checkedRadioButtonId
                if (selectedId != -1) {
                    val selectedRadioButton: RadioButton = rgCategories.findViewById(selectedId)
                    val currentCategory: TaskCategory = when (selectedRadioButton.text) {
                        getString(R.string.lista_dialog_category_trabajo) -> TaskCategory.Trabajo
                        getString(R.string.lista_dialog_category_personal) -> TaskCategory.Personal
                        else -> TaskCategory.Otros
                    }

                    tasks.add(Task(currentTask, currentCategory))
                    tasksAdapter.notifyItemInserted(tasks.size - 1)
                    updateTasks()
                    dialog.dismiss()
                }
            }
        }
        dialog.show()
    }

    private fun updateCategories(position: Int) {
        val selectedCategory = categories[position]

        // Si la categoría ya está seleccionada, la deseleccionamos
        if (selectedCategory.isSelected) {
            selectedCategory.isSelected = false
        } else {
            // Si no está seleccionada, deseleccionamos todas las categorías primero
            categories.forEach { it.isSelected = false }
            // Luego seleccionamos la categoría actual
            selectedCategory.isSelected = true
        }

        // Notificar al adaptador que los datos han cambiado
        categoriesAdapter.notifyDataSetChanged()

        // Actualizar la lista de tareas según la categoría seleccionada (o ninguna)
        updateTasks()
    }

    private fun onTaskSelected(position: Int) {
        tasks[position].isSelected = !tasks[position].isSelected
        updateTasks()
    }

    private fun onTaskDeleted(position: Int) {
        tasks.removeAt(position)
        tasksAdapter.notifyItemRemoved(position)
        updateTasks()
    }

    private fun updateTasks() {
        val selectedCategory = categories.find { it.isSelected } // Busca la categoría seleccionada

        val newTasks = if (selectedCategory == null) {
            tasks // Si no hay categoría seleccionada, mostrar todas las tareas
        } else {
            tasks.filter { it.category == selectedCategory } // Filtrar por la categoría seleccionada
        }

        tasksAdapter.tasks = newTasks.toMutableList()
        tasksAdapter.notifyDataSetChanged()
    }
}