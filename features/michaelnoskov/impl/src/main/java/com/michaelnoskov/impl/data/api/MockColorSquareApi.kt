package com.michaelnoskov.impl.data.api

import com.michaelnoskov.impl.data.api.model.ChartDataDto
import com.michaelnoskov.impl.data.api.model.ChartDataResponse
import com.michaelnoskov.impl.data.api.model.RemoteItemResponse
import com.michaelnoskov.impl.data.api.model.SyncRequest
import com.michaelnoskov.impl.data.api.model.SyncResponse
import retrofit2.Response

internal class MockColorSquareApi : ColorSquareApi {
    override suspend fun getChartData(): Response<ChartDataResponse> {
        return Response.success(
            ChartDataResponse(
                data = listOf(
                    ChartDataDto(
                        label = "Красный",
                        value = 30f,
                        colorHex = "#FF0000"
                    ),
                    ChartDataDto(
                        label = "Зеленый",
                        value = 25f,
                        colorHex = "#00FF00"
                    )
                )
            )
        )
    }

    override suspend fun getRemoteItems(): Response<List<RemoteItemResponse>> {
        return Response.success(emptyList())
    }

    override suspend fun syncData(syncRequest: SyncRequest): Response<SyncResponse> {
        return Response.success(
            SyncResponse(
                remoteItems = emptyList(),
                syncTimestamp = System.currentTimeMillis()
            )
        )
    }
}

