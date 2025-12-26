package com.fomin.impl.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.injector.inject
import com.fomin.impl.R
import com.fomin.impl.databinding.ContentFominBreedDetailBinding
import com.fomin.impl.ui.adapters.CatImageAdapter
import com.imageloader.api.ImageLoader
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

internal class BreedDetailFragment : Fragment(R.layout.content_fomin_breed_detail) {
    
    private val viewModel by viewModels<BreedDetailViewModel>()
    private val imageLoader: ImageLoader by inject()
    private val imageAdapter by lazy { CatImageAdapter(imageLoader) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = ContentFominBreedDetailBinding.bind(view)
        val breedId = arguments?.getString(ARG_BREED_ID) ?: return

        binding.breedImagesRecycler.layoutManager = 
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.breedImagesRecycler.adapter = imageAdapter

        binding.backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        viewModel.loadBreed(breedId)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                binding.breedLoadingContainer.visibility = 
                    if (state.isLoading) View.VISIBLE else View.GONE
                binding.breedErrorContainer.visibility = 
                    if (state.error != null) View.VISIBLE else View.GONE
                binding.breedError.text = state.error

                state.breed?.let { breed ->
                    binding.breedContent.visibility = View.VISIBLE
                    binding.breedName.text = breed.name
                    binding.breedDescription.text = breed.description ?: ""
                    binding.breedTemperament.text = breed.temperament ?: ""
                    binding.breedOrigin.text = breed.origin ?: ""
                    binding.breedLifeSpan.text = breed.lifeSpan ?: ""
                    binding.breedWeight.text = breed.weight?.metric?.let { "$it kg" } ?: 
                        breed.weight?.imperial?.let { "$it lbs" } ?: ""

                    val imageUrl = breed.imageUrl
                    if (imageUrl != null) {
                        imageLoader.load(binding.breedMainImage, imageUrl)
                    }

                    imageAdapter.submitList(state.images)
                } ?: run {
                    binding.breedContent.visibility = View.GONE
                }
            }
        }
    }

    companion object {
        private const val ARG_BREED_ID = "breed_id"

        fun newInstance(breedId: String): BreedDetailFragment {
            return BreedDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_BREED_ID, breedId)
                }
            }
        }
    }
}

