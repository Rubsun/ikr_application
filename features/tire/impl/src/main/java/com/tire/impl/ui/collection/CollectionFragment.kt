package com.tire.impl.ui.collection

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tire.impl.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

internal class CollectionFragment : Fragment() {

    private val viewModel: CollectionViewModel by viewModels()

    private lateinit var recycler: RecyclerView
    private lateinit var progress: ProgressBar
    private lateinit var textStats: TextView

    private val adapter = CollectionAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return inflater.inflate(R.layout.fragment_collection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recycler = view.findViewById(R.id.recyclerCollection)
        progress = view.findViewById(R.id.progress)
        textStats = view.findViewById(R.id.textStats)

        recycler.layoutManager = GridLayoutManager(requireContext(), 3)
        recycler.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                Log.d("CollectionUI", "pokemons=${state.pokemons.size}")
                progress.visibility = if (state.isLoading) View.VISIBLE else View.GONE
                adapter.submitList(state.pokemons)

                state.stats?.let { stats ->
                    textStats.text = "Owned: ${stats.totalOwned}/${stats.totalPossible} (${stats.completionPercentage.toInt()}%)"
                }
            }
        }
    }
}
