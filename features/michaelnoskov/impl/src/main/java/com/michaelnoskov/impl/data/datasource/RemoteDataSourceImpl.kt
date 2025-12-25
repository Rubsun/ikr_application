package com.michaelnoskov.impl.data.datasource

import com.michaelnoskov.api.domain.model.ChartData
import com.michaelnoskov.api.domain.model.FilteredItem
import com.michaelnoskov.network.api.ColorSquareApiClient
import com.michaelnoskov.network.api.WeatherApiClient

internal class RemoteDataSourceImpl(
    private val colorSquareApiClient: ColorSquareApiClient,
    private val weatherApiClient: WeatherApiClient
) : RemoteDataSource {

    override suspend fun fetchChartData(): Result<List<ChartData>> {
        return colorSquareApiClient.getChartData()
    }

    override suspend fun fetchRemoteItems(): Result<List<FilteredItem>> {
        return colorSquareApiClient.getRemoteItems()
    }

    override suspend fun sync(
        localItems: List<FilteredItem>,
        lastSync: Long
    ): Result<Unit> {
        return colorSquareApiClient.syncData(localItems, lastSync)
    }

    override suspend fun fetchWeatherTemperature(): Result<Double> {
        // Используем WeatherApiClient из network библиотеки
        // Координаты Сочи: 43.6028, 39.7342
        return weatherApiClient.getTemperature(43.6028, 39.7342)
    }
}

