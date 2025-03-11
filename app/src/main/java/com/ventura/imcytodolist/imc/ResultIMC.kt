package com.ventura.imcytodolist.imc

import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ventura.imcytodolist.R
import com.ventura.imcytodolist.databinding.ActivityResultImcBinding

class ResultIMC : AppCompatActivity() {

    private lateinit var binding: ActivityResultImcBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResultImcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val result = intent.extras?.getDouble("IMC_KEY") ?: 0.0

        setupUI(result)
        setupListeners()

        val strongAnimation = AnimationUtils.loadAnimation(this, R.anim.strong_animation)
        binding.ivResultImage.startAnimation(strongAnimation)
    }

    private fun setupUI(result: Double) {
        binding.tvIMC.text = result.toString()

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

    private fun setupListeners() {
        binding.btnRecalculate.setOnClickListener {
            val scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_anim)
            it.startAnimation(scaleAnimation)
            onBackPressed()
        }
    }
}