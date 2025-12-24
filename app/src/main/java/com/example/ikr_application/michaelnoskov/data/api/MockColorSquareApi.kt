package com.example.ikr_application.michaelnoskov.data.api

import com.example.ikr_application.michaelnoskov.data.api.model.ChartDataResponse
import com.example.ikr_application.michaelnoskov.data.api.model.RemoteItemResponse
import com.example.ikr_application.michaelnoskov.data.api.model.SyncRequest
import com.example.ikr_application.michaelnoskov.data.api.model.SyncResponse
import retrofit2.Response

class MockColorSquareApi : ColorSquareApi {
    override suspend fun getChartData(): Response<ChartDataResponse> {
        return Response.success(
            ChartDataResponse(
                data = listOf(
                    com.example.ikr_application.michaelnoskov.data.api.model.ChartDataDto(
                        label = "Красный",
                        value = 30f,
                        colorHex = "#FF0000"
                    ),
                    com.example.ikr_application.michaelnoskov.data.api.model.ChartDataDto(
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