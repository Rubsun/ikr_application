package com.example.ikr_application.vtyapkova.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.ikr_application.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ViktoriaFragment : Fragment() {
    private val viewModel by viewModels<ViktoriaViewModel>()

    private lateinit var randomNameView: TextView
    private lateinit var randomNameCard: View
    private lateinit var viktoriaListContainer: ViewGroup
    private lateinit var searchInput: TextInputEditText
    private lateinit var firstNameInput: TextInputEditText
    private lateinit var lastNameInput: TextInputEditText
    private lateinit var addButton: View
    private lateinit var addFromApiButton: View
    private lateinit var progressBar: View
    private lateinit var errorText: TextView
    private lateinit var statisticsChart: BarChart

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.content_vtyapkova_names, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        setupObservers()
        setupListeners()
        
        // Убеждаемся, что поля могут получать фокус с клавиатуры компьютера
        ensureFieldsAreFocusable()
    }

    private fun initViews(view: View) {
        randomNameView = view.findViewById(R.id.random_name)
        randomNameCard = view.findViewById(R.id.random_name_card)
        viktoriaListContainer = view.findViewById(R.id.names_list)
        searchInput = view.findViewById(R.id.search_input)
        firstNameInput = view.findViewById(R.id.first_name_input)
        lastNameInput = view.findViewById(R.id.last_name_input)
        addButton = view.findViewById(R.id.add_button)
        addFromApiButton = view.findViewById(R.id.add_from_api_button)
        progressBar = view.findViewById(R.id.progress_bar)
        errorText = view.findViewById(R.id.error_text)
        statisticsChart = view.findViewById(R.id.statistics_chart)
        
        setupChart()
    }

    private fun setupObservers() {
        viewModel.uiState
            .onEach { state ->
                updateUi(state)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun updateUi(state: ViktoriaUiState) {
        // Обновление случайного имени
        state.randomViktoria?.let { randomViktoria ->
            randomNameView.text = getString(
                R.string.text_random_name_pattern_viktoria,
                randomViktoria.displayViktoria,
                randomViktoria.initials
            )
        }

        // Обновление списка имен
        updateViktoriaList(state.viktoriaList)
        
        // Обновление графика
        updateChart(state.viktoriaList)

        // Обновление состояния загрузки
        progressBar.isVisible = state.isLoading

        // Обновление ошибки
        if (state.error != null) {
            errorText.text = state.error
            errorText.isVisible = true
        } else {
            errorText.isVisible = false
        }

        // Обновление поисковой строки (чтобы не терялся ввод при обновлении)
        if (searchInput.text?.toString() != state.searchQuery) {
            searchInput.setText(state.searchQuery)
        }
    }

    private fun updateViktoriaList(viktoriaList: List<com.example.ikr_application.vtyapkova.domain.models.ViktoriaDisplayModel>) {
        viktoriaListContainer.removeAllViews()
        
        viktoriaList.forEach { viktoriaModel ->
            val itemView = layoutInflater.inflate(
                R.layout.item_vtyapkova_name,
                viktoriaListContainer,
                false
            ) as LinearLayout
            
            val nameText = itemView.findViewById<TextView>(R.id.name_text)
            val avatarText = itemView.findViewById<TextView>(R.id.avatar_text)
            
            nameText.text = getString(
                R.string.text_name_item_pattern_viktoria,
                viktoriaModel.displayViktoria,
                viktoriaModel.shortViktoria
            )
            
            // Отображаем первые буквы имени и фамилии в аватаре
            val initials = viktoriaModel.initials.replace(".", "").take(2).uppercase()
            avatarText.text = initials
            
            // Делаем аватар круглым с цветным фоном
            val drawable = android.graphics.drawable.GradientDrawable()
            drawable.shape = android.graphics.drawable.GradientDrawable.OVAL
            // Используем цвет из темы
            val typedValue = android.util.TypedValue()
            requireContext().theme.resolveAttribute(com.google.android.material.R.attr.colorPrimaryContainer, typedValue, true)
            drawable.setColor(typedValue.data)
            avatarText.background = drawable
            
            viktoriaListContainer.addView(itemView)
        }
    }
    
    private fun setupChart() {
        statisticsChart.description.isEnabled = false
        statisticsChart.setDrawGridBackground(false)
        statisticsChart.setScaleEnabled(false)
        
        val xAxis = statisticsChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = -45f
        
        val leftAxis = statisticsChart.axisLeft
        leftAxis.setDrawGridLines(true)
        leftAxis.axisMinimum = 0f
        
        statisticsChart.axisRight.isEnabled = false
        statisticsChart.legend.isEnabled = false
    }
    
    private fun updateChart(viktoriaList: List<com.example.ikr_application.vtyapkova.domain.models.ViktoriaDisplayModel>) {
        // Подсчитываем статистику по первым буквам имен
        val firstLetterCount = mutableMapOf<Char, Int>()
        viktoriaList.forEach { item ->
            val firstLetter = item.displayViktoria.firstOrNull()?.uppercaseChar() ?: return@forEach
            firstLetterCount[firstLetter] = (firstLetterCount[firstLetter] ?: 0) + 1
        }
        
        val sortedEntries = firstLetterCount.toList().sortedBy { it.first }
        val entries = sortedEntries.mapIndexed { index, (_, count) ->
            BarEntry(index.toFloat(), count.toFloat())
        }
        
        if (entries.isNotEmpty()) {
            val dataSet = BarDataSet(entries, "Количество имен")
            dataSet.color = requireContext().getColor(android.R.color.holo_blue_dark)
            dataSet.valueTextSize = 12f
            
            val barData = BarData(dataSet)
            barData.barWidth = 0.5f
            
            statisticsChart.data = barData
            
            // Настройка меток оси X
            val labels = sortedEntries.map { it.first.toString() }
            statisticsChart.xAxis.valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    val index = value.toInt()
                    return if (index in labels.indices) labels[index] else ""
                }
            }
            
            statisticsChart.notifyDataSetChanged()
            statisticsChart.invalidate()
        } else {
            // Очищаем график если нет данных
            statisticsChart.clear()
            statisticsChart.invalidate()
        }
    }

    private fun setupListeners() {
        // Настройка обработчиков фокуса для показа клавиатуры
        setupKeyboardForEditText(firstNameInput)
        setupKeyboardForEditText(lastNameInput)
        setupKeyboardForEditText(searchInput)
        
        // Настройка навигации между полями с помощью клавиатуры
        setupKeyboardNavigation()
        
        // Обработка поиска
        searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                viewModel.updateSearchQuery(s?.toString() ?: "")
            }
        })

        // Обработка добавления нового имени
        addButton.setOnClickListener {
            val firstName = firstNameInput.text?.toString()?.trim() ?: ""
            val lastName = lastNameInput.text?.toString()?.trim() ?: ""
            
            if (firstName.isNotEmpty() && lastName.isNotEmpty()) {
                viewModel.addViktoria(firstName, lastName)
                // Очистка полей после добавления
                firstNameInput.text?.clear()
                lastNameInput.text?.clear()
                // Скрываем клавиатуру после добавления
                hideKeyboard()
            }
        }

        // Обработка обновления случайного имени по клику
        randomNameCard.setOnClickListener {
            viewModel.refreshRandomViktoria()
        }
        
        // Обработка добавления из API
        addFromApiButton.setOnClickListener {
            viewModel.addViktoriaFromApi()
        }
    }
    
    private fun setupKeyboardForEditText(editText: TextInputEditText) {
        editText.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                showKeyboard(view)
            }
        }
        
        editText.setOnClickListener {
            editText.requestFocus()
            showKeyboard(editText)
        }
    }
    
    private fun showKeyboard(view: View) {
        view.post {
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }
    
    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocus = view?.findFocus()
        if (currentFocus != null) {
            imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
        }
    }
    
    private fun setupKeyboardNavigation() {
        // Обработка нажатия Enter для перехода между полями
        firstNameInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                lastNameInput.requestFocus()
                showKeyboard(lastNameInput)
                true
            } else {
                false
            }
        }
        
        lastNameInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                searchInput.requestFocus()
                showKeyboard(searchInput)
                true
            } else {
                false
            }
        }
        
        searchInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                // Выполняем поиск
                searchInput.clearFocus()
                hideKeyboard()
                true
            } else {
                false
            }
        }
    }
    
    private fun ensureFieldsAreFocusable() {
        // Убеждаемся, что все поля могут получать фокус с клавиатуры компьютера
        firstNameInput.isFocusable = true
        firstNameInput.isFocusableInTouchMode = true
        firstNameInput.isClickable = true
        
        lastNameInput.isFocusable = true
        lastNameInput.isFocusableInTouchMode = true
        lastNameInput.isClickable = true
        
        searchInput.isFocusable = true
        searchInput.isFocusableInTouchMode = true
        searchInput.isClickable = true
    }
}