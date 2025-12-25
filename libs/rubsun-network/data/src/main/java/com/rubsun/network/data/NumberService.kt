package com.rubsun.network.data

import com.rubsun.network.api.models.PostDto
import retrofit2.http.GET
import retrofit2.http.Path

internal interface NumberService {
    @GET("posts/{id}")
    suspend fun getPostById(@Path("id") id: Int): PostDto
    
    @GET("posts")
    suspend fun getAllPosts(): List<PostDto>
}
