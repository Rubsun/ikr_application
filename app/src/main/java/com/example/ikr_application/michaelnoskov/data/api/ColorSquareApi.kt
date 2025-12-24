package com.example.ikr_application.michaelnoskov.data.api

import com.example.ikr_application.michaelnoskov.data.api.model.ChartDataResponse
import com.example.ikr_application.michaelnoskov.data.api.model.RemoteItemResponse
import com.example.ikr_application.michaelnoskov.data.api.model.SyncRequest
import com.example.ikr_application.michaelnoskov.data.api.model.SyncResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ColorSquareApi {
    @GET("chart/data")
    suspend fun getChartData(): Response<ChartDataResponse>

    @GET("items")
    suspend fun getRemoteItems(): Response<List<RemoteItemResponse>>

    @POST("sync")
    suspend fun syncData(@Body syncRequest: SyncRequest): Response<SyncResponse>
}