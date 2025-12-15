package com.example.ikr_application.stupishin.ui

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ikr_application.R
import com.example.ikr_application.databinding.ContentStupishinAnimeBinding
import com.example.ikr_application.stupishin.ui.adapters.StuAnimeAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class StuAnimeFragment : Fragment(R.layout.content_stupishin_anime) {
    private val viewModel by viewModels<StuAnimeViewModel>()
    private val adapter = StuAnimeAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = ContentStupishinAnimeBinding.bind(view)

        binding.stuRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.stuRecycler.adapter = adapter

        binding.stuSearch.addTextChangedListener { editable ->
            viewModel.onQueryChanged(editable?.toString().orEmpty())
        }

        binding.stuRetry.setOnClickListener {
            viewModel.retry()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                binding.stuLoadingContainer.visibility = if (state.isLoading) View.VISIBLE else View.GONE
                binding.stuErrorContainer.visibility = if (state.error != null) View.VISIBLE else View.GONE
                binding.stuError.text = state.error

                binding.stuEmpty.visibility =
                    if (!state.isLoading && state.error == null && state.items.isEmpty()) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }

                binding.stuRecycler.visibility =
                    if (!state.isLoading && state.error == null && state.items.isNotEmpty()) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }

                adapter.submitList(state.items)
            }
        }
    }
}
