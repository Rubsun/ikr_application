package quo.vadis.impl.domain

import quo.vadis.api.usecases.ApiBaseUrl
import quo.vadis.api.usecases.AssembleImageUrlUseCase
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

internal class AssembleImageUrlUseCaseImpl : AssembleImageUrlUseCase {

    override fun getImageUrl(api: ApiBaseUrl, query: String): String {
        val encoded = URLEncoder.encode(query, StandardCharsets.UTF_8.toString())

        return when (api) {
            ApiBaseUrl.CatAaS -> "${api.baseUrl}$encoded"
            ApiBaseUrl.HttpCats -> "${api.baseUrl}$encoded.jpg"
        }
    }
}