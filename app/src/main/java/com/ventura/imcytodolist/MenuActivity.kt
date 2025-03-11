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

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.cardImcCalculator.setOnClickListener {
            val intent = Intent(this, IMCCalculator::class.java)
            startActivity(intent)
        }

        binding.cardToDoList.setOnClickListener {
            val intent = Intent(this, ToDoList::class.java)
            startActivity(intent)
        }

        binding.buttonOpenOtherApp.setOnClickListener {
            // Cambia "com.otra.app" por el paquete de la otra aplicación
            val intent = packageManager.getLaunchIntentForPackage("com.otra.app")
            if (intent != null) {
                startActivity(intent)
            } else {
                // Manejar el caso en que la aplicación no esté instalada
                // Puedes mostrar un Toast o un Snackbar
            }
        }

        binding.buttonExit.setOnClickListener {
            finishAffinity() // Cierra la aplicación y toda la pila de actividades
        }
    }
}