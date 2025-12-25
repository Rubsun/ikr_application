package com.argun.impl.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.argun.api.domain.ArgunTimePrecisions
import com.argun.impl.R
import com.argun.impl.ui.adapters.TimeRecordAdapter
import com.argun.network.api.TimeFormatter
import com.example.injector.inject
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

internal class ArgunFragment : Fragment() {
    private val viewModel by viewModels<ArgunViewModel>()
    private val timeFormatter: TimeFormatter by inject()
    private val timeRecordAdapter by lazy { TimeRecordAdapter(timeFormatter) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.content_argun_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textView = view.findViewById<TextView>(R.id.text)
        val elapsedTextView = view.findViewById<TextView>(R.id.elapsed)
        val buttonsGroup = view.findViewById<ViewGroup>(R.id.buttons)
        val searchEditText = view.findViewById<TextInputEditText>(R.id.search)
        val addButton = view.findViewById<Button>(R.id.add_button)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = timeRecordAdapter
        }

        searchEditText.addTextChangedListener { editable ->
            viewModel.search(editable?.toString() ?: "")
        }

        addButton.setOnClickListener {
            viewModel.addTimeRecord()
        }

        viewModel.timePrecisions()
            .map { item ->
                layoutInflater
                    .inflate(R.layout.item_argun_precision, buttonsGroup, false)
                    .apply {
                        (this as? MaterialButton)?.apply {
                            text = item.typeName
                            setOnClickListener {
                                viewModel.selectPrecision(item)
                            }
                        }
                    }
            }
            .forEach { buttonView -> buttonsGroup.addView(buttonView) }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state().collectLatest { state ->
                applyState(state, textView, elapsedTextView)
            }
        }
    }

    private fun applyState(
        state: ArgunViewModel.State,
        textView: TextView,
        elapsedTextView: TextView
    ) {
        textView.text = getString(R.string.argun_text_time_pattern, state.currentDate)

        if (state.elapsedTime.isNotEmpty()) {
            elapsedTextView.text = getString(
                R.string.argun_text_time_from_reboot_pattern,
                state.elapsedTime
            )
        }

        timeRecordAdapter.submitList(state.records)

        state.error?.let { _ ->
            Toast.makeText(
                requireContext(),
                "Error adding record",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

