package com.example.ikr_application.nastyazz.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ikr_application.R
import com.example.ikr_application.databinding.FragmentNastyazzBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ItemsFragment : Fragment(R.layout.fragment_nastyazz) {

    private var _binding: FragmentNastyazzBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ItemsViewModel by viewModels {
        ItemsViewModelFactory()
    }

    private val adapter = ItemsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentNastyazzBinding.bind(view)

        binding.itemsRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.itemsRecycler.adapter = adapter

        binding.searchEditText.doAfterTextChanged {
            viewModel.onSearch(it.toString())
        }
        binding.addButton.setOnClickListener {
            viewModel.onAddClicked("Новый элемент")
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                binding.progress.isVisible = state.isLoading
                adapter.submitList(state.items)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
