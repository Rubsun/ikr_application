package com.example.ikr_application.demyanenko.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ikr_application.databinding.DemyanenkoF1carFragmentBinding
import com.example.ikr_application.demyanenko.data.F1CarRepository
import com.example.ikr_application.demyanenko.domain.GetF1CarUseCase

class F1CarFragment : Fragment() {
    private lateinit var viewModel: F1CarViewModel
    private lateinit var binding: DemyanenkoF1carFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DemyanenkoF1carFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repository = F1CarRepository()
        val useCase = GetF1CarUseCase(repository)
        viewModel = F1CarViewModel(useCase)
        setupObservers()
        loadF1Car()
    }

    private fun setupObservers() {
        viewModel.f1carLiveData.observe(viewLifecycleOwner) { f1car ->
            binding.f1carNameTv.text = f1car.name
            if (f1car.sound != null) {
                binding.phraseChip.text = f1car.sound
                binding.phraseChip.visibility = View.VISIBLE
            } else {
                binding.phraseChip.visibility = View.GONE
            }
        }
    }

    private fun loadF1Car() {
        binding.loadButton.setOnClickListener {
            val sound = binding.phraseInput.text.toString().takeIf { it.isNotBlank() }
            viewModel.loadRandomF1Car(sound)
        }
        viewModel.loadRandomF1Car()
    }
}