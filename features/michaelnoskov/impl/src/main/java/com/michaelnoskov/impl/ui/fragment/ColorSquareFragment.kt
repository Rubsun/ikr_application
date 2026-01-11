package com.michaelnoskov.impl.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.michaelnoskov.chart.api.BarChartData
import com.michaelnoskov.chart.api.BarChartEntry
import com.michaelnoskov.chart.api.BarChartView
import com.michaelnoskov.chart.api.BarChartViewFactory
import com.michaelnoskov.chart.api.ChartConfig
import com.michaelnoskov.impl.R
import com.michaelnoskov.impl.ui.adapter.ItemsAdapter
import com.michaelnoskov.impl.ui.viewmodel.ColorSquareState
import com.michaelnoskov.impl.ui.viewmodel.ColorSquareViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class ColorSquareFragment : Fragment(), KoinComponent {

    private val viewModel: ColorSquareViewModel by viewModel()
    private val barChartViewFactory: BarChartViewFactory by inject()

    private lateinit var squareView: View
    private lateinit var itemsRecyclerView: androidx.recyclerview.widget.RecyclerView
    private lateinit var searchEditText: TextInputEditText
    private lateinit var addItemEditText: TextInputEditText
    private lateinit var addItemButton: MaterialButton
    private lateinit var changeColorButton: MaterialButton
    private lateinit var rotateButton: MaterialButton
    private lateinit var resizeButton: MaterialButton
    private lateinit var syncButton: MaterialButton
    private lateinit var barChart: BarChartView
    private lateinit var itemsCountTextView: android.widget.TextView
    private lateinit var temperatureTextView: android.widget.TextView

    private lateinit var itemsAdapter: ItemsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.content_michaelnoskov_content, container, false)

        squareView = view.findViewById(R.id.square_view)
        itemsRecyclerView = view.findViewById(R.id.items_recycler_view)
        searchEditText = view.findViewById(R.id.search_edit_text)
        addItemEditText = view.findViewById(R.id.add_item_edit_text)
        addItemButton = view.findViewById(R.id.add_item_button)
        changeColorButton = view.findViewById(R.id.change_color_button)
        rotateButton = view.findViewById(R.id.rotate_button)
        resizeButton = view.findViewById(R.id.resize_button)
        // Создаем BarChartView через фабрику и добавляем в контейнер
        val barChartContainer = view.findViewById<android.widget.FrameLayout>(R.id.bar_chart_container)
        val barChartView = barChartViewFactory.create(requireContext()) as BarChartView
        barChart = barChartView
        val params = android.widget.FrameLayout.LayoutParams(
            android.view.ViewGroup.LayoutParams.MATCH_PARENT,
            android.view.ViewGroup.LayoutParams.MATCH_PARENT
        )
        (barChartView as android.view.View).layoutParams = params
        barChartContainer?.addView(barChartView as android.view.View)
        itemsCountTextView = view.findViewById(R.id.items_count_text)
        temperatureTextView = view.findViewById(R.id.temperature_text)
        syncButton = view.findViewById(R.id.sync_button)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupRecyclerView()
        setupChart()
        collectState()
    }

    private fun setupUI() {
        changeColorButton.setOnClickListener {
            viewModel.changeSquareColor()
        }

        rotateButton.setOnClickListener {
            viewModel.rotateSquare()
        }

        resizeButton.setOnClickListener {
            viewModel.resizeSquare()
        }

        syncButton.setOnClickListener {
            viewModel.syncData()
        }

        addItemButton.setOnClickListener {
            val text = addItemEditText.text.toString()
            if (text.isNotBlank()) {
                viewModel.onAddItemClicked(text)
                addItemEditText.text?.clear()
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.michaelnoskov_error_empty_text),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onSearchQueryChanged(s?.toString().orEmpty())
            }
        })
    }

    private fun setupRecyclerView() {
        itemsAdapter = ItemsAdapter()
        itemsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        itemsRecyclerView.adapter = itemsAdapter
    }

    private fun setupChart() {
        barChart.setup(
            ChartConfig(
                textColor = Color.BLACK,
                showGridLines = false
            )
        )
    }

    private fun collectState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    // Обновляем квадрат
                    updateSquareView(state)

                    // Обновляем список
                    itemsAdapter.submitList(state.filteredItems)

                    // Обновляем счетчик
                    itemsCountTextView.text = getString(
                        R.string.michaelnoskov_items_count_default
                    ).replace("0", state.itemsCount.toString())

                    // Обновляем температуру
                    updateTemperature(state.currentTemperature)
                    
                    // Обновляем график истории температур
                    updateTemperatureChart(state.temperatureHistory)

                    // Показываем/скрываем загрузку
                    if (state.isLoading) {
                        syncButton.text = "Синхронизация..."
                        syncButton.isEnabled = false
                    } else {
                        syncButton.text = getString(R.string.michaelnoskov_button_sync)
                        syncButton.isEnabled = true
                    }

                    // Показываем ошибки
                    state.error?.let { error ->
                        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun updateSquareView(state: ColorSquareState) {
        squareView.setBackgroundColor(state.squareState.color)
        squareView.rotation = state.squareState.rotation

        val density = resources.displayMetrics.density
        val sizeInPx = if (state.squareState.size < 100) {
            (state.squareState.size * density).toInt()
        } else {
            state.squareState.size
        }

        squareView.layoutParams.width = sizeInPx
        squareView.layoutParams.height = sizeInPx

        squareView.requestLayout()
    }

    private fun updateTemperature(temperature: Double?) {
        temperatureTextView.text = if (temperature != null) {
            String.format("%.1f°C", temperature)
        } else {
            "--°C"
        }
    }

    private fun updateTemperatureChart(history: List<com.michaelnoskov.api.domain.repository.TemperaturePoint>) {
        if (history.isEmpty()) {
            barChart.setData(
                BarChartData(
                    entries = emptyList(),
                    barColor = Color.BLUE,
                    textColor = Color.BLACK
                )
            )
            barChart.invalidate()
            return
        }

        val entries = history.mapIndexed { index, point ->
            BarChartEntry(
                x = index.toFloat(),
                y = point.temperature.toFloat(),
                label = "${index + 1}"
            )
        }

        // Цвет зависит от температуры - используем градиент
        val avgTemp = history.map { it.temperature }.average()
        val barColor = when {
            avgTemp < 0 -> Color.BLUE
            avgTemp < 15 -> Color.CYAN
            avgTemp < 25 -> Color.GREEN
            else -> Color.RED
        }
        
        barChart.setData(
            BarChartData(
                entries = entries,
                barColor = barColor,
                textColor = Color.BLACK
            )
        )
        barChart.invalidate()
    }
}

