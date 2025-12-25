package com.tire.impl.ui.casepreview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.imageloader.api.ImageLoader
import com.example.injector.inject
import com.tire.impl.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

internal class CasePreviewFragment : Fragment() {

    companion object {
        private const val ARG_CASE_ID = "arg_case_id"

        fun newInstance(caseId: String): CasePreviewFragment {
            val f = CasePreviewFragment()
            f.arguments = Bundle().apply {
                putString(ARG_CASE_ID, caseId)
            }
            return f
        }
    }

    private lateinit var viewModel: CasePreviewViewModel
    private val adapter = CasePreviewAdapter()
    private val imageLoader: ImageLoader by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val caseId = requireArguments().getString(ARG_CASE_ID)
            ?: error("Case id is required")

        val factory = CasePreviewViewModelFactory(caseId)
        viewModel = viewModels<CasePreviewViewModel> { factory }.value
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return inflater.inflate(R.layout.fragment_case_preview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recycler: RecyclerView = view.findViewById(R.id.recyclerPreview)
        recycler.layoutManager = GridLayoutManager(requireContext(), 2)
        recycler.adapter = adapter

        val progress: ProgressBar = view.findViewById(R.id.progress)
        val buttonBack: ImageButton = view.findViewById(R.id.buttonBack)
        val textTitle: TextView = view.findViewById(R.id.textTitle)
        val textCaseName: TextView = view.findViewById(R.id.textCaseName)
        val textCaseInfo: TextView = view.findViewById(R.id.textCaseInfo)
        val imageCase: ImageView = view.findViewById(R.id.imageCase)

        buttonBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                progress.visibility = if (state.isLoading) View.VISIBLE else View.GONE
                adapter.submitList(state.pokemons)
                textTitle.text = state.caseName
                textCaseName.text = state.caseName
                textCaseInfo.text = state.caseInfo
                state.caseImageUrl?.let { url ->
                    imageLoader.load(imageCase, url)
                }
            }
        }
    }
}
