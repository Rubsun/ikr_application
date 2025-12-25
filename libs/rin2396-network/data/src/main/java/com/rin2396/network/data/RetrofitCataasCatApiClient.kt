package com.rin2396.network.data

import com.rin2396.network.api.CataasCatApiClient
import com.rin2396.network.api.CatImageResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.http.GET
import kotlinx.serialization.Serializable

@Serializable
internal data class CataasListItem(
    val id: String,
    val tags: List<String> = emptyList(),
    val mimetype: String? = null,
    val createdAt: String? = null
)

internal interface CataasService {
    @GET("api/cats")
    suspend fun getRandomCatList(): List<CataasListItem>
}

internal class RetrofitCataasCatApiClient(
    private val service: CataasService
) : CataasCatApiClient {
    override suspend fun getRandomCat(): CatImageResponse = withContext(Dispatchers.IO) {
        val list = service.getRandomCatList()
        val item = list.randomOrNull() ?: throw IllegalStateException("Empty response from Cataas API")
        val imageUrl = "https://cataas.com/cat/${item.id}"
        CatImageResponse(url = imageUrl, _id = item.id)
    }
}
