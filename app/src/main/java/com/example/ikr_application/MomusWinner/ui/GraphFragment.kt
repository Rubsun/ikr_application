package com.example.ikr_application.MomusWinner.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.ikr_application.MomusWinner.data.models.Point
import com.example.ikr_application.MomusWinner.data.models.PointsState
import com.example.ikr_application.R
import com.example.ikr_application.databinding.MomusWinnerFragmentGraphBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class GraphFragment : Fragment() {
    private var _binding: MomusWinnerFragmentGraphBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PointsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MomusWinnerFragmentGraphBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[PointsViewModel::class.java]
        setupChart()
        setupButtons()
        observePointsState()
    }

    private fun setupChart() {
        with(binding.chart) {
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
        binding.buttonAddPoint.setOnClickListener {
            val xText = binding.editTextX.text.toString()
            val yText = binding.editTextY.text.toString()

            if (xText.isBlank() || yText.isBlank()) {
                Toast.makeText(requireContext(), getString(R.string.areg_toast_enter_xy), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                val x = xText.toDouble()
                val y = yText.toDouble()
                viewModel.addPoint(x, y)
                binding.editTextX.text?.clear()
                binding.editTextY.text?.clear()
            } catch (e: NumberFormatException) {
                Toast.makeText(requireContext(), getString(R.string.areg_toast_invalid_numbers), Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonClearPoints.setOnClickListener {
            viewModel.clearPoints()
            clearChart()
        }

        binding.buttonDrawGraph.setOnClickListener {
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
        binding.textPointCount.text = getString(R.string.areg_chart_loading)
        binding.textPointsList.text = ""
        binding.buttonDrawGraph.isEnabled = false
    }

    private fun showEmptyState() {
        binding.textPointCount.text = getString(R.string.areg_chart_no_points, 0)
        binding.textPointsList.text = getString(R.string.areg_chart_empty_points)
        binding.buttonDrawGraph.isEnabled = false
        binding.buttonDrawGraph.text = getString(R.string.areg_chart_draw_graph)
        clearChart()
    }

    private fun showSuccessState(points: List<Point>) {
        binding.textPointCount.text = getString(R.string.areg_chart_no_points, points.size)

        val maxPoints = 5
        val displayedPoints = if (points.size > maxPoints) points.takeLast(maxPoints) else points
        val pointsText = displayedPoints.joinToString("\n") { point ->
            getString(R.string.areg_chart_point_format, point.x, point.y)
        }

        binding.textPointsList.text = if (points.size > maxPoints) {
            getString(R.string.areg_chart_more_points_format, points.size - maxPoints) + "\n" + pointsText
        } else {
            pointsText
        }

        binding.buttonDrawGraph.isEnabled = true
        binding.buttonDrawGraph.text = getString(R.string.areg_chart_update_graph_format, points.size)
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

        binding.chart.xAxis.valueFormatter = IndexAxisValueFormatter(xLabels)
        binding.chart.data = LineData(dataSet)

        if (entries.size > 10) {
            binding.chart.setVisibleXRangeMaximum(10f)
            binding.chart.moveViewToX(entries.last().x)
        }

        binding.chart.invalidate()
    }

    private fun clearChart() {
        binding.chart.clear()
        binding.chart.description.text = getString(R.string.areg_chart_description)
        binding.chart.invalidate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}