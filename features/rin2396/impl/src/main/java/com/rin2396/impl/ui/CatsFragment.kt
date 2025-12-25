package com.rin2396.impl.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.rin2396.impl.databinding.FragmentCatsBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.java.KoinJavaComponent.inject

internal class CatsFragment : Fragment() {

    private var _binding: FragmentCatsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CatsViewModel by viewModel()
    private val imageLoader: ImageLoader by inject(ImageLoader::class.java)
    private lateinit var adapter: CatsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        adapter = CatsAdapter(imageLoader)
        binding.catsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.catsRecyclerView.adapter = adapter

        binding.addCatButton.setOnClickListener {
            val tag = binding.tagInput.text.toString().ifEmpty { "random" }
            viewModel.addCat(tag)
            binding.tagInput.text.clear()
        }

        binding.searchInput.addTextChangedListener { text ->
            viewModel.updateSearchQuery(text.toString())
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    adapter.submitList(state.cats)
                    binding.loadingProgressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
                    
                    if (state.error != null) {
                        binding.errorTextView.text = state.error
                        binding.errorTextView.visibility = View.VISIBLE
                    } else {
                        binding.errorTextView.visibility = View.GONE
                    }

                    if (state.cats.isEmpty() && !state.isLoading) {
                        binding.emptyStateTextView.visibility = View.VISIBLE
                    } else {
                        binding.emptyStateTextView.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
