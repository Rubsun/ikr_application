package com.example.ikr_application.zagora.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import dog.dohodyaga.gaf.databinding.FragmentDogBinding
import com.example.ikr_application.zagora.domain.DogImageModel

class FragmentDog : Fragment() {

    private var _binding: FragmentDogBinding? = null
    private val binding get() = _binding

    private val viewModel: MyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDogBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.dogImage.observe(viewLifecycleOwner) { dogImage ->
            dogImage?.let {
                showDogImage(it)
            }
        }

        viewModel.loadDogImage()
    }

    private fun showDogImage(dogImage: DogImageModel) {
        try {
            binding?.dogImageView?.visibility = View.VISIBLE

            binding?.let {
                Glide.with(this)
                    .load(dogImage.imageUrl)
                    .into(it.dogImageView)
            }
        } catch (e: IllegalStateException) {
            // Ignore if the fragment is already gone
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}