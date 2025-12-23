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
import com.eremin.api.domain.models.Capybara
import com.eremin.impl.R
import com.eremin.impl.domain.GetCapybarasUseCase
import com.example.primitivestorage.api.PrimitiveStorage
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named

internal class EreminFragment : Fragment() {

    private val getCapybarasUseCase: GetCapybarasUseCase by inject()
    private val primitiveStorage: PrimitiveStorage<String> by inject(named("eremin_storage"))
    private lateinit var searchEditText: EditText
    private lateinit var capybaraAdapter: CapybaraAdapter
    private var allCapybaras: List<Capybara> = emptyList()

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
        capybaraAdapter = CapybaraAdapter(emptyList())
        recyclerView.adapter = capybaraAdapter

        searchEditText = view.findViewById(R.id.search_edit_text)
        viewLifecycleOwner.lifecycleScope.launch {
            searchEditText.setText(primitiveStorage.get().first() ?: "")
        }

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterCapybaras(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                viewLifecycleOwner.lifecycleScope.launch {
                    primitiveStorage.put(s.toString())
                }
            }
        })

        viewLifecycleOwner.lifecycleScope.launch {
            getCapybarasUseCase.execute(0, 100).collect { capybaras ->
                allCapybaras = capybaras
                filterCapybaras(searchEditText.text.toString())
            }
        }
    }

    private fun filterCapybaras(query: String) {
        val filteredList = if (query.isEmpty()) {
            allCapybaras
        } else {
            allCapybaras.filter { it.alt.contains(query, ignoreCase = true) }
        }
        capybaraAdapter.updateCapybaras(filteredList)
    }
}
