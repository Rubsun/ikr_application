package com.rin2396.impl.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.rin2396.impl.R
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch
import timber.log.Timber

internal class RinFragment :
    Fragment(R.layout.content_rin2396_content) {

    private val viewModel: RinViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchEditText = view.findViewById<EditText>(R.id.search_input)
        val textView = view.findViewById<TextView>(R.id.text)
        val elapsedTextView = view.findViewById<TextView>(R.id.elapsed)
        val entriesCountTextView = view.findViewById<TextView>(R.id.entries_count)
        val buttonsGroup = view.findViewById<ViewGroup>(R.id.buttons)
        val addButton = view.findViewById<MaterialButton>(R.id.add_button)

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.updateSearchQuery(s?.toString().orEmpty())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        addButton.setOnClickListener {
            viewModel.addTimeEntry()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->

                    textView.text = getString(
                        R.string.rin2396_text_time_pattern,
                        state.currentDate
                    )

                    elapsedTextView.text = getString(
                        R.string.rin2396_text_time_from_reboot_pattern,
                        state.elapsedTime
                    )

                    entriesCountTextView.text = getString(
                        R.string.rin2396_entries_count,
                        state.timeEntries.size
                    )

                    buttonsGroup.removeAllViews()
                    viewModel.timePrecisions.forEach { precision ->
                        val button = layoutInflater
                            .inflate(
                                R.layout.item_rin2396_precision,
                                buttonsGroup,
                                false
                            ) as TextView

                        button.text = precision.typeName
                        button.setOnClickListener {
                            viewModel.selectPrecision(precision)
                        }

                        buttonsGroup.addView(button)
                    }
                }
            }
        }
    }
}
