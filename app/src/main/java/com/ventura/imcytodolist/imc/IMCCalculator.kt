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

class IMCCalculator : AppCompatActivity() {
    private lateinit var binding: ActivityImccalculatorBinding

    private var isMaleSelected: Boolean = true
    private var isFemaleSelected: Boolean = false

    private var currentHeight: Int = 120
    private var currentWeight: Int = 70
    private var currentAge: Int = 30

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityImccalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupListeners()
        setupUI()
    }

    private fun setupListeners() {
        val scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_anim)

        binding.viewMale.setOnClickListener {
            it.startAnimation(scaleAnimation)
            toggleGenderSelection()
            updateGenderColors()
        }

        binding.viewFemale.setOnClickListener {
            it.startAnimation(scaleAnimation)
            toggleGenderSelection()
            updateGenderColors()
        }

        binding.rsHeight.addOnChangeListener { _, value, _ ->
            val decimalFormat = DecimalFormat("#.##")
            currentHeight = decimalFormat.format(value).toInt()
            binding.tvHeight.text = "$currentHeight cm"
        }

        binding.btnPlusWeight.setOnClickListener {
            it.startAnimation(scaleAnimation)
            currentWeight += 1
            updateWeightDisplay()
        }

        binding.btnSubtractWeight.setOnClickListener {
            it.startAnimation(scaleAnimation)
            if (currentWeight > 1) {
                currentWeight -= 1
                updateWeightDisplay()
            }
        }

        binding.btnPlusAge.setOnClickListener {
            it.startAnimation(scaleAnimation)
            currentAge += 1
            updateAgeDisplay()
        }

        binding.btnSubtractAge.setOnClickListener {
            it.startAnimation(scaleAnimation)
            if (currentAge > 1) {
                currentAge -= 1
                updateAgeDisplay()
            }
        }

        binding.btnCalculate.setOnClickListener {
            it.startAnimation(scaleAnimation)
            val result = calculateIMC()
            navigateToResultScreen(result)
        }
    }

    private fun calculateIMC(): Double {
        val decimalFormat = DecimalFormat("#.##")
        val heightInMeters = currentHeight.toDouble() / 100
        val imc = currentWeight / (heightInMeters * heightInMeters)
        return decimalFormat.format(imc).toDouble()
    }

    private fun updateWeightDisplay() {
        binding.tvWeight.text = currentWeight.toString()
    }

    private fun updateAgeDisplay() {
        binding.tvAge.text = currentAge.toString()
    }

    private fun toggleGenderSelection() {
        isMaleSelected = !isMaleSelected
        isFemaleSelected = !isFemaleSelected
    }

    private fun getBackgroundColor(isSelected: Boolean): Int {
        val colorReference = if (isSelected) {
            R.color.primary
        } else {
            R.color.card_background
        }
        return ContextCompat.getColor(this, colorReference)
    }

    private fun getTextColor(isSelected: Boolean): Int {
        val colorReference = if (isSelected) {
            R.color.white
        } else {
            R.color.black
        }
        return ContextCompat.getColor(this, colorReference)
    }

    private fun updateGenderColors() {
        binding.viewMale.setCardBackgroundColor(getBackgroundColor(isMaleSelected))
        binding.viewFemale.setCardBackgroundColor(getBackgroundColor(isFemaleSelected))
        binding.maleText.setTextColor(getTextColor(isMaleSelected))
        binding.maleIcon.setColorFilter(getTextColor(isMaleSelected))
        binding.femaleText.setTextColor(getTextColor(isFemaleSelected))
        binding.femaleIcon.setColorFilter(getTextColor(isFemaleSelected))
    }

    private fun setupUI() {
        updateGenderColors()
        updateWeightDisplay()
        updateAgeDisplay()

        val initialHeight = (binding.rsHeight.valueFrom + binding.rsHeight.valueTo) / 2
        binding.rsHeight.values = listOf(initialHeight)
        currentHeight = initialHeight.toInt()
        binding.tvHeight.text = "$currentHeight cm"
    }

    private fun navigateToResultScreen(result: Double) {
        val intent = Intent(this, ResultIMC::class.java)
        intent.putExtra("IMC_KEY", result)
        startActivity(intent)
    }
}