package com.example.ikr_application.kristevt.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.ikr_application.R
import com.example.ikr_application.kristevt.domain.TimePrecisions
import java.sql.Time

class MyFragment : Fragment() {
    private val viewModel: MyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.content_kristevt_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.text).apply {
            val date = viewModel.date()
            text = getString(R.string.text_time_pattern, date)
        }

        view.findViewById<TextView>(R.id.text1).apply {
            val elapsed = viewModel.elapsed(TimePrecisions.MS)
            text = getString(R.string.elapsed_time_pattern, elapsed)
        }
    }
}