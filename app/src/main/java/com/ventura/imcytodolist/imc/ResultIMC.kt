package com.ventura.imcytodolist.imc

import android.icu.text.DecimalFormat
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ventura.imcytodolist.R
import com.ventura.imcytodolist.databinding.ActivityResultImcBinding

/**
 * Actividad que muestra el resultado del cálculo del IMC.
 * Clasifica el IMC en categorías (bajo peso, normal, sobrepeso, obesidad) y muestra una descripción e imagen.
 */
class ResultIMC : AppCompatActivity() {
    // Binding para acceder a las vistas del layout
    private lateinit var binding: ActivityResultImcBinding

    /**
     * Método del ciclo de vida que se ejecuta al crear la actividad.
     * Configura el binding, obtiene el IMC del Intent y muestra el resultado.
     *
     * @param savedInstanceState Estado anterior de la actividad, si existe.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Habilita el diseño edge-to-edge
        binding = ActivityResultImcBinding.inflate(layoutInflater) // Infla el layout
        setContentView(binding.root) // Establece la vista raíz

        // Ajusta el padding para respetar las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val result = intent.extras?.getDouble("IMC_KEY") ?: 0.0 // Obtiene el IMC del Intent

        setupUI(result) // Configura la UI según el IMC
        setupListeners() // Configura los listeners

        // Aplica una animación fuerte a la imagen de resultado
        val strongAnimation = AnimationUtils.loadAnimation(this, R.anim.strong_animation)
        binding.ivResultImage.startAnimation(strongAnimation)
    }

    /**
     * Configura la interfaz de usuario según el valor del IMC.
     * Muestra el resultado, categoría, descripción e imagen correspondiente.
     *
     * @param result El valor del IMC a clasificar y mostrar.
     */
    private fun setupUI(result: Double) {
        val decimalFormat = DecimalFormat("#.##")
        binding.tvIMC.text = decimalFormat.format(result)

        // Clasifica el IMC y ajusta la UI según el rango
        when (result) {
            in 0.00..18.50 -> {
                binding.tvResult.text = getString(R.string.title_bajo_peso)
                binding.tvResult.setTextColor(ContextCompat.getColor(this, R.color.peso_bajo))
                binding.tvDescription.text = getString(R.string.description_bajo_peso)
                binding.ivResultImage.setImageResource(R.drawable.delgado)
            }
            in 18.51..24.99 -> {
                binding.tvResult.text = getString(R.string.title_peso_normal)
                binding.tvResult.setTextColor(ContextCompat.getColor(this, R.color.peso_normal))
                binding.tvDescription.text = getString(R.string.description_peso_normal)
                binding.ivResultImage.setImageResource(R.drawable.normal)
            }
            in 25.00..29.99 -> {
                binding.tvResult.text = getString(R.string.title_sobrepeso)
                binding.tvResult.setTextColor(ContextCompat.getColor(this, R.color.peso_sobrepeso))
                binding.tvDescription.text = getString(R.string.description_sobrepeso)
                binding.ivResultImage.setImageResource(R.drawable.sobrepeso)
            }
            in 30.00..99.00 -> {
                binding.tvResult.text = getString(R.string.title_obesidad)
                binding.tvResult.setTextColor(ContextCompat.getColor(this, R.color.obesidad))
                binding.tvDescription.text = getString(R.string.description_obesidad)
                binding.ivResultImage.setImageResource(R.drawable.obesidad)
            }
            else -> {
                binding.tvIMC.text = getString(R.string.error)
                binding.tvResult.text = getString(R.string.error)
                binding.tvResult.setTextColor(ContextCompat.getColor(this, R.color.obesidad))
                binding.tvDescription.text = getString(R.string.error)
                binding.ivResultImage.setImageResource(R.drawable.logoimc)
            }
        }
    }

    /**
     * Configura los listeners para los elementos interactivos.
     * Permite volver a la pantalla anterior al hacer clic en "Recalcular".
     */
    private fun setupListeners() {
        binding.btnRecalculate.setOnClickListener {
            val scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_anim)
            it.startAnimation(scaleAnimation) // Aplica animación al botón
            onBackPressed() // Regresa a la pantalla anterior
        }
    }
}