package com.egorik4.impl.ui

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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.egorik4.impl.R
import com.example.injector.inject
import com.imageloader.api.ImageLoader
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class Egorik4Fragment : Fragment() {
    private val viewModel: Egorik4ViewModel by viewModel()
    private val imageLoader: ImageLoader by inject()
    private val adapter by lazy { BookAdapter(imageLoader) }

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
