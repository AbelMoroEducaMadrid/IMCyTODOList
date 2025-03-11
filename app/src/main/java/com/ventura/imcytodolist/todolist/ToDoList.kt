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

/**
 * Actividad principal para la lista de tareas.
 * Permite ver, filtrar, agregar, marcar y eliminar tareas organizadas por categorías.
 */
class ToDoList : AppCompatActivity() {
    // Componentes de la UI
    private lateinit var rvCategories: RecyclerView
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var rvTasks: RecyclerView
    private lateinit var tasksAdapter: TasksAdapter
    private lateinit var fabAddTask: FloatingActionButton

    // Lista fija de categorías disponibles
    private val categories = listOf(
        TaskCategory.Personal,
        TaskCategory.Trabajo,
        TaskCategory.Otros
    )

    // Lista mutable de tareas iniciales
    private val tasks = mutableListOf(
        Task("Revisar correos", TaskCategory.Trabajo),
        Task("Llamar a un amigo", TaskCategory.Personal),
        Task("Hacer ejercicio", TaskCategory.Personal),
        Task("Leer un libro", TaskCategory.Personal),
        Task("Meditar 10 minutos", TaskCategory.Personal),
        Task("Salir a caminar", TaskCategory.Personal),
        Task("Aprender una nueva receta", TaskCategory.Personal),
        Task("Redactar informe mensual", TaskCategory.Trabajo),
        Task("Preparar presentación para la reunión", TaskCategory.Trabajo),
        Task("Actualizar la base de datos", TaskCategory.Trabajo),
        Task("Revisar código en pull requests", TaskCategory.Trabajo),
        Task("Responder mensajes pendientes", TaskCategory.Trabajo),
        Task("Comprar un regalo", TaskCategory.Otros),
        Task("Investigar sobre un nuevo hobby", TaskCategory.Otros),
        Task("Organizar archivos", TaskCategory.Otros),
        Task("Limpiar el escritorio", TaskCategory.Otros),
        Task("Ver un documental interesante", TaskCategory.Otros)
    )

    /**
     * Método del ciclo de vida que se ejecuta al crear la actividad.
     * Inicializa componentes, UI y listeners.
     *
     * @param savedInstanceState Estado anterior de la actividad, si existe.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_list) // Establece el layout

        initComponent() // Inicializa las referencias a los componentes
        initUI()        // Configura la interfaz de usuario
        initListeners() // Configura los listeners
    }

    /**
     * Inicializa las referencias a los componentes de la UI.
     */
    private fun initComponent() {
        rvCategories = findViewById(R.id.rvCategories)
        rvTasks = findViewById(R.id.rvTasks)
        fabAddTask = findViewById(R.id.fabAddTask)
    }

    /**
     * Configura la interfaz de usuario inicial con los adaptadores y layouts.
     */
    private fun initUI() {
        // Configura el RecyclerView de categorías (horizontal)
        categoriesAdapter = CategoriesAdapter(categories) { position -> updateCategories(position) }
        rvCategories.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvCategories.adapter = categoriesAdapter

        // Configura el RecyclerView de tareas (vertical)
        tasksAdapter = TasksAdapter(tasks, { position -> onTaskSelected(position) }, { position -> onTaskDeleted(position) })
        rvTasks.layoutManager = LinearLayoutManager(this)
        rvTasks.adapter = tasksAdapter
    }

    /**
     * Configura los listeners para los elementos interactivos.
     */
    private fun initListeners() {
        fabAddTask.setOnClickListener { showDialog() } // Muestra el diálogo para agregar tarea
    }

    /**
     * Muestra un diálogo para agregar una nueva tarea.
     * Permite ingresar el nombre y seleccionar una categoría.
     */
    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_task) // Establece el layout del diálogo

        // Referencias a los elementos del diálogo
        val btnAddTask: Button = dialog.findViewById(R.id.btnAddTask)
        val etTask: EditText = dialog.findViewById(R.id.etTask)
        val rgCategories: RadioGroup = dialog.findViewById(R.id.rgCategories)

        btnAddTask.setOnClickListener {
            val currentTask = etTask.text.toString() // Obtiene el nombre de la tarea

            if (currentTask.isNotEmpty()) { // Verifica que el nombre no esté vacío
                val selectedId = rgCategories.checkedRadioButtonId
                if (selectedId != -1) { // Verifica que se haya seleccionado una categoría
                    val selectedRadioButton: RadioButton = rgCategories.findViewById(selectedId)
                    // Asigna la categoría según el texto del RadioButton seleccionado
                    val currentCategory: TaskCategory = when (selectedRadioButton.text) {
                        getString(R.string.lista_dialog_category_trabajo) -> TaskCategory.Trabajo
                        getString(R.string.lista_dialog_category_personal) -> TaskCategory.Personal
                        else -> TaskCategory.Otros
                    }

                    // Agrega la nueva tarea a la lista
                    tasks.add(Task(currentTask, currentCategory))
                    tasksAdapter.notifyItemInserted(tasks.size - 1) // Notifica al adaptador
                    updateTasks() // Actualiza la lista de tareas mostradas
                    dialog.dismiss() // Cierra el diálogo
                }
            }
        }
        dialog.show() // Muestra el diálogo
    }

    /**
     * Actualiza el estado de selección de las categorías y refresca las tareas mostradas.
     *
     * @param position La posición de la categoría seleccionada.
     */
    private fun updateCategories(position: Int) {
        val selectedCategory = categories[position]

        // Si la categoría ya está seleccionada, la deselecciona
        if (selectedCategory.isSelected) {
            selectedCategory.isSelected = false
        } else {
            // Deselecciona todas las categorías y selecciona la actual
            categories.forEach { it.isSelected = false }
            selectedCategory.isSelected = true
        }

        categoriesAdapter.notifyDataSetChanged() // Notifica cambios en las categorías
        updateTasks() // Actualiza las tareas mostradas
    }

    /**
     * Marca o desmarca una tarea como completada.
     *
     * @param position La posición de la tarea en la lista.
     */
    private fun onTaskSelected(position: Int) {
        tasks[position].isSelected = !tasks[position].isSelected // Invierte el estado
        updateTasks() // Actualiza la lista de tareas
    }

    /**
     * Elimina una tarea de la lista.
     *
     * @param position La posición de la tarea a eliminar.
     */
    private fun onTaskDeleted(position: Int) {
        tasks.removeAt(position) // Elimina la tarea
        tasksAdapter.notifyItemRemoved(position) // Notifica al adaptador
        updateTasks() // Actualiza la lista de tareas
    }

    /**
     * Actualiza la lista de tareas mostradas según la categoría seleccionada.
     * Si no hay categoría seleccionada, muestra todas las tareas.
     */
    private fun updateTasks() {
        val selectedCategory = categories.find { it.isSelected } // Busca la categoría seleccionada

        // Filtra las tareas según la categoría o muestra todas
        val newTasks = if (selectedCategory == null) {
            tasks
        } else {
            tasks.filter { it.category == selectedCategory }
        }

        tasksAdapter.tasks = newTasks.toMutableList() // Actualiza las tareas en el adaptador
        tasksAdapter.notifyDataSetChanged() // Notifica cambios al RecyclerView
    }
}