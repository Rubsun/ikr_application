package com.n0tsszzz.impl.ui

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.injector.inject
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.n0tsszzz.api.domain.models.MarkoInfo
import com.n0tsszzz.chart.api.ChartConfig
import com.n0tsszzz.chart.api.ChartData
import com.n0tsszzz.chart.api.ChartEntry
import com.n0tsszzz.chart.api.ChartView
import com.n0tsszzz.chart.api.ChartViewFactory
import com.n0tsszzz.impl.R
import com.n0tsszzz.impl.ui.adapters.TimeRecordAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

internal class MarkoFragment : Fragment() {
    
    private val chartViewFactory: ChartViewFactory by inject()
    
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
        val chartContainer = view.findViewById<FrameLayout>(R.id.chart_container)
        
        // Создаем ChartView программно через фабрику из api модуля
        val chartViewAsView = chartViewFactory.create(requireContext())
        val chart = chartViewAsView as? ChartView
            ?: throw IllegalStateException("Created view must implement ChartView")
        chartContainer.addView(chartViewAsView, FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        ))

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
        chart: ChartView
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

    private fun setupChart(chart: ChartView) {
        val textColor = getColorOnSurface()
        chart.setup(ChartConfig(
            textColor = textColor,
            showGridLines = true
        ))
    }

    private fun updateChart(chart: ChartView, records: List<MarkoInfo>) {
        if (records.isEmpty()) {
            chart.setData(ChartData(emptyList(), 0, 0))
            chart.invalidate()
            return
        }

        val entries = records.mapIndexed { index, info ->
            ChartEntry(index.toFloat(), info.elapsedTime.toFloat())
        }

        val lineColor = getColorPrimary()
        val textColor = getColorOnSurface()
        chart.setData(ChartData(
            entries = entries,
            lineColor = lineColor,
            textColor = textColor
        ))
        chart.invalidate()
    }
}

