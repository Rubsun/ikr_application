package com.example.ikr_application.n0tsSzzz.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ikr_application.R
import com.example.ikr_application.n0tsSzzz.ui.adapters.TimeRecordAdapter
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MarkoFragment : Fragment() {
    private val viewModel by viewModels<MarkoViewModel>()
    private val timeRecordAdapter = TimeRecordAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.content_n0tsszzz_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textView = view.findViewById<TextView>(R.id.text)
        val elapsedTextView = view.findViewById<TextView>(R.id.elapsed)
        val buttonsGroup = view.findViewById<ViewGroup>(R.id.buttons)
        val searchEditText = view.findViewById<TextInputEditText>(R.id.search)
        val addButton = view.findViewById<Button>(R.id.add_button)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler)
        val chart = view.findViewById<LineChart>(R.id.chart)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = timeRecordAdapter
        }

        searchEditText.addTextChangedListener { editable ->
            viewModel.search(editable?.toString() ?: "")
        }

        addButton.setOnClickListener {
            viewModel.addTimeRecord()
        }

        viewModel.timePrecisions()
            .map { item ->
                layoutInflater
                    .inflate(R.layout.item_n0tsszzz_precision, buttonsGroup, false)
                    .apply {
                        (this as? MaterialButton)?.apply {
                            text = item.typeName
                            backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.red)
                            setOnClickListener {
                                viewModel.selectPrecision(item)
                            }
                        }
                    }
            }
            .forEach { buttonView -> buttonsGroup.addView(buttonView) }

        setupChart(chart)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state().collectLatest { state ->
                applyState(state, textView, elapsedTextView, chart)
            }
        }
    }

    private fun applyState(
        state: MarkoViewModel.State,
        textView: TextView,
        elapsedTextView: TextView,
        chart: LineChart
    ) {
        textView.text = getString(R.string.n0tsszzz_text_time_pattern, state.currentDate)
        
        if (state.elapsedTime.isNotEmpty()) {
            elapsedTextView.text = getString(
                R.string.n0tsszzz_text_time_from_reboot_pattern,
                state.elapsedTime
            )
        }

        timeRecordAdapter.submitList(state.records)

        updateChart(chart, state.records)
        
        state.error?.let { _ ->
            Toast.makeText(
                requireContext(),
                getString(R.string.n0tsszzz_error_adding_record),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setupChart(chart: LineChart) {
        chart.description.isEnabled = false
        chart.setTouchEnabled(true)
        chart.isDragEnabled = true
        chart.setScaleEnabled(true)
        chart.setPinchZoom(true)

        val xAxis = chart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.textColor = Color.BLACK
        xAxis.setDrawGridLines(false)

        val leftAxis = chart.axisLeft
        leftAxis.textColor = Color.BLACK
        leftAxis.setDrawGridLines(true)

        chart.axisRight.isEnabled = false
        chart.legend.isEnabled = false
    }

    private fun updateChart(chart: LineChart, records: List<com.example.ikr_application.n0tsSzzz.data.models.MarkoInfo>) {
        if (records.isEmpty()) {
            chart.data = null
            chart.invalidate()
            return
        }

        val entries = records.mapIndexed { index, info ->
            Entry(index.toFloat(), info.elapsedTime.toFloat())
        }

        val dataSet = LineDataSet(entries, "Elapsed Time").apply {
            color = Color.BLUE
            valueTextColor = Color.BLACK
            lineWidth = 2f
            setCircleColor(Color.BLUE)
            circleRadius = 4f
            setDrawCircleHole(false)
        }

        val lineData = LineData(dataSet)
        chart.data = lineData
        chart.invalidate()
    }
}
