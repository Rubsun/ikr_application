package com.n0tsszzz.impl.ui

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.n0tsszzz.api.domain.models.MarkoInfo
import com.n0tsszzz.api.domain.models.MarkoTimePrecisions
import com.n0tsszzz.impl.R
import com.n0tsszzz.impl.ui.adapters.TimeRecordAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.fragment.app.viewModels

internal class MarkoFragment : Fragment() {
    
    private fun getColorFromAttr(attrResId: Int): Int {
        val typedValue = TypedValue()
        requireContext().theme.resolveAttribute(attrResId, typedValue, true)
        return typedValue.data
    }
    
    private fun getColorPrimary(): Int {
        // Material Design 3 colorPrimary attribute ID
        val typedValue = TypedValue()
        requireContext().theme.resolveAttribute(android.R.attr.colorPrimary, typedValue, true)
        return typedValue.data
    }
    
    private fun getColorOnSurface(): Int {
        // Material Design 3 colorOnSurface attribute ID (16842806)
        val typedValue = TypedValue()
        // Using colorForeground as fallback for colorOnSurface in Material 3
        requireContext().theme.resolveAttribute(android.R.attr.colorForeground, typedValue, true)
        return typedValue.data
    }
    private val viewModel: MarkoViewModel by viewModels()
    private val timeRecordAdapter = TimeRecordAdapter()
    private var lastShownError: Throwable? = null

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
        val clearButton = view.findViewById<Button>(R.id.clear_button)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler)
        val chart = view.findViewById<LineChart>(R.id.chart)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = timeRecordAdapter
        }

        searchEditText.addTextChangedListener { editable ->
            viewModel.search(editable?.toString() ?: "")
        }

        addButton.apply {
            val buttonColor = getColorPrimary()
            backgroundTintList = ColorStateList.valueOf(buttonColor)
            setOnClickListener {
                viewModel.addTimeRecord()
            }
        }

        clearButton.apply {
            val buttonColor = getColorPrimary()
            backgroundTintList = ColorStateList.valueOf(buttonColor)
            setOnClickListener {
                viewModel.clearRecords()
            }
        }

        viewModel.timePrecisions()
            .map { item ->
                layoutInflater
                    .inflate(R.layout.item_n0tsszzz_precision, buttonsGroup, false)
                    .apply {
                        (this as? MaterialButton)?.apply {
                            text = item.typeName
                            val buttonColor = getColorPrimary()
                            backgroundTintList = ColorStateList.valueOf(buttonColor)
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
        
        val currentError = state.error
        val currentErrorMessage = currentError?.message
        
        when {
            currentError != null -> {
                val lastErrorMessage = lastShownError?.message
                if (currentErrorMessage != lastErrorMessage) {
                    lastShownError = currentError
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.n0tsszzz_error_adding_record),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            else -> {
                if (lastShownError != null) {
                    lastShownError = null
                }
            }
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
        val textColor = getColorOnSurface()
        xAxis.textColor = textColor
        xAxis.setDrawGridLines(false)

        val leftAxis = chart.axisLeft
        leftAxis.textColor = textColor
        leftAxis.setDrawGridLines(true)

        chart.axisRight.isEnabled = false
        chart.legend.isEnabled = false
    }

    private fun updateChart(chart: LineChart, records: List<MarkoInfo>) {
        if (records.isEmpty()) {
            chart.data = null
            chart.invalidate()
            return
        }

        val entries = records.mapIndexed { index, info ->
            Entry(index.toFloat(), info.elapsedTime.toFloat())
        }

        val lineColor = getColorPrimary()
        val textColor = getColorOnSurface()
        val dataSet = LineDataSet(entries, "Elapsed Time").apply {
            color = lineColor
            valueTextColor = textColor
            lineWidth = 2f
            setCircleColor(lineColor)
            circleRadius = 4f
            setDrawCircleHole(false)
        }

        val lineData = LineData(dataSet)
        chart.data = lineData
        chart.invalidate()
    }
}

