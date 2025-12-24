package com.example.ikr_application.michaelnoskov.data.datasource

import com.example.ikr_application.michaelnoskov.domain.model.ChartData
import com.example.ikr_application.michaelnoskov.domain.model.FilteredItem

interface RemoteDataSource {
    suspend fun fetchChartData(): Result<List<ChartData>>
    suspend fun fetchRemoteItems(): Result<List<FilteredItem>>
    suspend fun sync(localItems: List<FilteredItem>, lastSync: Long): Result<Unit>
}