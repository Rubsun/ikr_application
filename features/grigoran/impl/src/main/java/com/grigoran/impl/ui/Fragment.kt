package com.grigoran.impl.ui

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.grigoran.impl.R
import com.grigoran.impl.databinding.FragmentGrigoranBinding
import com.imageloader.api.ImageLoader
import com.example.injector.inject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class GrigoranFragment : Fragment(R.layout.fragment_grigoran) {

    private var vb: FragmentGrigoranBinding? = null
    private val viewModel: ExampleViewModel by viewModels()
    private val imageLoader: ImageLoader by inject()
    private lateinit var adapter: ExampleAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentGrigoranBinding.bind(view)
        vb = binding

        adapter = ExampleAdapter(imageLoader)
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter

        binding.inputTitle.doAfterTextChanged { text ->
            viewModel.search(text.toString())
        }

        binding.sortButton.setOnClickListener {
            val ascending = !viewModel.sortAscending
            viewModel.setSortAscending(ascending)
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state().collectLatest { state ->
                binding.progress.visibility = if (state.isPending) View.VISIBLE else View.GONE

                if (state.error != null) {
                    binding.errorText.visibility = View.VISIBLE
                    binding.errorText.text = state.error?.message
                } else {
                    binding.errorText.visibility = View.GONE
                }

                val uiItems = state.data.map {
                    ItemUi(
                        id = it.id,
                        displayTitle = it.title,
                        displayPrice = "${it.price} ₽",
                        imageUrt = it.ItemUrl
                    )
                }
                adapter.submitList(uiItems)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        vb = null
    }
}