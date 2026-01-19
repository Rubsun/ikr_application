package com.telegin.impl.ui

import androidx.lifecycle.ViewModel
import com.telegin.api.domain.usecases.GetWeatherUseCase
import com.telegin.api.ui.models.WeatherDisplayModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flatMapLatest

internal class TeleginViewModel(
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {
    private val cityFlow = MutableStateFlow("")
    
    init {
        // При инициализации пытаемся загрузить из кэша
        cityFlow.value = ""
    }
    
    private val state = cityFlow
        .flatMapLatest { city ->
            flow {
                if (city.isBlank()) {
                    // При пустом городе пытаемся получить из кэша
                    val result = getWeatherUseCase("")
                    emit(
                        State(
                            data = result.getOrNull()?.let { weather ->
                                WeatherDisplayModel(
                                    city = weather.city,
                                    temperature = "${weather.temperature.toInt()}°C",
                                    description = weather.description,
                                    humidity = "Влажность: ${weather.humidity}%",
                                    windSpeed = "Ветер: ${weather.windSpeed} м/с",
                                    iconUrl = weather.iconUrl
                                )
                            },
                            isLoading = false,
                            error = result.exceptionOrNull()
                        )
                    )
                } else {
                    val result = getWeatherUseCase(city)
                    emit(
                        State(
                            data = result.getOrNull()?.let { weather ->
                                WeatherDisplayModel(
                                    city = weather.city,
                                    temperature = "${weather.temperature.toInt()}°C",
                                    description = weather.description,
                                    humidity = "Влажность: ${weather.humidity}%",
                                    windSpeed = "Ветер: ${weather.windSpeed} м/с",
                                    iconUrl = weather.iconUrl
                                )
                            },
                            isLoading = false,
                            error = result.exceptionOrNull()
                        )
                    )
                }
            }
        }

    fun state(): Flow<State> {
        return state
    }

    fun search(city: String) {
        cityFlow.value = city
    }

    data class State(
        val data: WeatherDisplayModel? = null,
        val isLoading: Boolean = false,
        val error: Throwable? = null,
    )
}

