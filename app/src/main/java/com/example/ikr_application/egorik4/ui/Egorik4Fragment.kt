package com.example.ikr_application.egorik4.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ikr_application.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class Egorik4Fragment : Fragment() {
    private val viewModel by viewModels<Egorik4ViewModel>()
    private val adapter by lazy { BookAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.content_egorik4_books, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchEditText = view.findViewById<EditText>(R.id.search)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler)
        val progressBar = view.findViewById<ProgressBar>(R.id.progress)
        val errorText = view.findViewById<TextView>(R.id.error_text)

        searchEditText.addTextChangedListener { editable ->
            viewModel.search(editable.toString())
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@Egorik4Fragment.adapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state().collectLatest { state ->
                adapter.submitList(state.data)

                progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

                if (state.error != null) {
                    errorText.visibility = View.VISIBLE
                    errorText.text = state.error.message
                    Toast.makeText(
                        requireContext(),
                        state.error.message,
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    errorText.visibility = View.GONE
                }
            }
        }
    }
}

