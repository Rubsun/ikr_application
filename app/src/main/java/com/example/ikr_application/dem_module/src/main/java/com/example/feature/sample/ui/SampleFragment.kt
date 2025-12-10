package com.example.ikr_application.dem_module.ui
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.ikr_application.databinding.FragmentSampleBinding
import com.example.ikr_application.dem_module.domain.SampleItem
import kotlinx.coroutines.launch

class SampleFragment : Fragment() {

    private var _binding: com.example.feature.sample.databinding.FragmentSampleBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SampleViewModel by viewModels()

    private lateinit var adapter: SampleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = com.example.feature.sample.databinding.FragmentSampleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupListeners()
        observeUiState()
    }

    private fun setupRecyclerView() {
        adapter = SampleAdapter(onItemClick = ::onItemClicked)
        binding.recyclerView.adapter = adapter
    }

    private fun setupListeners() {
        binding.btnRetry.setOnClickListener {
            viewModel.loadItems()
        }

        binding.btnRefresh.setOnClickListener {
            viewModel.loadItems()
        }

        binding.etSearch.setOnEditorActionListener { textView, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = textView.text.toString()
                viewModel.searchItems(query)
                true
            } else {
                false
            }
        }
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { state ->
                    renderUiState(state)
                }
        }
    }

    private fun renderUiState(state: SampleUiState) {
        when (state) {
            SampleUiState.Loading -> showLoading()
            is SampleUiState.Success -> showSuccess(state.items)
            is SampleUiState.Error -> showError(state.message)
            SampleUiState.Empty -> showEmpty()
        }
    }

    private fun showLoading() {
        binding.apply {
            com.example.feature.sample.databinding.FragmentSampleBinding.progressBar.visibility = View.VISIBLE
            com.example.feature.sample.databinding.FragmentSampleBinding.recyclerView.visibility = View.GONE
            com.example.feature.sample.databinding.FragmentSampleBinding.errorContainer.visibility = View.GONE
            com.example.feature.sample.databinding.FragmentSampleBinding.emptyContainer.visibility = View.GONE
        }
    }

    private fun showSuccess(items: List<SampleItem>) {
        binding.apply {
            com.example.feature.sample.databinding.FragmentSampleBinding.progressBar.visibility = View.GONE
            com.example.feature.sample.databinding.FragmentSampleBinding.recyclerView.visibility = View.VISIBLE
            com.example.feature.sample.databinding.FragmentSampleBinding.errorContainer.visibility = View.GONE
            com.example.feature.sample.databinding.FragmentSampleBinding.emptyContainer.visibility = View.GONE
            adapter.submitList(items)
        }
    }

    private fun showError(message: String) {
        binding.apply {
            com.example.feature.sample.databinding.FragmentSampleBinding.progressBar.visibility = View.GONE
            com.example.feature.sample.databinding.FragmentSampleBinding.recyclerView.visibility = View.GONE
            com.example.feature.sample.databinding.FragmentSampleBinding.errorContainer.visibility = View.VISIBLE
            com.example.feature.sample.databinding.FragmentSampleBinding.emptyContainer.visibility = View.GONE
            com.example.feature.sample.databinding.FragmentSampleBinding.tvError.text = message
        }
    }

    private fun showEmpty() {
        binding.apply {
            com.example.feature.sample.databinding.FragmentSampleBinding.progressBar.visibility = View.GONE
            com.example.feature.sample.databinding.FragmentSampleBinding.recyclerView.visibility = View.GONE
            com.example.feature.sample.databinding.FragmentSampleBinding.errorContainer.visibility = View.GONE
            com.example.feature.sample.databinding.FragmentSampleBinding.emptyContainer.visibility = View.VISIBLE
        }
    }

    private fun onItemClicked(item: SampleItem) {
        // Обработка клика по элементу
        // Например, открыть детальный экран
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
