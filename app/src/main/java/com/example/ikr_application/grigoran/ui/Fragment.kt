package com.example.ikr_application.grigoran.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ikr_application.R
import com.example.ikr_application.databinding.FragmentGrigoranBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class GrigoranFragment : Fragment(R.layout.fragment_grigoran) {

    private var vb: FragmentGrigoranBinding? = null

    private val viewModel: ExampleViewModel by viewModels()


    private lateinit var adapter: ExampleAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("ExampleFragment", "onViewCreated called")


        val binding = FragmentGrigoranBinding.bind(view)
        vb = binding

        adapter = ExampleAdapter()
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter

        binding.btnAdd.setOnClickListener {
            val title = binding.inputTitle.text.toString()
            val price = binding.inputPrice.text.toString().toIntOrNull() ?: 0
            viewModel.onAddClicked(title, price)
            binding.inputTitle.text.clear()
            binding.inputPrice.text.clear()
        }

        binding.inputMinPrice.doAfterTextChanged {
            val value = it.toString().toDoubleOrNull() ?: 0.0
            viewModel.onMinPriceChanged(value)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->

                binding.progress.visibility =
                    if (state.isLoading) View.VISIBLE else View.GONE

                if (state.error != null) {
                    binding.errorText.visibility = View.VISIBLE
                    binding.errorText.text = state.error
                } else {
                    binding.errorText.visibility = View.GONE
                }

                adapter.submitList(state.items)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        vb = null
    }
}