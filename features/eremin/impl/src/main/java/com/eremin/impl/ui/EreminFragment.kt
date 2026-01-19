package com.eremin.impl.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eremin.impl.R
import com.imageloader.api.ImageLoader
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class EreminFragment : Fragment() {

    private val viewModel: EreminViewModel by viewModel()
    private val imageLoader: ImageLoader by inject()
    private lateinit var searchEditText: EditText
    private lateinit var capybaraAdapter: CapybaraAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_eremin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.capybara_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        capybaraAdapter = CapybaraAdapter(emptyList(), imageLoader)
        recyclerView.adapter = capybaraAdapter

        searchEditText = view.findViewById(R.id.search_edit_text)

        var isUpdatingFromViewModel = false
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (!isUpdatingFromViewModel) {
                    viewModel.updateSearchQuery(s?.toString() ?: "")
                }
            }
        })

        viewModel.uiState
            .onEach { state ->
                if (searchEditText.text.toString() != state.searchQuery) {
                    isUpdatingFromViewModel = true
                    searchEditText.setText(state.searchQuery)
                    searchEditText.setSelection(state.searchQuery.length)
                    isUpdatingFromViewModel = false
                }
                capybaraAdapter.updateCapybaras(state.capybaras)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }
}
