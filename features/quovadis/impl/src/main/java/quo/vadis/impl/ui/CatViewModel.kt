package quo.vadis.impl.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api.CatDto
import com.example.api.CatRoomRepository
import com.example.injector.inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import quo.vadis.api.usecases.ApiBaseUrl
import quo.vadis.api.usecases.AssembleImageUrlUseCase
import quo.vadis.api.usecases.GetCatNameUseCase
import kotlin.collections.plus


internal class CatViewModel : ViewModel() {
    private val getRandomCatUseCase: GetCatNameUseCase by inject()
    private val assembleImageUrlUseCase: AssembleImageUrlUseCase by inject()
    private val roomRepository: CatRoomRepository by inject()

    private val _uiState = MutableStateFlow(CatUiState())
    val uiState: StateFlow<CatUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            roomRepository.getAllCatsFlow()
                .map { entities ->
                    entities.map { entity ->
                        CatDto(
                            name = entity.name,
                            phrase = entity.phrase,
                            imageUrl = entity.imageUrl,
                            fetchedFrom = entity.fetchedFrom
                        )
                    }
                }
                .collect { cats ->
                    _uiState.value = _uiState.value.copy(cats = cats)
                }
        }
    }

    fun loadRandomCat(phraseInput: String?) {
        val phrase = phraseInput?.takeIf { it.isNotBlank() }

        if (phrase.isNullOrBlank()) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            val cat = withContext(Dispatchers.Default) {
                val result = getRandomCatUseCase.getRandomCat(phrase)
                CatDto(
                    name = result.name,
                    phrase = result.phrase,
                    imageUrl = null,
                    fetchedFrom = _uiState.value.api.name
                )
            }

            val imageUrl = withContext(Dispatchers.IO) {
                assembleImageUrlUseCase.getImageUrl(_uiState.value.api, phrase)
            }

            val catWithImage = cat.copy(imageUrl = imageUrl)

            withContext(Dispatchers.IO) {
                roomRepository.insertCat(
                    name = catWithImage.name,
                    phrase = catWithImage.phrase,
                    imageUrl = catWithImage.imageUrl,
                    fetchedFrom = catWithImage.fetchedFrom
                )
            }

            _uiState.value = _uiState.value.copy(
                isLoading = false,
                cats = _uiState.value.cats + catWithImage
            )
        }
    }

    fun onFilterChanged(newFilter: String) {
        _uiState.value = _uiState.value.copy(filter = newFilter)
    }

    fun onApiChanged(newApi: ApiBaseUrl) {
        _uiState.value = _uiState.value.copy(api = newApi)
    }

    fun deleteCat(catId: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                roomRepository.deleteCat(catId)
            }
        }
    }
}
