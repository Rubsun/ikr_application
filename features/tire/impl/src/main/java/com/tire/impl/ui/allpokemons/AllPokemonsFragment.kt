package com.tire.impl.ui.allpokemons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.tire.impl.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.recyclerview.widget.LinearLayoutManager

internal class AllPokemonsFragment : Fragment() {

    private val viewModel: AllPokemonsViewModel by viewModels()
    private val adapter = AllPokemonsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_all_pokemons, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recycler: RecyclerView = view.findViewById(R.id.recyclerPokemons)
        val buttonBack: ImageButton = view.findViewById(R.id.buttonBack)
        val editSearch: EditText = view.findViewById(R.id.editSearch)
        recycler.adapter = adapter.apply {
            stateRestorationPolicy =
                RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }
        recycler.layoutManager = LinearLayoutManager(requireContext())
        buttonBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        editSearch.doOnTextChanged { text, _, _, _ ->
            viewModel.onQueryChanged(text?.toString().orEmpty())
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.pokemons.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }
}
