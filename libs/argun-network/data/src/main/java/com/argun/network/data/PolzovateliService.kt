package com.argun.network.data

import com.argun.network.data.models.PolzovatelResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

internal interface PolzovateliService {
    @GET("users")
    suspend fun vsePolzovateli(): List<PolzovatelResponse>
    
    @GET("users/{id}")
    suspend fun polzovatelPoId(@Path("id") id: Int): PolzovatelResponse
    
    @POST("users")
    suspend fun sozdatPolzovatelya(@Body polzovatel: PolzovatelResponse): PolzovatelResponse
    
    @PUT("users/{id}")
    suspend fun obnovitPolzovatelya(
        @Path("id") id: Int,
        @Body polzovatel: PolzovatelResponse
    ): PolzovatelResponse
    
    @DELETE("users/{id}")
    suspend fun udalitPolzovatelya(@Path("id") id: Int)
}

