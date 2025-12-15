package com.example.ikr_application.nfirex.data

import com.example.ikr_application.nfirex.data.models.EmojiDto
import retrofit2.http.GET
import retrofit2.http.Query

internal interface EmojiService {
    @GET("api/search")
    suspend fun searchEmojis(@Query("q") query: String): List<EmojiDto>
}

