package com.rubsun.network.data

import com.rubsun.network.api.NumberApiClient
import com.rubsun.network.api.models.PostDto

internal class RetrofitNumberApiClient(
    private val service: NumberService
) : NumberApiClient {
    override suspend fun getPostById(id: Int): PostDto? {
        return try {
            val postId = id.coerceIn(1, 100)
            service.getPostById(postId)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun getRandomPost(): PostDto? {
        return try {
            val randomId = (1..100).random()
            service.getPostById(randomId)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
