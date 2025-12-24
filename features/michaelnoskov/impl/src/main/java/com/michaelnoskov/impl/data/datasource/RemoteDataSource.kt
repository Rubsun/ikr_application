package com.michaelnoskov.impl.data.datasource

import com.michaelnoskov.api.domain.model.ChartData
import com.michaelnoskov.api.domain.model.FilteredItem
import com.michaelnoskov.network.api.WeatherApiClient

internal interface RemoteDataSource {
    suspend fun fetchChartData(): Result<List<ChartData>>
    suspend fun fetchRemoteItems(): Result<List<FilteredItem>>
    suspend fun sync(localItems: List<FilteredItem>, lastSync: Long): Result<Unit>
    suspend fun fetchWeatherTemperature(): Result<Double>
}

