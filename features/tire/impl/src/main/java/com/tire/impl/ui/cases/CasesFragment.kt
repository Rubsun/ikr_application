package com.tire.impl.ui.cases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.tire.impl.R
import com.tire.impl.ui.allpokemons.AllPokemonsFragment
import com.tire.impl.ui.casepreview.CasePreviewFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

internal class CasesFragment : Fragment() {

    private val viewModel: CasesViewModel by viewModels()

    private lateinit var recyclerCases: RecyclerView
    private lateinit var progress: ProgressBar

    private val adapter = CasesAdapter(
        onOpenClick = { case -> viewModel.openCase(case.id) },
        onPreviewClick = { case -> openPreview(case.id) }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return inflater.inflate(R.layout.fragment_cases, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerCases = view.findViewById(R.id.recyclerCases)
        progress = view.findViewById(R.id.progress)
        val buttonAllPokemons: Button = view.findViewById(R.id.buttonAllPokemons)
        recyclerCases.adapter = adapter
        buttonAllPokemons.setOnClickListener {
            openAllPokemons()
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                progress.visibility = if (state.isLoading) View.VISIBLE else View.GONE
                adapter.submitList(state.cases)
                // здесь можно еще показать результат открытия кейса
            }
        }
    }

    private fun openPreview(caseId: String) {
        val fragment = CasePreviewFragment.newInstance(caseId)
        parentFragmentManager.beginTransaction()
            .replace(R.id.tireChildContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun openAllPokemons() {
        val fragment = AllPokemonsFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.tireChildContainer, fragment)
            .addToBackStack("all_pokemons")
            .commit()
    }
}
