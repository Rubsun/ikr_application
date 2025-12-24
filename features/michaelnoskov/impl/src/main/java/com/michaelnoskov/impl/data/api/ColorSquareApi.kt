package com.michaelnoskov.impl.data.api

import com.michaelnoskov.impl.data.api.model.ChartDataResponse
import com.michaelnoskov.impl.data.api.model.RemoteItemResponse
import com.michaelnoskov.impl.data.api.model.SyncRequest
import com.michaelnoskov.impl.data.api.model.SyncResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

internal interface ColorSquareApi {
    @GET("chart/data")
    suspend fun getChartData(): Response<ChartDataResponse>

    @GET("items")
    suspend fun getRemoteItems(): Response<List<RemoteItemResponse>>

    @POST("sync")
    suspend fun syncData(@Body syncRequest: SyncRequest): Response<SyncResponse>
}

