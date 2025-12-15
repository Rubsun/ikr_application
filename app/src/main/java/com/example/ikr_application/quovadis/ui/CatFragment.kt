package com.example.ikr_application.quovadis.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ikr_application.databinding.RoganovCatFragmentBinding
import com.example.ikr_application.quovadis.domain.GetCatUseCase
import com.example.ikr_application.quovadis.data.CatRepository

class CatFragment : Fragment() {
    private lateinit var viewModel: CatViewModel
    private lateinit var binding: RoganovCatFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RoganovCatFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repository = CatRepository()
        val useCase = GetCatUseCase(repository)
        viewModel = CatViewModel(useCase)
        setupObservers()
        loadCat()
    }

    private fun setupObservers() {
        viewModel.catLiveData.observe(viewLifecycleOwner) { cat ->
            binding.catNameTv.text = cat.name
            if (cat.phrase != null) {
                binding.phraseChip.text = cat.phrase
                binding.phraseChip.visibility = View.VISIBLE
            } else {
                binding.phraseChip.visibility = View.GONE
            }
        }
    }

    private fun loadCat() {
        binding.loadButton.setOnClickListener {
            val phrase = binding.phraseInput.text.toString().takeIf { it.isNotBlank() }
            viewModel.loadRandomCat(phrase)
        }
        viewModel.loadRandomCat()
    }
}