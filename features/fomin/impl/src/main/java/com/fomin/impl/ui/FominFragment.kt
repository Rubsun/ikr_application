package com.fomin.impl.ui

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.injector.inject
import com.fomin.impl.R
import com.fomin.impl.databinding.ContentFominBinding
import com.fomin.impl.ui.adapters.CatBreedAdapter
import com.imageloader.api.ImageLoader
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

internal class FominFragment : Fragment(R.layout.content_fomin) {
    
    private val viewModel by viewModels<FominViewModel>()
    private val imageLoader: ImageLoader by inject()
    private val adapter by lazy { 
        CatBreedAdapter(imageLoader) { breed -> 
            navigateToDetail(breed.id)
        } 
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = ContentFominBinding.bind(view)

        binding.fominRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.fominRecycler.adapter = adapter

        binding.fominSearch.addTextChangedListener { editable ->
            viewModel.onQueryChanged(editable?.toString().orEmpty())
        }

        binding.fominRetry.setOnClickListener {
            viewModel.retry()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                binding.fominLoadingContainer.visibility = 
                    if (state.isLoading) View.VISIBLE else View.GONE
                binding.fominErrorContainer.visibility = 
                    if (state.error != null) View.VISIBLE else View.GONE
                binding.fominError.text = state.error

                binding.fominEmpty.visibility =
                    if (!state.isLoading && state.error == null && state.breeds.isEmpty()) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }

                binding.fominRecycler.visibility =
                    if (!state.isLoading && state.error == null && state.breeds.isNotEmpty()) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }

                adapter.submitList(state.breeds)
            }
        }
    }

    private fun navigateToDetail(breedId: String) {
        val detailFragment = BreedDetailFragment.newInstance(breedId)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(com.example.libs.arch.R.id.fragment_container, detailFragment)
            .addToBackStack(null)
            .commit()
    }
}

