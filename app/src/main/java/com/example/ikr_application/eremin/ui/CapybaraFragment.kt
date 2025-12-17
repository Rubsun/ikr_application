package com.example.ikr_application.eremin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ikr_application.R
import com.google.android.material.switchmaterial.SwitchMaterial
import kotlinx.coroutines.launch

class CapybaraFragment : Fragment() {

    private val viewModel: CapybaraViewModel by viewModels()
    private lateinit var capybaraAdapter: CapybaraAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var columnSwitch: SwitchMaterial
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_capybara, container, false)
        recyclerView = view.findViewById(R.id.capybara_recycler_view)
        columnSwitch = view.findViewById(R.id.column_switch)
        progressBar = view.findViewById(R.id.progress_bar)

        capybaraAdapter = CapybaraAdapter(mutableListOf())
        recyclerView.adapter = capybaraAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager
                val lastVisibleItemPosition = when (layoutManager) {
                    is LinearLayoutManager -> layoutManager.findLastVisibleItemPosition()
                    is GridLayoutManager -> layoutManager.findLastVisibleItemPosition()
                    else -> 0
                }
                val totalItemCount = layoutManager?.itemCount ?: 0
                if (lastVisibleItemPosition == totalItemCount - 1 && totalItemCount > 0 && !viewModel.uiState.value.isLoading) {
                    viewModel.loadNextPage()
                }
            }
        })

        columnSwitch.setOnCheckedChangeListener { _, isChecked ->
            val layoutManager = if (isChecked) {
                GridLayoutManager(context, 2)
            } else {
                LinearLayoutManager(context)
            }
            recyclerView.layoutManager = layoutManager
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    progressBar.isVisible = state.isLoading
                    if (state.error != null) {
                        Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
                    }
                    capybaraAdapter.setCapybaras(state.capybaras)
                }
            }
        }

        if (viewModel.uiState.value.capybaras.isEmpty()) {
            viewModel.loadNextPage()
        }

        return view
    }
}
