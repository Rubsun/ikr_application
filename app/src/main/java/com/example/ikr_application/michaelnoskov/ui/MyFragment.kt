package com.example.ikr_application.michaelnoskov.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.ikr_application.R
import com.google.android.material.button.MaterialButton

class MyFragment : Fragment() {
    private val viewModel by viewModels<MyViewModel>()

    private lateinit var currentTimeTextView: TextView
    private lateinit var elapsedTimeTextView: TextView
    private lateinit var precisionTextView: TextView
    private lateinit var circleView: View
    private lateinit var changePrecisionButton: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.content_michaelnoskov_content, container, false)

        currentTimeTextView = view.findViewById(R.id.time_text)
        elapsedTimeTextView = view.findViewById(R.id.elapsed)
        precisionTextView = view.findViewById(R.id.precision_text)
        circleView = view.findViewById(R.id.circle_view)
        changePrecisionButton = view.findViewById(R.id.change_precision_button)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.currentTimeText.observe(viewLifecycleOwner) { time ->
            currentTimeTextView.text = time
        }

        viewModel.elapsedTimeText.observe(viewLifecycleOwner) { elapsed ->
            elapsedTimeTextView.text = elapsed
        }

        viewModel.currentPrecision.observe(viewLifecycleOwner) { precision ->
            precisionTextView.text = "Precision: ${precision.typeName}"
        }

        viewModel.circleColor.observe(viewLifecycleOwner) { color ->
            circleView.setBackgroundColor(color)
        }

        changePrecisionButton.setOnClickListener {
            viewModel.changePrecision()
        }

        viewModel.startTimeUpdates()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.stopTimeUpdates()
    }
}