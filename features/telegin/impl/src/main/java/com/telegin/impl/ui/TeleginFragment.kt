package com.telegin.impl.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.telegin.impl.R
import com.example.injector.inject
import com.imageloader.api.ImageLoader
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class TeleginFragment : Fragment() {
    private val viewModel: TeleginViewModel by viewModel()
    private val imageLoader: ImageLoader by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.content_telegin_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cityEditText = view.findViewById<EditText>(R.id.city_input)
        val cityNameText = view.findViewById<TextView>(R.id.city_name)
        val temperatureText = view.findViewById<TextView>(R.id.temperature)
        val descriptionText = view.findViewById<TextView>(R.id.description)
        val humidityText = view.findViewById<TextView>(R.id.humidity)
        val windSpeedText = view.findViewById<TextView>(R.id.wind_speed)
        val weatherIcon = view.findViewById<ImageView>(R.id.weather_icon)
        val progressBar = view.findViewById<ProgressBar>(R.id.progress)
        val errorText = view.findViewById<TextView>(R.id.error_text)
        val weatherContainer = view.findViewById<View>(R.id.weather_container)

        cityEditText.addTextChangedListener { editable ->
            viewModel.search(editable.toString())
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state().collectLatest { state ->
                if (state.isLoading) {
                    progressBar.visibility = View.VISIBLE
                    weatherContainer.visibility = View.GONE
                } else {
                    progressBar.visibility = View.GONE
                }

                if (state.error != null) {
                    errorText.visibility = View.VISIBLE
                    errorText.text = state.error.message
                    weatherContainer.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        state.error.message,
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    errorText.visibility = View.GONE
                }

                state.data?.let { weather ->
                    weatherContainer.visibility = View.VISIBLE
                    cityNameText.text = weather.city
                    temperatureText.text = weather.temperature
                    descriptionText.text = weather.description
                    humidityText.text = weather.humidity
                    windSpeedText.text = weather.windSpeed

                    weather.iconUrl?.let { iconUrl ->
                        imageLoader.load(weatherIcon, iconUrl)
                    }
                } ?: run {
                    weatherContainer.visibility = View.GONE
                }
            }
        }
    }
}

