package com.ventura.imcytodolist

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.random.Random

/**
 * Vista personalizada que dibuja un fondo animado con puntos en movimiento.
 * Los puntos son círculos de tamaño aleatorio, en negro o blanco con opacidad media,
 * que se desplazan lentamente por la pantalla y rebotan al tocar los bordes.
 *
 * @param context Contexto de la aplicación o actividad donde se usa la vista.
 * @param attrs Conjunto de atributos XML opcionales para personalizar la vista.
 */
class AnimatedDotsBackground(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    // Constantes que definen el comportamiento de la animación
    companion object {
        private const val DOT_COUNT = 50  // Número total de puntos en la animación
        private const val MAX_RADIUS = 15 // Tamaño máximo del radio de un punto (en píxeles)
        private const val MIN_RADIUS = 5  // Tamaño mínimo del radio de un punto (en píxeles)
        private const val SPEED = 2       // Velocidad máxima de desplazamiento de los puntos
    }

    // Lista mutable que almacena los puntos animados
    private val dots = mutableListOf<Dot>()

    // Objeto Paint usado para dibujar los puntos en el lienzo
    private val paint = Paint().apply {
        isAntiAlias = true // Habilita el suavizado para bordes más limpios
    }

    // Bandera para evitar reinicializar los puntos cada vez que cambia el tamaño de la vista
    private var initialized = false

    /**
     * Dibuja los puntos en el lienzo y actualiza su posición en cada fotograma.
     * Este método es llamado automáticamente por el sistema para redibujar la vista.
     *
     * @param canvas El lienzo en el que se dibujan los puntos.
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Itera sobre cada punto en la lista
        dots.forEach { dot ->
            dot.update(width, height)  // Actualiza la posición del punto según su velocidad y límites
            paint.color = dot.color    // Configura el color del punto en el objeto Paint
            canvas.drawCircle(dot.x, dot.y, dot.radius, paint) // Dibuja el círculo en el lienzo
        }

        // Solicita que la vista se redibuje en el siguiente fotograma de animación
        postInvalidateOnAnimation() // Esto asegura una animación fluida
    }

    /**
     * Se ejecuta cuando cambia el tamaño de la vista.
     * Inicializa los puntos solo la primera vez que se define el tamaño.
     *
     * @param w Nuevo ancho de la vista en píxeles.
     * @param h Nuevo alto de la vista en píxeles.
     * @param oldw Ancho anterior de la vista en píxeles.
     * @param oldh Alto anterior de la vista en píxeles.
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        // Solo inicializa los puntos si no se ha hecho antes
        if (!initialized) {
            dots.clear() // Limpia la lista para evitar duplicados
            repeat(DOT_COUNT) {
                dots.add(Dot(w, h)) // Crea y agrega un nuevo punto con posición y tamaño aleatorios
            }
            initialized = true // Marca la vista como inicializada
        }
    }

    /**
     * Clase interna que representa un punto animado en la pantalla.
     * Cada punto tiene posición, tamaño, velocidad y color propios.
     *
     * @param width Ancho de la vista para definir límites iniciales.
     * @param height Alto de la vista para definir límites iniciales.
     */
    private inner class Dot(width: Int, height: Int) {
        // Posición inicial aleatoria dentro de los límites de la vista
        var x = Random.nextInt(width).toFloat()
        var y = Random.nextInt(height).toFloat()

        // Radio aleatorio entre MIN_RADIUS y MAX_RADIUS
        var radius = Random.nextInt(MIN_RADIUS, MAX_RADIUS).toFloat()

        // Velocidad aleatoria en X e Y, ajustada para ser positiva o negativa
        var dx = (Random.nextFloat() - 0.5f) * SPEED // Rango: -SPEED/2 a SPEED/2
        var dy = (Random.nextFloat() - 0.5f) * SPEED // Rango: -SPEED/2 a SPEED/2

        // Color base aleatorio (negro o blanco) con opacidad media
        val colorBase = if (Random.nextBoolean()) Color.BLACK else Color.WHITE
        val alpha = Random.nextInt(100, 155) // Opacidad entre 100 y 155 (~50% de 255)
        val color = Color.argb(alpha, Color.red(colorBase), Color.green(colorBase), Color.blue(colorBase))

        /**
         * Actualiza la posición del punto y maneja el rebote en los bordes de la vista.
         *
         * @param width Ancho actual de la vista.
         * @param height Alto actual de la vista.
         */
        fun update(width: Int, height: Int) {
            x += dx // Mueve el punto en el eje X según su velocidad
            y += dy // Mueve el punto en el eje Y según su velocidad

            // Invierte la dirección si el punto toca los bordes horizontales
            if (x < 0 || x > width) dx = -dx
            // Invierte la dirección si el punto toca los bordes verticales
            if (y < 0 || y > height) dy = -dy
        }
    }
}