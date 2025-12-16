package com.example.ikr_application.artemkaa.ui

import com.example.ikr_application.nfirex.ui.MyViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.ikr_application.R
import com.example.ikr_application.nfirex.domain.TimePrecisions

class ArtemkaaFragment : Fragment() {
    private val viewModel by viewModels<MyViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.content_nfirex_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.text).apply {
            val date = viewModel.date()
            text = getString(R.string.artemkaa_text_time_pattern, date)
        }

        val elapsed = view.findViewById<TextView>(R.id.elapsed)
        view.findViewById<ViewGroup>(R.id.buttons).apply {
            viewModel.timePrecisions()
                .map { item ->
                    layoutInflater
                        .inflate(R.layout.item_nfirex_precision, this, false)
                        .apply {
                            (this as? TextView)?.text = item.typeName
                            setOnClickListener { applyPrecision(elapsed, item) }
                        }
                }
                .forEach { view -> addView(view) }
        }
    }

    private fun applyPrecision(elapsed: TextView, item: TimePrecisions) {
        val time = viewModel.elapsedTime(item)
        elapsed.text = getString(R.string.artemkaa_text_time_from_reboot_pattern, time)
    }
}