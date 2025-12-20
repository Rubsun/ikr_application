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
import com.github.mikephil.charting.charts.LineChart
import com.momuswinner.api.domain.models.Point
import com.momuswinner.api.domain.models.PointsState
import com.momuswinner.impl.R
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class GraphFragment : Fragment(R.layout.momus_winner_fragment_graph) {
    private val viewModel: PointsViewModel by viewModel()
    private lateinit var chart: LineChart
    private lateinit var textPointCount: TextView
    private lateinit var textPointsList: TextView
    private lateinit var buttonDrawGraph: Button
    private lateinit var editTextX: EditText
    private lateinit var editTextY: EditText
    private lateinit var buttonAddPoint: Button
    private lateinit var buttonClearPoints: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        chart = view.findViewById(R.id.chart)
        textPointCount = view.findViewById(R.id.textPointCount)
        textPointsList = view.findViewById(R.id.textPointsList)
        buttonDrawGraph = view.findViewById(R.id.buttonDrawGraph)
        editTextX = view.findViewById(R.id.editTextX)
        editTextY = view.findViewById(R.id.editTextY)
        buttonAddPoint = view.findViewById(R.id.buttonAddPoint)
        buttonClearPoints = view.findViewById(R.id.buttonClearPoints)
        
        setupChart()
        setupButtons()
        observePointsState()
    }

    private fun setupChart() {
        with(chart) {
            description.isEnabled = true
            description.text = getString(R.string.areg_chart_description)
            description.textSize = resources.getDimension(R.dimen.areg_text_size_tiny)

            setTouchEnabled(true)
            setPinchZoom(true)
            isDragEnabled = true

            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(true)
            xAxis.gridColor = ContextCompat.getColor(requireContext(), R.color.areg_color_grid)
            xAxis.gridLineWidth = resources.getDimension(R.dimen.areg_chart_grid_width)
            xAxis.textSize = resources.getDimension(R.dimen.areg_text_size_tiny)

            axisLeft.setDrawGridLines(true)
            axisLeft.gridColor = ContextCompat.getColor(requireContext(), R.color.areg_color_grid)
            axisLeft.gridLineWidth = resources.getDimension(R.dimen.areg_chart_grid_width)
            axisLeft.textSize = resources.getDimension(R.dimen.areg_text_size_tiny)
            axisRight.isEnabled = false

            legend.isEnabled = false

            animateXY(1000, 1000)
        }
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

        val entries = mutableListOf<Entry>()
        val xLabels = mutableListOf<String>()

        points.forEachIndexed { index, point ->
            entries.add(Entry(index.toFloat(), point.y.toFloat()))
            xLabels.add(String.format("%.1f", point.x))
        }

        val dataSet = LineDataSet(entries, getString(R.string.areg_chart_points_label)).apply {
            color = ContextCompat.getColor(requireContext(), R.color.areg_color_primary)
            valueTextColor = ContextCompat.getColor(requireContext(), R.color.areg_color_text)
            lineWidth = resources.getDimension(R.dimen.areg_chart_line_width)

            setCircleColor(ContextCompat.getColor(requireContext(), R.color.areg_color_accent))
            circleRadius = resources.getDimension(R.dimen.areg_chart_point_size)
            setDrawCircleHole(false)

            setDrawValues(false)
            mode = LineDataSet.Mode.LINEAR

            highLightColor = ContextCompat.getColor(requireContext(), R.color.areg_color_highlight)
            setDrawHorizontalHighlightIndicator(false)
        }

        chart.xAxis.valueFormatter = IndexAxisValueFormatter(xLabels)
        chart.data = LineData(dataSet)

        if (entries.size > 10) {
            chart.setVisibleXRangeMaximum(10f)
            chart.moveViewToX(entries.last().x)
        }

        chart.invalidate()
    }

    private fun clearChart() {
        chart.clear()
        chart.description.text = getString(R.string.areg_chart_description)
        chart.invalidate()
    }
}
