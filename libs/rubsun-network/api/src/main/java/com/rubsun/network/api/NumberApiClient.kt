package com.rubsun.network.api

import com.rubsun.network.api.models.PostDto

interface NumberApiClient {
    suspend fun getPostById(id: Int): PostDto?
    suspend fun getRandomPost(): PostDto?
}
