package com.dyatlova.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

object DestinationsRemoteSourceFactory {
    fun create(
        baseUrl: String = DEFAULT_BASE_URL,
        client: OkHttpClient? = null,
    ): DestinationsRemoteSource {
        val retrofit = createRetrofit(baseUrl, client)
        val service = retrofit.create(RecipesService::class.java)
        return RetrofitDestinationsRemoteSource(service)
    }

    private fun createRetrofit(
        baseUrl: String,
        client: OkHttpClient?,
    ): Retrofit {
        val logger = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
        val baseClient = client ?: OkHttpClient.Builder().build()
        val retrofitClient = baseClient.newBuilder()
            .addInterceptor(logger)
            .build()

        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(retrofitClient)
            .addConverterFactory(
                Json {
                    ignoreUnknownKeys = true
                }.asConverterFactory(contentType)
            )
            .build()
    }
}

private class RetrofitDestinationsRemoteSource(
    private val service: RecipesService,
) : DestinationsRemoteSource {
    override suspend fun loadDestinations(limit: Int): List<RemoteDestination> {
        val response = service.getRecipes(limit)
        return response.recipes.map { it.toRemote() }
    }
}

private interface RecipesService {
    @GET("recipes")
    suspend fun getRecipes(@Query("limit") limit: Int): RecipesResponse
}

@Serializable
private data class RecipesResponse(
    @SerialName("recipes") val recipes: List<RecipeDto> = emptyList(),
)

@Serializable
private data class RecipeDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("cuisine") val cuisine: String,
    @SerialName("image") val image: String,
    @SerialName("tags") val tags: List<String> = emptyList(),
)

private fun RecipeDto.toRemote(): RemoteDestination = RemoteDestination(
    id = id.toString(),
    title = name,
    country = cuisine,
    imageUrl = image,
    tags = tags,
)

private const val DEFAULT_BASE_URL = "https://dummyjson.com/"


