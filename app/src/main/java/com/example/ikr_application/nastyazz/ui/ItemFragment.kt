package com.example.ikr_application.nastyazz.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ikr_application.R
import com.example.ikr_application.databinding.FragmentNastyazzBinding
import com.example.ikr_application.nastyazz.data.FakeItemRepository
import com.example.ikr_application.nastyazz.domain.GetItemByIdUseCase
import com.example.ikr_application.nastyazz.domain.GetItemsUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ItemsFragment : Fragment(R.layout.fragment_nastyazz) {

    private var vb: FragmentNastyazzBinding? = null
    private val viewModel: ItemsViewModel by viewModels {
        ItemsViewModelFactory(
            GetItemsUseCase(FakeItemRepository()),
            GetItemByIdUseCase(FakeItemRepository())
        )
    }

    private lateinit var adapter: ItemsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentNastyazzBinding.bind(view)
        vb = binding

        adapter = ItemsAdapter()
        binding.itemsRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.itemsRecycler.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectLatest { state ->

                binding.progress.visibility =
                    if (state is UiState.Loading) View.VISIBLE else View.GONE

                if (state is UiState.Error) {
                    binding.errorText.visibility = View.VISIBLE
                    binding.errorText.text = state.message
                } else {
                    binding.errorText.visibility = View.GONE
                }

                if (state is UiState.Success) {
                    adapter.submitList(state.items)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        vb = null
    }
}
