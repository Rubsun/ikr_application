package com.grigoran.network.data
import com.example.network.api.RetrofitServiceFactory
import com.grigoran.network.api.ApiClient
import com.grigoran.network.api.Item


private const val BASE_URL = "https://dummyjson.com/"
internal class ApiClientImpl(
    retrofitServiceFactory: RetrofitServiceFactory
): ApiClient {
    private val service: ItemService = retrofitServiceFactory.create(BASE_URL, ItemService::class.java)
    override suspend fun search(query: String): List<Item> {
        return service.search(query=query).products.map { dto ->
            Item(
                id=dto.id,
                title = dto.title,
                price = dto.price,
                thumbnail = dto.thumbnail
            )
        }
    }
}