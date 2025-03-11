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
        TaskCategory.Otros,
        TaskCategory.Estudio,
        TaskCategory.Hogar,
        TaskCategory.Salud
    )

    private val tasks = mutableListOf(
        Task("Estudiar para el examen", TaskCategory.Estudio),
        Task("Limpiar la casa", TaskCategory.Hogar),
        Task("Hacer ejercicio", TaskCategory.Salud),
        Task("Revisar correos", TaskCategory.Trabajo),
        Task("Llamar a un amigo", TaskCategory.Personal),
        Task("Comprar víveres", TaskCategory.Hogar)
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

        tasksAdapter = TasksAdapter(tasks) { position -> onItemSelected(position) }
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
                        getString(R.string.lista_dialog_category_estudio) -> TaskCategory.Estudio
                        getString(R.string.lista_dialog_category_hogar) -> TaskCategory.Hogar
                        getString(R.string.lista_dialog_category_salud) -> TaskCategory.Salud
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
        categories[position].isSelected = !categories[position].isSelected
        categoriesAdapter.notifyItemChanged(position)
        updateTasks()
    }

    private fun onItemSelected(position: Int) {
        tasks[position].isSelected = !tasks[position].isSelected
        updateTasks()
    }

    private fun updateTasks() {
        val selectedCategories = categories.filter { it.isSelected }
        val newTasks = if (selectedCategories.isEmpty()) tasks else tasks.filter {
            selectedCategories.contains(it.category)
        }

        tasksAdapter.tasks = newTasks.toMutableList()
        tasksAdapter.notifyDataSetChanged()
    }
}
