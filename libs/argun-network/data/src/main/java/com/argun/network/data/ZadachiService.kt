package com.argun.network.data

import com.argun.network.data.models.ZadachaResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

internal interface ZadachiService {
    @GET("todos")
    suspend fun vseZadachi(): List<ZadachaResponse>
    
    @GET("todos/{id}")
    suspend fun zadachaPoId(@Path("id") id: Int): ZadachaResponse
    
    @POST("todos")
    suspend fun sozdatZadachu(@Body zadacha: ZadachaResponse): ZadachaResponse
    
    @PUT("todos/{id}")
    suspend fun obnovitZadachu(
        @Path("id") id: Int,
        @Body zadacha: ZadachaResponse
    ): ZadachaResponse
    
    @DELETE("todos/{id}")
    suspend fun udalitZadachu(@Path("id") id: Int)
}

