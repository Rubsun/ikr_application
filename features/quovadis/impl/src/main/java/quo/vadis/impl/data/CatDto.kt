package quo.vadis.impl.data

import quo.vadis.api.usecases.ApiBaseUrl

data class CatDto(
    val name: String,
    val phrase: String?,
    val imageUrl: String?,
    val fetchedFrom: ApiBaseUrl
)
