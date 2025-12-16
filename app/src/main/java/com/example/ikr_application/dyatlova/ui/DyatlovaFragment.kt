package com.example.ikr_application.dyatlova.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ikr_application.R
import com.example.ikr_application.databinding.ContentDyatlovaFragmentBinding
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class DyatlovaFragment : Fragment(R.layout.content_dyatlova_fragment) {

    private var _binding: ContentDyatlovaFragmentBinding? = null
    private val binding get() = _binding!!

    private val adapter = DestinationAdapter()
    private val viewModel: DyatlovaViewModel by viewModels { DyatlovaViewModel.factory() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = ContentDyatlovaFragmentBinding.bind(view)

        binding.destinationsRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@DyatlovaFragment.adapter
        }

        binding.searchInput.doOnTextChanged { text, _, _, _ ->
            viewModel.onQueryChanged(text?.toString().orEmpty())
        }
        binding.titleInput.doOnTextChanged { text, _, _, _ ->
            viewModel.onTitleChanged(text?.toString().orEmpty())
        }
        binding.countryInput.doOnTextChanged { text, _, _, _ ->
            viewModel.onCountryChanged(text?.toString().orEmpty())
        }
        binding.imageInput.doOnTextChanged { text, _, _, _ ->
            viewModel.onImageChanged(text?.toString().orEmpty())
        }
        binding.addButton.setOnClickListener {
            viewModel.onAddDestination()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect(::render)
            }
        }
    }

    private fun render(state: DyatlovaUiState) {
        binding.searchInput.updateIfNeeded(state.query)
        binding.titleInput.updateIfNeeded(state.draft.title)
        binding.countryInput.updateIfNeeded(state.draft.country)
        binding.imageInput.updateIfNeeded(state.draft.imageUrl)

        binding.emptyText.isVisible = state.isEmpty
        binding.destinationsRecycler.isVisible = state.destinations.isNotEmpty()

        adapter.submitList(state.destinations)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun TextInputEditText.updateIfNeeded(value: String) {
        if (text?.toString() != value) {
            setText(value)
            setSelection(value.length)
        }
    }
}

