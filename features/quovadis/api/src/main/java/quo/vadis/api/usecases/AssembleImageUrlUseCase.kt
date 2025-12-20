package quo.vadis.api.usecases

import quo.vadis.api.usecases.ApiBaseUrl

interface AssembleImageUrlUseCase {
    fun getImageUrl(baseUrl: ApiBaseUrl, query: String) : String
}