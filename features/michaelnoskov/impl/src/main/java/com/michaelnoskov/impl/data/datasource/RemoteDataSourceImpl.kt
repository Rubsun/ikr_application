package com.michaelnoskov.impl.data.datasource

import com.michaelnoskov.api.domain.model.ChartData
import com.michaelnoskov.api.domain.model.FilteredItem
import com.michaelnoskov.impl.data.api.ColorSquareApi
import com.michaelnoskov.impl.data.api.model.SyncRequest
import com.michaelnoskov.impl.data.mapper.NetworkMapper
import retrofit2.Response

internal class RemoteDataSourceImpl(
    private val api: ColorSquareApi,
    private val networkMapper: NetworkMapper
) : RemoteDataSource {

    override suspend fun fetchChartData(): Result<List<ChartData>> {
        return try {
            val response = api.getChartData()
            handleResponse(response) { chartDataResponse ->
                networkMapper.mapChartData(chartDataResponse.data)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun fetchRemoteItems(): Result<List<FilteredItem>> {
        return try {
            val response = api.getRemoteItems()
            handleResponse(response) { remoteItems ->
                remoteItems.map { networkMapper.mapRemoteItem(it) }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun sync(
        localItems: List<FilteredItem>,
        lastSync: Long
    ): Result<Unit> {
        return try {
            val localItemsDto = localItems.map { networkMapper.mapToLocalItemDto(it) }
            val request = SyncRequest(
                localItems = localItemsDto,
                lastSync = lastSync
            )
            val response = api.syncData(request)
            handleResponse(response) { syncResponse ->
                // Здесь можно обработать ответ синхронизации
                Unit
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun <T, R> handleResponse(
        response: Response<T>,
        onSuccess: (T) -> R
    ): Result<R> {
        return if (response.isSuccessful) {
            response.body()?.let { body ->
                Result.success(onSuccess(body))
            } ?: Result.failure(Exception("Response body is null"))
        } else {
            Result.failure(Exception("HTTP ${response.code()}: ${response.message()}"))
        }
    }
}

