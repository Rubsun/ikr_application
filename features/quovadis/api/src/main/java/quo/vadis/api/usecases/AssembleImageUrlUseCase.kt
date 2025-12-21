package quo.vadis.api.usecases

interface AssembleImageUrlUseCase {
    fun getImageUrl(api: ApiBaseUrl, query: String) : String
}