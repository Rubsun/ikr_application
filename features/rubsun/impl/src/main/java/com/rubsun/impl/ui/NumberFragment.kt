package com.rubsun.impl.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.textfield.TextInputEditText
import com.rubsun.api.domain.models.NumberDisplayModel
import com.rubsun.impl.R
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class NumberFragment : Fragment() {
    private val viewModel: NumberViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.content_rubsun_numbers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val randomNumberView = view.findViewById<TextView>(R.id.random_number)
        val numbersListContainer = view.findViewById<ViewGroup>(R.id.numbers_list)
        val searchInput = view.findViewById<TextInputEditText>(R.id.search_input)
        val valueInput = view.findViewById<TextInputEditText>(R.id.value_input)
        val labelInput = view.findViewById<TextInputEditText>(R.id.label_input)
        val addButton = view.findViewById<View>(R.id.add_button)
        val chart = view.findViewById<LineChart>(R.id.chart)

        searchInput?.doOnTextChanged { text, _, _, _ ->
            viewModel.onSearchQueryChanged(text?.toString().orEmpty())
        }

        addButton?.setOnClickListener {
            val valueText = valueInput?.text?.toString().orEmpty()
            val labelText = labelInput?.text?.toString().orEmpty()
            if (valueText.isNotBlank() && labelText.isNotBlank()) {
                val value = valueText.toIntOrNull()
                if (value != null) {
                    viewModel.addNumber(value, labelText)
                    valueInput?.text?.clear()
                    labelInput?.text?.clear()
                }
            }
        }

        setupChart(chart)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    state.randomNumber?.let { random ->
                        randomNumberView.text = getString(
                            R.string.rubsun_text_random_number_pattern,
                            random.value,
                            random.label,
                            random.squared
                        )
                    }

                    numbersListContainer.removeAllViews()
                    state.numbers.forEach { numberModel ->
                        val numberView = layoutInflater.inflate(
                            R.layout.item_rubsun_number,
                            numbersListContainer,
                            false
                        ) as TextView
                        numberView.text = getString(
                            R.string.rubsun_text_number_item_pattern,
                            numberModel.value,
                            numberModel.label,
                            numberModel.squared
                        )
                        numbersListContainer.addView(numberView)
                    }

                    updateChart(chart, state.numbers)
                }
            }
        }
    }

    private fun setupChart(chart: LineChart) {
        chart.description.isEnabled = false
        chart.setTouchEnabled(true)
        chart.setDragEnabled(true)
        chart.setScaleEnabled(true)
        chart.setPinchZoom(true)

        val xAxis = chart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.textColor = Color.BLACK
        xAxis.setDrawGridLines(false)

        val leftAxis = chart.axisLeft
        leftAxis.textColor = Color.BLACK
        leftAxis.setDrawGridLines(true)

        val rightAxis = chart.axisRight
        rightAxis.isEnabled = false

        chart.legend.isEnabled = false
    }

    private fun updateChart(chart: LineChart, numbers: List<NumberDisplayModel>) {
        val entries = numbers.mapIndexed { index, number ->
            Entry(index.toFloat(), number.squared.toFloat())
        }

        if (entries.isNotEmpty()) {
            val dataSet = LineDataSet(entries, "Квадраты чисел")
            dataSet.color = Color.BLUE
            dataSet.valueTextColor = Color.BLACK
            dataSet.lineWidth = 2f
            dataSet.setCircleColor(Color.BLUE)
            dataSet.circleRadius = 4f

            val lineData = LineData(dataSet)
            chart.data = lineData
            chart.invalidate()
        }
    }
}

