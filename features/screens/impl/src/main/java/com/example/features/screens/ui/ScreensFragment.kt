package com.example.features.screens.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.features.screens.R
import com.example.features.screens.domain.Screen
import com.example.libs.arch.navigateTo
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

internal class ScreensFragment : Fragment(R.layout.content_navigation_screens), ScreensAdapter.Listener {
    private val viewModel: ScreenListViewModel by viewModels()
    private val screensAdapter = ScreensAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<RecyclerView>(R.id.recycler).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = screensAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state().collectLatest(::applyState)
        }
    }

    override fun onClicked(item: Screen) {
        navigateTo(item.name)
    }

    private fun applyState(state: ScreenListViewModel.State) {
        screensAdapter.submitList(state.items)

        view?.findViewById<TextView>(R.id.message)?.apply {
            text = state.message
            isVisible = !state.message.isNullOrBlank()
        }

        view?.findViewById<View>(R.id.pending)?.apply {
            isVisible = state.isLoading
        }
    }
}