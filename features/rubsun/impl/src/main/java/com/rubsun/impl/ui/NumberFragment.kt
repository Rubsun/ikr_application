package com.rubsun.impl.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.textfield.TextInputEditText
import com.rubsun.api.domain.models.NumberDisplayModel
import com.rubsun.chart.api.ChartConfig
import com.rubsun.chart.api.ChartData
import com.rubsun.chart.api.ChartEntry
import com.rubsun.chart.api.ChartView
import com.rubsun.chart.api.ChartViewFactory
import com.rubsun.impl.R
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class NumberFragment : Fragment(), KoinComponent {
    private val viewModel: NumberViewModel by viewModel()
    private val chartViewFactory: ChartViewFactory by inject()
    private var chartView: ChartView? = null

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
        val addWithFactButton = view.findViewById<View>(R.id.add_with_fact_button)
        val clearButton = view.findViewById<View>(R.id.clear_button)
        val chartContainer = view.findViewById<FrameLayout>(R.id.chart_container)
        
        chartContainer?.let { container ->
            val chartViewInstance = chartViewFactory.create(requireContext())
            if (chartViewInstance is ChartView) {
                chartView = chartViewInstance
                container.removeAllViews()
                container.addView(chartViewInstance as View)
            }
        }

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

        addWithFactButton?.setOnClickListener {
            val valueText = valueInput?.text?.toString().orEmpty()
            if (valueText.isNotBlank()) {
                val value = valueText.toIntOrNull()
                if (value != null) {
                    if (value > 100) {
                        Toast.makeText(
                            requireContext(),
                            "Число ограничено до 100. Будет использовано значение 100.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    val limitedValue = value.coerceIn(1, 100)
                    viewModel.addNumberWithFactFromApi(limitedValue)
                    valueInput?.text?.clear()
                    labelInput?.text?.clear()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Введите корректное число",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Введите число от 1 до 100",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        clearButton?.setOnClickListener {
            viewModel.clearAllNumbers()
        }

        chartView?.setup(ChartConfig(textColor = Color.BLACK, showGridLines = true))

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

                    updateChart(state.numbers)
                }
            }
        }
    }

    private fun updateChart(numbers: List<NumberDisplayModel>) {
        val entries = numbers.mapIndexed { index, number ->
            ChartEntry(index.toFloat(), number.squared.toFloat())
        }

        if (entries.isNotEmpty()) {
            val chartData = ChartData(
                entries = entries,
                lineColor = Color.BLUE,
                textColor = Color.BLACK
            )
            chartView?.setData(chartData)
            chartView?.invalidate()
        }
    }
}

