package com.ventura.imcytodolist

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ventura.imcytodolist.databinding.ActivityMenuBinding
import com.ventura.imcytodolist.imc.IMCCalculator
import com.ventura.imcytodolist.todolist.ToDoList

/**
 * Actividad principal que sirve como menú de la aplicación.
 * Proporciona opciones para navegar a la calculadora de IMC, la lista de tareas,
 * abrir otra aplicación externa o salir de la aplicación.
 */
class MenuActivity : AppCompatActivity() {

    // Binding para acceder a las vistas definidas en el layout
    private lateinit var binding: ActivityMenuBinding

    /**
     * Método del ciclo de vida que se ejecuta al crear la actividad.
     * Configura el binding, habilita el diseño edge-to-edge y establece los listeners para las opciones del menú.
     *
     * @param savedInstanceState Estado anterior de la actividad, si existe.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Habilita el diseño sin bordes para usar toda la pantalla
        binding = ActivityMenuBinding.inflate(layoutInflater) // Infla el layout del menú
        setContentView(binding.root) // Establece la vista raíz

        // Ajusta el padding para respetar las barras del sistema (status bar, navigation bar)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Listener para navegar a la calculadora de IMC
        binding.cardImcCalculator.setOnClickListener {
            val intent = Intent(this, IMCCalculator::class.java) // Crea un Intent para IMCCalculator
            startActivity(intent) // Inicia la actividad de la calculadora de IMC
        }

        // Listener para navegar a la lista de tareas
        binding.cardToDoList.setOnClickListener {
            val intent = Intent(this, ToDoList::class.java) // Crea un Intent para ToDoList
            startActivity(intent) // Inicia la actividad de la lista de tareas
        }

        // Listener para abrir otra aplicación externa
        binding.buttonOpenOtherApp.setOnClickListener {
            // Intenta obtener el Intent de lanzamiento para el paquete de otra aplicación
            val intent = packageManager.getLaunchIntentForPackage("com.otra.app")
            if (intent != null) {
                startActivity(intent) // Si la aplicación existe, la abre
            } else {
                // Maneja el caso en que la aplicación no esté instalada
                // TODO: Podrías agregar un Toast o Snackbar aquí para informar al usuario
                // Ejemplo: Toast.makeText(this, "Aplicación no encontrada", Toast.LENGTH_SHORT).show()
            }
        }

        // Listener para cerrar la aplicación
        binding.buttonExit.setOnClickListener {
            finishAffinity() // Cierra todas las actividades en la pila y termina la aplicación
        }
    }
}