package com.example.ikr_application.denisova.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import kotlin.math.max

class TemperatureSparklineView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {

    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#1E88E5")
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 6f
    }

    private val path = Path()

    private var values: List<Double> = emptyList()

    fun setTemperaturesC(temperaturesC: List<Double>) {
        values = temperaturesC.filter { it.isFinite() }
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (values.size < 2) return

        val minValue = values.minOrNull() ?: return
        val maxValue = values.maxOrNull() ?: return

        val range = max(maxValue - minValue, 0.0001)

        val contentWidth = (width - paddingLeft - paddingRight).toFloat()
        val contentHeight = (height - paddingTop - paddingBottom).toFloat()

        if (contentWidth <= 0f || contentHeight <= 0f) return

        val xStep = contentWidth / (values.size - 1)

        path.reset()
        values.forEachIndexed { index, v ->
            val x = paddingLeft + xStep * index
            val normalized = ((v - minValue) / range).toFloat()
            val y = paddingTop + contentHeight - normalized * contentHeight

            if (index == 0) path.moveTo(x, y) else path.lineTo(x, y)
        }

        canvas.drawPath(path, linePaint)
    }
}
