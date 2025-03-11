package com.ventura.imcytodolist.imc

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ventura.imcytodolist.R
import com.ventura.imcytodolist.databinding.ActivityImccalculatorBinding
import java.text.DecimalFormat

/**
 * Actividad que permite al usuario calcular su Índice de Masa Corporal (IMC) ingresando
 * altura, peso, edad y género. Proporciona una interfaz interactiva con controles deslizantes
 * y botones para ajustar los valores y calcular el resultado.
 */
class IMCCalculator : AppCompatActivity() {
    // Binding para acceder a las vistas definidas en el layout
    private lateinit var binding: ActivityImccalculatorBinding

    // Variables para rastrear el estado de selección de género
    private var isMaleSelected: Boolean = true
    private var isFemaleSelected: Boolean = false

    // Variables para almacenar los valores actuales de altura, peso y edad
    private var currentHeight: Int = 120 // Altura inicial en cm
    private var currentWeight: Int = 70  // Peso inicial en kg
    private var currentAge: Int = 30     // Edad inicial en años

    /**
     * Método del ciclo de vida que se ejecuta al crear la actividad.
     * Configura el binding, habilita el diseño edge-to-edge y establece listeners y UI inicial.
     *
     * @param savedInstanceState Estado anterior de la actividad, si existe.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Habilita el diseño sin bordes para usar toda la pantalla
        binding = ActivityImccalculatorBinding.inflate(layoutInflater) // Infla el layout
        setContentView(binding.root) // Establece la vista raíz

        // Ajusta el padding para respetar las barras del sistema (status bar, navigation bar)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupListeners() // Configura los listeners de los elementos interactivos
        setupUI()        // Inicializa la interfaz de usuario con valores predeterminados
    }

    /**
     * Configura los listeners para los elementos interactivos como botones y sliders.
     * Incluye animaciones para mejorar la experiencia del usuario.
     */
    private fun setupListeners() {
        val scaleAnimation =
            AnimationUtils.loadAnimation(this, R.anim.scale_anim) // Carga animación de escala

        // Listener para el botón de selección de género masculino
        binding.viewMale.setOnClickListener {
            it.startAnimation(scaleAnimation) // Aplica animación al hacer clic
            toggleGenderSelection()           // Cambia la selección de género
            updateGenderColors()              // Actualiza los colores según la selección
        }

        // Listener para el botón de selección de género femenino
        binding.viewFemale.setOnClickListener {
            it.startAnimation(scaleAnimation)
            toggleGenderSelection()
            updateGenderColors()
        }

        // Listener para el slider de altura
        binding.rsHeight.addOnChangeListener { _, value, _ ->
            currentHeight = value.toInt() // Actualiza altura
            binding.tvHeight.text = "$currentHeight cm" // Muestra la altura en la UI
        }

        // Incrementa el peso al hacer clic en el botón "+"
        binding.btnPlusWeight.setOnClickListener {
            it.startAnimation(scaleAnimation)
            currentWeight += 1 // Aumenta el peso en 1 kg
            updateWeightDisplay() // Actualiza la UI
        }

        // Decrementa el peso al hacer clic en el botón "-"
        binding.btnSubtractWeight.setOnClickListener {
            it.startAnimation(scaleAnimation)
            if (currentWeight > 1) { // Evita que el peso sea menor a 1 kg
                currentWeight -= 1
                updateWeightDisplay()
            }
        }

        // Incrementa la edad al hacer clic en el botón "+"
        binding.btnPlusAge.setOnClickListener {
            it.startAnimation(scaleAnimation)
            currentAge += 1 // Aumenta la edad en 1 año
            updateAgeDisplay() // Actualiza la UI
        }

        // Decrementa la edad al hacer clic en el botón "-"
        binding.btnSubtractAge.setOnClickListener {
            it.startAnimation(scaleAnimation)
            if (currentAge > 1) { // Evita que la edad sea menor a 1 año
                currentAge -= 1
                updateAgeDisplay()
            }
        }

        // Calcula el IMC y navega a la pantalla de resultados al hacer clic en "Calcular"
        binding.btnCalculate.setOnClickListener {
            it.startAnimation(scaleAnimation)
            val result = calculateIMC() // Calcula el IMC
            navigateToResultScreen(result) // Navega a la pantalla de resultados
        }
    }

    /**
     * Calcula el Índice de Masa Corporal (IMC) usando la fórmula: peso / (altura^2).
     *
     * @return El valor del IMC.
     */
    private fun calculateIMC(): Double {
        val heightInMeters = currentHeight.toDouble() / 100
        val imc = currentWeight / (heightInMeters * heightInMeters)
        return imc
    }

    /**
     * Actualiza el texto que muestra el peso actual en la UI.
     */
    private fun updateWeightDisplay() {
        binding.tvWeight.text = currentWeight.toString() // Muestra el peso actual
    }

    /**
     * Actualiza el texto que muestra la edad actual en la UI.
     */
    private fun updateAgeDisplay() {
        binding.tvAge.text = currentAge.toString() // Muestra la edad actual
    }

    /**
     * Alterna la selección entre masculino y femenino.
     */
    private fun toggleGenderSelection() {
        isMaleSelected = !isMaleSelected // Invierte la selección de masculino
        isFemaleSelected = !isFemaleSelected // Invierte la selección de femenino
    }

    /**
     * Obtiene el color de fondo para las tarjetas de género según su estado de selección.
     *
     * @param isSelected Indica si el género está seleccionado.
     * @return El ID del recurso de color correspondiente.
     */
    private fun getBackgroundColor(isSelected: Boolean): Int {
        val colorReference = if (isSelected) {
            R.color.primary // Color primario si está seleccionado
        } else {
            R.color.card_background // Color de fondo por defecto si no está seleccionado
        }
        return ContextCompat.getColor(this, colorReference) // Devuelve el color resuelto
    }

    /**
     * Obtiene el color del texto e íconos para las tarjetas de género según su estado de selección.
     *
     * @param isSelected Indica si el género está seleccionado.
     * @return El ID del recurso de color correspondiente.
     */
    private fun getTextColor(isSelected: Boolean): Int {
        val colorReference = if (isSelected) {
            R.color.white // Blanco si está seleccionado
        } else {
            R.color.black // Negro si no está seleccionado
        }
        return ContextCompat.getColor(this, colorReference) // Devuelve el color resuelto
    }

    /**
     * Actualiza los colores de las tarjetas de género en la UI según su estado de selección.
     */
    private fun updateGenderColors() {
        binding.viewMale.setCardBackgroundColor(getBackgroundColor(isMaleSelected))
        binding.viewFemale.setCardBackgroundColor(getBackgroundColor(isFemaleSelected))
        binding.maleText.setTextColor(getTextColor(isMaleSelected))
        binding.maleIcon.setColorFilter(getTextColor(isMaleSelected))
        binding.femaleText.setTextColor(getTextColor(isFemaleSelected))
        binding.femaleIcon.setColorFilter(getTextColor(isFemaleSelected))
    }

    /**
     * Configura la interfaz de usuario inicial con valores predeterminados.
     */
    private fun setupUI() {
        updateGenderColors() // Aplica colores iniciales a las tarjetas de género
        updateWeightDisplay() // Muestra el peso inicial
        updateAgeDisplay() // Muestra la edad inicial

        // Configura el slider de altura con un valor inicial en el medio
        val initialHeight = (binding.rsHeight.valueFrom + binding.rsHeight.valueTo) / 2
        binding.rsHeight.values = listOf(initialHeight)
        currentHeight = initialHeight.toInt()
        binding.tvHeight.text = "$currentHeight cm" // Muestra la altura inicial
    }

    /**
     * Navega a la pantalla de resultados pasando el valor del IMC calculado.
     *
     * @param result El valor del IMC a mostrar en la siguiente pantalla.
     */
    private fun navigateToResultScreen(result: Double) {
        val intent =
            Intent(this, ResultIMC::class.java) // Crea un Intent para la pantalla de resultados
        intent.putExtra("IMC_KEY", result) // Pasa el IMC como extra
        startActivity(intent) // Inicia la nueva actividad
    }
}