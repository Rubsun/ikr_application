package com.example.ikr_application.rin2396.ui

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
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch
import timber.log.Timber

class RinFragment : Fragment() {
    private val viewModel by viewModels<RinViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.content_rin2396_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Timber.d("RinFragment created")

        val searchEditText = view.findViewById<EditText>(R.id.search_input)
        val textView = view.findViewById<TextView>(R.id.text)
        val elapsedTextView = view.findViewById<TextView>(R.id.elapsed)
        val entriesCountTextView = view.findViewById<TextView>(R.id.entries_count)
        val buttonsGroup = view.findViewById<ViewGroup>(R.id.buttons)
        val addButton = view.findViewById<MaterialButton>(R.id.add_button)

        // Setup search listener
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.updateSearchQuery(s?.toString() ?: "")
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Setup add button
        addButton.setOnClickListener {
            Timber.d("Add button clicked")
            viewModel.addTimeEntry()
        }

        // Collect UI state
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    Timber.d("UI State updated: entries=${state.timeEntries.size}, query='${state.searchQuery}'")

                    // Update date
                    textView.text = getString(R.string.rin2396_text_time_pattern, state.currentDate)

                    // Update elapsed time
                    elapsedTextView.text = getString(
                        R.string.rin2396_text_time_from_reboot_pattern,
                        state.elapsedTime
                    )

                    // Update entries count
                    entriesCountTextView.text = resources.getString(
                        R.string.rin2396_entries_count,
                        state.timeEntries.size
                    )

                    // Update precision buttons
                    buttonsGroup.removeAllViews()
                    viewModel.timePrecisions()
                        .map { precision ->
                            layoutInflater
                                .inflate(R.layout.item_rin2396_precision, buttonsGroup, false)
                                .apply {
                                    (this as? TextView)?.text = precision.typeName
                                    setOnClickListener {
                                        Timber.d("Precision clicked: ${precision.typeName}")
                                        viewModel.selectPrecision(precision)
                                    }
                                }
                        }
                        .forEach { buttonView -> buttonsGroup.addView(buttonView) }
                }
            }
        }
    }
}