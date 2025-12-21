package com.rin2396.impl.data

import com.rin2396.impl.data.models.RinInfo
import retrofit2.http.GET
import retrofit2.http.Query

internal interface RinService {
    @GET("api/search")
    suspend fun searchTimes(@Query("q") query: String): List<RinInfo>
}

