package com.example.ikr_application.zagora.ui

import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.ikr_application.databinding.FragmentDogBinding
import com.example.ikr_application.zagora.domain.DogImageModel
import java.util.concurrent.Executors

class FragmentDog : Fragment() {

    private var _binding: FragmentDogBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDogBinding.inflate(inflater, container, false)
        return binding.root
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
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())

        executor.execute {
            try {
                val inputStream = java.net.URL(dogImage.imageUrl).openStream()
                val bitmap = BitmapFactory.decodeStream(inputStream)
                handler.post {
                    binding.dogImageView.setImageBitmap(bitmap)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}