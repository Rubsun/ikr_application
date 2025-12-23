package quo.vadis.impl.ui

import com.example.api.CatDto
import quo.vadis.api.usecases.ApiBaseUrl


internal data class CatUiState(
    val isLoading: Boolean = false,
    val cats: List<CatDto> = emptyList(),
    val filter: String = "",
    val api: ApiBaseUrl = ApiBaseUrl.CatAaS,
    val error: String? = null
)