package com.example.ikr_application.akiko23.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class DrawingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {

    private val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 12f
    }

    private val erasePaint = Paint(strokePaint).apply {
        color = Color.WHITE
        strokeWidth = 32f
    }

    private val paths = mutableListOf<Pair<Path, Paint>>()
    private var activePath: Path? = null

    var isEraserMode: Boolean = false
        set(value) {
            field = value
        }

    init {
        setBackgroundColor(Color.WHITE)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paths.forEach { (path, paint) ->
            canvas.drawPath(path, paint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startPath(x, y)
                invalidate()
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                activePath?.lineTo(x, y)
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                activePath?.lineTo(x, y)
                performClick()
            }
        }

        invalidate()
        return true
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }

    fun clearCanvas() {
        paths.clear()
        activePath = null
        invalidate()
    }

    private fun startPath(x: Float, y: Float) {
        val path = Path().apply { moveTo(x, y) }
        val paintCopy = Paint(if (isEraserMode) erasePaint else strokePaint)

        activePath = path
        paths.add(path to paintCopy)
    }
}

