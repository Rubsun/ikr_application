package com.alexcode69.impl.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.alexcode69.impl.R
import com.example.logger.api.debug
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class Alexcode69Fragment : Fragment() {
    private val viewModel: MyViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.content_alexcode69_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        "Alexcode69Fragment created".debug()

        val searchEditText = view.findViewById<EditText>(R.id.search_input)
        val textView = view.findViewById<TextView>(R.id.text)
        val elapsed = view.findViewById<TextView>(R.id.elapsed)
        val buttons = view.findViewById<ViewGroup>(R.id.buttons)
        val fetchInfoButton = view.findViewById<com.google.android.material.button.MaterialButton>(R.id.fetch_info_button)
        val requestInfoText = view.findViewById<TextView>(R.id.request_info_text)

        // Setup search listener
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.updateSearchQuery(s?.toString() ?: "")
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Setup fetch info button
        fetchInfoButton.setOnClickListener {
            viewModel.fetchRequestInfo()
        }

        // Collect UI state
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    "UI State updated: entries=${state.timeEntries.size}, query='${state.searchQuery}'".debug()
                    
                    // Update date
                    textView.text = getString(R.string.alexcode69_text_time_pattern, state.currentDate)

                    // Update entry list info
                    elapsed.text = "Entries found: ${state.timeEntries.size}"

                    // Update request info
                    state.requestInfo?.let { info ->
                        requestInfoText.text = "URL: ${info.url}\nOrigin: ${info.origin}"
                    } ?: run {
                        if (state.requestInfo == null) {
                            requestInfoText.text = "No request info yet"
                        }
                    }

                    // Update buttons based on precision
                    buttons.removeAllViews()
                    viewModel.timePrecisions()
                        .map { item ->
                            layoutInflater
                                .inflate(R.layout.item_alexcode69_precision, buttons, false)
                                .apply {
                                    (this as? TextView)?.text = item.typeName
                                    setOnClickListener { 
                                        "Precision clicked: ${item.typeName}".debug()
                                    }
                                }
                        }
                        .forEach { v -> buttons.addView(v) }
                }
            }
        }
    }
}

