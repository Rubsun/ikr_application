package com.nastyazz.impel.nastyazz.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.nastyazz.impel.R
import com.nastyazz.impel.databinding.FragmentNastyazzBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

internal class ItemsFragment : Fragment(R.layout.fragment_nastyazz) {
    private var _binding: FragmentNastyazzBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ItemsViewModel by viewModels ()

    private val adapter = ItemsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentNastyazzBinding.bind(view)

        binding.itemsRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.itemsRecycler.adapter = adapter

        binding.searchEditText.doAfterTextChanged {
            viewModel.onSearch(it.toString())
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