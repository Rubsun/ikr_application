package com.denisova.impl.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denisova.impl.R
import com.example.injector.inject
import com.imageloader.api.ImageLoader
import kotlinx.coroutines.launch

internal class DenisovaFragment : Fragment() {
    private val viewModel by viewModels<DenisovaViewModel>()
    private val imageLoader: ImageLoader by inject()
    private val adapter by lazy { WeatherLocationAdapter(imageLoader) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.content_denisova_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<RecyclerView>(R.id.recycler).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@DenisovaFragment.adapter
        }
        val statusText = view.findViewById<TextView>(R.id.status)

        val search = view.findViewById<EditText>(R.id.search)
        search.doOnTextChanged { text, _, _, _ ->
            viewModel.onQueryChanged(text?.toString().orEmpty())
        }

        val cityInput = view.findViewById<EditText>(R.id.cityInput)
        view.findViewById<Button>(R.id.addButton).setOnClickListener {
            val cityName = cityInput.text?.toString().orEmpty()
            if (cityName.isNotBlank()) {
                viewModel.onAddCity(cityName = cityName)
                cityInput.text?.clear()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    adapter.submitList(state.locations)
                    statusText.text = when {
                        state.isLoading -> getString(R.string.denisova_status_loading)
                        state.error != null -> getString(R.string.denisova_status_error_pattern, state.error)
                        else -> getString(R.string.denisova_status_count_pattern, state.locations.size)
                    }
                }
            }
        }
    }
}
