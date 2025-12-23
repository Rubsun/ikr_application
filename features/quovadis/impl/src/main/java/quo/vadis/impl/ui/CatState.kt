package quo.vadis.impl.ui

import quo.vadis.api.usecases.ApiBaseUrl
import quo.vadis.impl.data.CatDto


internal data class CatUiState(
    val isLoading: Boolean = false,
    val cats: List<CatDto> = emptyList(),
    val filter: String = "",
    val api: ApiBaseUrl = ApiBaseUrl.CatAaS,
    val error: String? = null
)