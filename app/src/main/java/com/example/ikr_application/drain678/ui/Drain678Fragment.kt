package com.example.ikr_application.drain678.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.ikr_application.R
import com.example.ikr_application.drain678.domain.Drain678TimePrecisions

class Drain678Fragment : Fragment() {
    private val viewModel by viewModels<Drain678ViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.content_drain678_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.text).apply {
            val date = viewModel.date()
            text = getString(R.string.drain678_text_time_pattern, date)
        }

        val elapsed = view.findViewById<TextView>(R.id.elapsed)
        view.findViewById<ViewGroup>(R.id.buttons).apply {
            viewModel.timePrecisions()
                .map { item ->
                    layoutInflater
                        .inflate(R.layout.item_drain678_precision, this, false)
                        .apply {
                            (this as? TextView)?.text = item.typeName
                            setOnClickListener { applyPrecision(elapsed, item) }
                        }
                }
                .forEach { view -> addView(view) }
        }
    }

    private fun applyPrecision(elapsed: TextView, item: Drain678TimePrecisions) {
        val time = viewModel.elapsedTime(item)
        elapsed.text = getString(R.string.drain678_text_time_from_reboot_pattern, time)
    }
}