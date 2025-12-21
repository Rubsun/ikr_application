package com.example.demyanenko.impl.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.impl.databinding.DemyanenkoF1carFragmentBinding
import com.example.impl.domain.GetF1CarUseCase
import com.example.impl.ui.F1CarViewModel

import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

internal class F1CarFragment : Fragment() {

    private val getF1CarUseCase: GetF1CarUseCase by inject()

    private lateinit var viewModel: F1CarViewModel
    private lateinit var binding: DemyanenkoF1carFragmentBinding
    private lateinit var adapter: F1CarAdapter

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
        viewModel = F1CarViewModel(getF1CarUseCase)

        setupRecyclerView()
        setupSearchListener()
        setupAddCarListener()
        observeState()
    }

    private fun setupRecyclerView() {
        adapter = F1CarAdapter()
        binding.carsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@F1CarFragment.adapter
        }
    }

    private fun setupSearchListener() {
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                viewModel.searchF1Cars(s?.toString() ?: "")
            }
        })
    }

    private fun setupAddCarListener() {
        binding.addCarButton.setOnClickListener {
            val carName = binding.carNameEditText.text.toString().trim()
            val carSound = binding.carSoundEditText.text.toString().trim()
            if (carName.isNotBlank()) {
                viewModel.addF1Car(carName, carSound.takeIf { it.isNotBlank() })
                binding.carNameEditText.text.clear()
                binding.carSoundEditText.text.clear()
            }
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                adapter.submitList(state.cars)
                binding.loadingProgressBar.visibility =
                    if (state.isLoading) View.VISIBLE else View.GONE
                if (state.error != null) {
                    binding.errorTextView.text = state.error
                    binding.errorTextView.visibility = View.VISIBLE
                } else {
                    binding.errorTextView.visibility = View.GONE
                }
            }
        }
    }

    companion object {
        fun newInstance(): F1CarFragment = F1CarFragment()
    }
}
