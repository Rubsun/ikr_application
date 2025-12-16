package com.example.ikr_application.michaelnoskov.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ikr_application.R
import com.example.ikr_application.michaelnoskov.ui.adapter.ItemsAdapter
import com.example.ikr_application.michaelnoskov.ui.viewmodel.ColorSquareViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class ColorSquareFragment : Fragment() {

    private val viewModel: ColorSquareViewModel by viewModels()

    private lateinit var squareView: View
    private lateinit var itemsRecyclerView: androidx.recyclerview.widget.RecyclerView
    private lateinit var searchEditText: TextInputEditText
    private lateinit var addItemEditText: TextInputEditText
    private lateinit var addItemButton: MaterialButton
    private lateinit var changeColorButton: MaterialButton
    private lateinit var rotateButton: MaterialButton
    private lateinit var resizeButton: MaterialButton
    private lateinit var barChart: BarChart
    private lateinit var itemsCountTextView: android.widget.TextView

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
        barChart = view.findViewById(R.id.bar_chart)
        itemsCountTextView = view.findViewById(R.id.items_count_text)

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
        barChart.description.isEnabled = false
        barChart.legend.isEnabled = false
        barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        barChart.axisLeft.axisMinimum = 0f
        barChart.axisRight.isEnabled = false
        barChart.setTouchEnabled(false)
        barChart.animateY(1000)
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

                    // Обновляем график
                    updateChart(state.chartData)
                }
            }
        }
    }

    private fun updateSquareView(state: com.example.ikr_application.michaelnoskov.ui.viewmodel.ColorSquareState) {
        squareView.setBackgroundColor(state.squareState.color)
        squareView.rotation = state.squareState.rotation

        // Обновляем размер с анимацией
        squareView.layoutParams.width = state.squareState.size
        squareView.layoutParams.height = state.squareState.size

        // Анимация изменения размера
        squareView.animate()
            .scaleX(1.05f)
            .scaleY(1.05f)
            .setDuration(100)
            .withEndAction {
                squareView.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(100)
                    .start()
            }
            .start()

        squareView.requestLayout()
    }

    private fun updateChart(data: List<Pair<String, Float>>) {
        if (data.isEmpty()) return

        val entries = data.mapIndexed { index, pair ->
            BarEntry(index.toFloat(), pair.second)
        }

        val dataSet = BarDataSet(entries, "")
        dataSet.colors = listOf(
            Color.RED,
            Color.GREEN,
            Color.BLUE,
            Color.YELLOW,
            Color.MAGENTA
        )
        dataSet.valueTextSize = 12f
        dataSet.setValueTextColors(listOf(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK))

        val barData = BarData(dataSet)
        barData.barWidth = 0.5f

        barChart.data = barData

        // Используем строки ресурсов для подписей
        barChart.xAxis.valueFormatter = object : com.github.mikephil.charting.formatter.ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                val index = value.toInt()
                return when (index) {
                    0 -> getString(R.string.michaelnoskov_chart_label_red)
                    1 -> getString(R.string.michaelnoskov_chart_label_green)
                    2 -> getString(R.string.michaelnoskov_chart_label_blue)
                    3 -> getString(R.string.michaelnoskov_chart_label_yellow)
                    4 -> getString(R.string.michaelnoskov_chart_label_purple)
                    else -> ""
                }
            }
        }

        barChart.invalidate()
        barChart.animateY(1000)
    }
}