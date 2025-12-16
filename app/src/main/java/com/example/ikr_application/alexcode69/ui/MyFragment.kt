package com.example.ikr_application.alexcode69.ui

import com.example.ikr_application.alexcode69.ui.MyViewModel
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.ikr_application.R
import kotlinx.coroutines.launch
import timber.log.Timber

class Alexcode69Fragment : Fragment() {
    private val viewModel by viewModels<MyViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.content_alexcode69_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Timber.d("Alexcode69Fragment created")

        val searchEditText = view.findViewById<EditText>(R.id.search_input)
        val textView = view.findViewById<TextView>(R.id.text)
        val elapsed = view.findViewById<TextView>(R.id.elapsed)
        val buttons = view.findViewById<ViewGroup>(R.id.buttons)

        // Setup search listener
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.updateSearchQuery(s?.toString() ?: "")
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Collect UI state
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    Timber.d("UI State updated: entries=${state.timeEntries.size}, query='${state.searchQuery}'")
                    
                    // Update date
                    textView.text = getString(R.string.text_time_pattern, state.currentDate)

                    // Update entry list info
                    elapsed.text = "Entries found: ${state.timeEntries.size}"

                    // Update buttons based on precision
                    buttons.removeAllViews()
                    viewModel.timePrecisions()
                        .map { item ->
                            layoutInflater
                                .inflate(R.layout.item_alexcode69_precision, buttons, false)
                                .apply {
                                    (this as? TextView)?.text = item.typeName
                                    setOnClickListener { 
                                        Timber.d("Precision clicked: ${item.typeName}")
                                    }
                                }
                        }
                        .forEach { v -> buttons.addView(v) }
                }
            }
        }
    }
}