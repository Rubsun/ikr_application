package com.example.ikr_application.quovadis.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil3.load
import com.example.ikr_application.databinding.RoganovCatFragmentBinding
import com.example.ikr_application.quovadis.data.CatRepository
import com.example.ikr_application.quovadis.domain.GetCatUseCase
import kotlinx.coroutines.launch

class CatFragment : Fragment() {

    private var _binding: RoganovCatFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CatViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RoganovCatFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = CatRepository()
        val useCase = GetCatUseCase(repository)
        viewModel = CatViewModel(useCase)

        setupUi()
        collectState()

        viewModel.loadRandomCat(null)
    }

    private fun setupUi() {
        // текст, который кот скажет (передаём в loadRandomCat)
        binding.phraseInput.addTextChangedListener { text ->
            val phrase = text?.toString().orEmpty()
            // используем как фильтр (по имени/фразе) — это состояние
            viewModel.onFilterChanged(phrase)
        }

        binding.loadButton.setOnClickListener {
            val phrase = binding.phraseInput.text?.toString()
                ?.takeIf { it.isNotBlank() }
            viewModel.loadRandomCat(phrase)
        }
    }

    private fun collectState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    render(state)
                }
            }
        }
    }

    private fun render(state: CatUiState) {
        binding.progressBar.visibility =
            if (state.isLoading) View.VISIBLE else View.GONE

        val filteredCats = state.cats.filter { cat ->
            state.filter.isBlank() ||
                    cat.name.contains(state.filter, ignoreCase = true) ||
                    (cat.phrase?.contains(state.filter, ignoreCase = true) == true)
        }

        val cat = filteredCats.lastOrNull()
        if (cat != null) {
            binding.catNameTv.text = cat.name
            if (cat.phrase != null) {
                binding.phraseChip.text = cat.phrase
                binding.phraseChip.visibility = View.VISIBLE
            } else {
                binding.phraseChip.visibility = View.GONE
            }

            if (cat.imageUrl != null) {
                binding.catImageView.visibility = View.VISIBLE
                binding.catImageView.load(cat.imageUrl)
            } else {
                binding.catImageView.visibility = View.GONE
            }
        } else {
            binding.catNameTv.text = "Нет котов"
            binding.phraseChip.visibility = View.GONE
            binding.catImageView.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}