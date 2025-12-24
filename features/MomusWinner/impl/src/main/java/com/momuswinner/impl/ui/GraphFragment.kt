package com.momuswinner.impl.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.momuswinner.api.domain.models.Point
import com.momuswinner.api.domain.models.PointsState
import com.momuswinner.api.domain.models.QuoteState
import com.momuswinner.chart.api.ChartConfig
import com.momuswinner.chart.api.ChartData
import com.momuswinner.chart.api.ChartEntry
import com.momuswinner.chart.api.LineChartFactory
import com.momuswinner.chart.api.LineChartView
import com.momuswinner.chart.api.LineMode
import com.momuswinner.chart.api.XAxisPosition
import com.momuswinner.impl.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class GraphFragment : Fragment(R.layout.momus_winner_fragment_graph), KoinComponent {
    private val viewModel: PointsViewModel by viewModel()
    private val chartFactory: LineChartFactory by inject()
    private lateinit var chartWrapper: LineChartViewWrapper
    private lateinit var chart: LineChartView
    private lateinit var textPointCount: TextView
    private lateinit var textPointsList: TextView
    private lateinit var buttonDrawGraph: Button
    private lateinit var editTextX: EditText
    private lateinit var editTextY: EditText
    private lateinit var buttonAddPoint: Button
    private lateinit var buttonClearPoints: Button
    private lateinit var textVpnWarning: TextView
    private lateinit var textQuoteDisplay: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        chartWrapper = view.findViewById(R.id.chart)
        chart = chartFactory.create(requireContext())
        chartWrapper.setChartView(chart)
        textPointCount = view.findViewById(R.id.textPointCount)
        textPointsList = view.findViewById(R.id.textPointsList)
        buttonDrawGraph = view.findViewById(R.id.buttonDrawGraph)
        editTextX = view.findViewById(R.id.editTextX)
        editTextY = view.findViewById(R.id.editTextY)
        buttonAddPoint = view.findViewById(R.id.buttonAddPoint)
        buttonClearPoints = view.findViewById(R.id.buttonClearPoints)
        textVpnWarning = view.findViewById(R.id.textVpnWarning)
        textQuoteDisplay = view.findViewById(R.id.textQuoteDisplay)

        viewModel.getQuote()
        setupChart()
        setupButtons()
        setupVpnWarning()
        observePointsState()
        observeQuoteState()
    }

    private fun setupChart() {
        val config = ChartConfig(
            description = getString(R.string.areg_chart_description),
            descriptionTextSize = resources.getDimension(R.dimen.areg_text_size_tiny),
            touchEnabled = true,
            pinchZoomEnabled = true,
            dragEnabled = true,
            xAxisPosition = XAxisPosition.BOTTOM,
            drawXGridLines = true,
            xGridColor = ContextCompat.getColor(requireContext(), R.color.areg_color_grid),
            xGridLineWidth = resources.getDimension(R.dimen.areg_chart_grid_width),
            xTextSize = resources.getDimension(R.dimen.areg_text_size_tiny),
            drawLeftGridLines = true,
            leftGridColor = ContextCompat.getColor(requireContext(), R.color.areg_color_grid),
            leftGridLineWidth = resources.getDimension(R.dimen.areg_chart_grid_width),
            leftTextSize = resources.getDimension(R.dimen.areg_text_size_tiny),
            rightAxisEnabled = false,
            legendEnabled = false
        )
        chart.setupChart(config)
    }

    private fun setupButtons() {
        buttonAddPoint.setOnClickListener {
            val xText = editTextX.text.toString()
            val yText = editTextY.text.toString()

            if (xText.isBlank() || yText.isBlank()) {
                Toast.makeText(requireContext(), getString(R.string.areg_toast_enter_xy), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                val x = xText.toDouble()
                val y = yText.toDouble()
                viewModel.addPoint(x, y)
                editTextX.text?.clear()
                editTextY.text?.clear()
            } catch (e: NumberFormatException) {
                Toast.makeText(requireContext(), getString(R.string.areg_toast_invalid_numbers), Toast.LENGTH_SHORT).show()
            }
        }

        buttonClearPoints.setOnClickListener {
            viewModel.clearPoints()
            clearChart()
        }

        buttonDrawGraph.setOnClickListener {
            val state = viewModel.pointsState.value
            if (state is PointsState.Success) {
                updateChart(state.points)
                Toast.makeText(requireContext(), getString(R.string.areg_toast_chart_updated), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), getString(R.string.areg_toast_no_points_to_draw), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupVpnWarning() {
        textVpnWarning.text = getString(R.string.areg_vpn_warning)
        textVpnWarning.visibility = View.VISIBLE
    }

    private fun observePointsState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.pointsState.collectLatest { state ->
                when (state) {
                    is PointsState.Loading -> showLoadingState()
                    is PointsState.Empty -> showEmptyState()
                    is PointsState.Success -> {
                        showSuccessState(state.points)
                        updateChart(state.points)
                    }
                }
            }
        }
    }
    private fun observeQuoteState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.quoteState.collectLatest { state ->
                when (state) {
                    is QuoteState.Idle -> {
                    }
                    is QuoteState.Loading -> {
                        textQuoteDisplay.text = getString(R.string.areg_quote_loading)
                        textQuoteDisplay.setTextColor(ContextCompat.getColor(requireContext(), R.color.areg_color_loading))
                    }
                    is QuoteState.Success -> {
                        val quoteText = getString(R.string.areg_quote_format, state.quote.quote, state.quote.author)
                        textQuoteDisplay.text = quoteText
                        textQuoteDisplay.setTextColor(ContextCompat.getColor(requireContext(), R.color.areg_color_text))

                        textVpnWarning.visibility = View.GONE
                    }
                    is QuoteState.Error -> {
                        textQuoteDisplay.text = getString(R.string.areg_quote_error, state.message)
                        textQuoteDisplay.setTextColor(ContextCompat.getColor(requireContext(), R.color.areg_color_error))

                        textVpnWarning.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun showLoadingState() {
        textPointCount.text = getString(R.string.areg_chart_loading)
        textPointsList.text = ""
        buttonDrawGraph.isEnabled = false
    }

    private fun showEmptyState() {
        textPointCount.text = getString(R.string.areg_chart_no_points, 0)
        textPointsList.text = getString(R.string.areg_chart_empty_points)
        buttonDrawGraph.isEnabled = false
        buttonDrawGraph.text = getString(R.string.areg_chart_draw_graph)
        clearChart()
    }

    private fun showSuccessState(points: List<Point>) {
        textPointCount.text = getString(R.string.areg_chart_no_points, points.size)

        val maxPoints = 5
        val displayedPoints = if (points.size > maxPoints) points.takeLast(maxPoints) else points
        val pointsText = displayedPoints.joinToString("\n") { point ->
            getString(R.string.areg_chart_point_format, point.x, point.y)
        }

        textPointsList.text = if (points.size > maxPoints) {
            getString(R.string.areg_chart_more_points_format, points.size - maxPoints) + "\n" + pointsText
        } else {
            pointsText
        }

        buttonDrawGraph.isEnabled = true
        buttonDrawGraph.text = getString(R.string.areg_chart_update_graph_format, points.size)
    }

    private fun updateChart(points: List<Point>) {
        if (points.isEmpty()) {
            clearChart()
            return
        }

        val entries = mutableListOf<ChartEntry>()
        val xLabels = mutableListOf<String>()

        points.forEachIndexed { index, point ->
            entries.add(ChartEntry(index.toFloat(), point.y.toFloat()))
            xLabels.add(String.format("%.1f", point.x))
        }

        val chartData = ChartData(
            entries = entries,
            xLabels = xLabels,
            label = getString(R.string.areg_chart_points_label),
            lineColor = ContextCompat.getColor(requireContext(), R.color.areg_color_primary),
            valueTextColor = ContextCompat.getColor(requireContext(), R.color.areg_color_text),
            lineWidth = resources.getDimension(R.dimen.areg_chart_line_width),
            circleColor = ContextCompat.getColor(requireContext(), R.color.areg_color_accent),
            circleRadius = resources.getDimension(R.dimen.areg_chart_point_size),
            drawCircleHole = false,
            drawValues = false,
            mode = LineMode.LINEAR,
            highlightColor = ContextCompat.getColor(requireContext(), R.color.areg_color_highlight),
            drawHorizontalHighlightIndicator = false,
            visibleXRangeMaximum = if (entries.size > 10) 10f else null
        )

        chart.updateChart(chartData)
    }

    private fun clearChart() {
        chart.clearChart()
    }
}
