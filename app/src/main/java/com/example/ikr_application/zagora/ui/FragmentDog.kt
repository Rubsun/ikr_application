package com.example.ikr_application.zagora.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil3.load
import coil3.request.crossfade
import com.example.ikr_application.databinding.FragmentDogBinding
import com.example.ikr_application.zagora.domain.ZagoraUiState
import kotlinx.coroutines.launch

class FragmentDog : Fragment() {

    private var _binding: FragmentDogBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    updateUi(uiState)
                }
            }
        }

        binding.breedSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.uiState.value.breeds.getOrNull(position)?.let { viewModel.selectBreed(it) }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.regenerateButton.setOnClickListener {
            viewModel.regenerateImage()
        }
    }

    private fun updateUi(uiState: ZagoraUiState) {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, uiState.breeds)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.breedSpinner.adapter = adapter

        uiState.selectedBreed?.let {
            val position = uiState.breeds.indexOf(it)
            if (position != binding.breedSpinner.selectedItemPosition) {
                binding.breedSpinner.setSelection(position)
            }
        }

        binding.loadingSpinner.isVisible = uiState.isLoading
        binding.dogImageView.isVisible = !uiState.isLoading && uiState.dogImage != null
        binding.regenerateButton.isVisible = uiState.dogImage != null

        uiState.dogImage?.let {
            binding.dogImageView.load(it.imageUrl) {
                crossfade(true)
            }
        } ?: run {
            binding.dogImageView.setImageDrawable(null)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}