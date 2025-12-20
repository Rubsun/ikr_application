package quo.vadis.impl.domain

import quo.vadis.api.usecases.AssembleImageUrlUseCase
import quo.vadis.api.usecases.ApiBaseUrl
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

internal class AssembleImageUrlUseCaseImpl : AssembleImageUrlUseCase {
    private val baseUrlCatAaS = "https://cataas.com/cat/says/"
    private val baseUrlHttpCats = "https://httpcats.com/"

    override fun getImageUrl(baseUrl: ApiBaseUrl, query: String): String {
        val encoded = URLEncoder.encode(query, StandardCharsets.UTF_8.toString())

        return when (baseUrl) {
            ApiBaseUrl.CatAaS -> "$baseUrlCatAaS$encoded"
            ApiBaseUrl.HttpCats -> "$baseUrlHttpCats$encoded.jpg"
        }
    }
}