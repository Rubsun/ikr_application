package com.akiko23.impl.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.akiko23.impl.R
import com.google.android.material.button.MaterialButtonToggleGroup

/**
 * Фрагмент для экрана рисования akiko23.
 */
internal class Akiko23CanvasFragment : Fragment(R.layout.fragment_akiko23_canvas) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val drawingView = view.findViewById<DrawingView>(R.id.drawing_view)
        val toggle = view.findViewById<MaterialButtonToggleGroup>(R.id.mode_toggle)
        val clearButton = view.findViewById<View>(R.id.clear_button)

        toggle.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (!isChecked) return@addOnButtonCheckedListener

            drawingView.isEraserMode = checkedId == R.id.mode_erase
        }
        toggle.check(R.id.mode_draw)

        clearButton.setOnClickListener { drawingView.clearCanvas() }
    }
}

