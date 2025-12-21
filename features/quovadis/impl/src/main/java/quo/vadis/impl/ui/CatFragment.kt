package quo.vadis.impl.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil3.load
import kotlinx.coroutines.launch
import quo.vadis.api.usecases.ApiBaseUrl
import quo.vadis.impl.R
import quo.vadis.impl.databinding.RoganovCatFragmentBinding

class CatFragment : Fragment() {

    private var _binding: RoganovCatFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<CatViewModel>()

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

        setupUi()
        collectState()

        viewModel.loadRandomCat(null)
    }

    private fun setupUi() {
        binding.phraseInput.addTextChangedListener { text ->
            val phrase = text?.toString().orEmpty()
            viewModel.onFilterChanged(phrase)
        }

        binding.loadButton.setOnClickListener {
            val phrase = binding.phraseInput.text?.toString()
                ?.takeIf { it.isNotBlank() }
            viewModel.loadRandomCat(phrase)
        }

        binding.catAasBtnApi.setOnClickListener {
            viewModel.onApiChanged(ApiBaseUrl.CatAaS)
            updateApiButtonStates(ApiBaseUrl.CatAaS)
        }

        binding.httpCatsBtnApi.setOnClickListener {
            viewModel.onApiChanged(ApiBaseUrl.HttpCats)
            updateApiButtonStates(ApiBaseUrl.HttpCats)
        }
    }

    private fun updateApiButtonStates(selectedApi: ApiBaseUrl) {
        val isCatAaS = selectedApi == ApiBaseUrl.CatAaS

        binding.catAasBtnApi.apply {
            isEnabled = !isCatAaS
        }

        binding.httpCatsBtnApi.apply {
            isEnabled = isCatAaS
        }

        with (binding){
            if (isCatAaS) {
                whatCatSays.text = getString(R.string.what_does_the_cat_say)
                phraseInput.hint = getString(R.string.cat_says)
            } else {
                whatCatSays.text = getString(R.string.what_http_code_does_the_cat_send)
                phraseInput.hint = getString(R.string.cat_responds)
            }
        }
    }

    private fun collectState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    updateApiButtonStates(state.api)
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
                    (cat.phrase?.contains(state.filter, ignoreCase = true) == true) ||
                    cat.fetchedFrom.name.contains(state.api.name, ignoreCase = true)
        }

        val cat = filteredCats.firstOrNull()
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