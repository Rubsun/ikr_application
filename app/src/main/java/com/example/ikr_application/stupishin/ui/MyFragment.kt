package com.example.ikr_application.stupishin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.ikr_application.R
import com.example.ikr_application.stupishin.domain.TimePrecisions
import com.google.android.material.button.MaterialButton
import android.view.ContextThemeWrapper

class MyFragment : Fragment() {
    private val viewModel by viewModels<MyViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.content_stupishin_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.stu_text).apply {
            val date = viewModel.date()
            text = getString(R.string.stu_text_time_pattern, date)
        }

        val elapsed = view.findViewById<TextView>(R.id.stu_elapsed)
        view.findViewById<ViewGroup>(R.id.stu_buttons).apply {
            val themed = ContextThemeWrapper(requireContext(), R.style.Widget_Stupishin_Button)
            viewModel.timePrecisions().forEach { item ->
                val b = MaterialButton(themed).apply {
                    text = item.typeName
                    setOnClickListener { applyPrecision(elapsed, item) }
                }
                addView(b)
            }
        }

        applyPrecision(elapsed, TimePrecisions.S)
    }

    private fun applyPrecision(elapsed: TextView, item: TimePrecisions) {
        val time = viewModel.elapsedTime(item)
        elapsed.text = getString(R.string.stu_text_time_from_reboot_pattern, time)
    }
}
