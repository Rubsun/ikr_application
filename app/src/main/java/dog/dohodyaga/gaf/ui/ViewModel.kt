package dog.dohodyaga.gaf.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dog.dohodyaga.gaf.data.Repository
import dog.dohodyaga.gaf.domain.DogImageModel
import dog.dohodyaga.gaf.domain.GetDogImageUseCase
import kotlinx.coroutines.launch

class MyViewModel(
    private val getDogImageUseCase: GetDogImageUseCase =
        GetDogImageUseCase(Repository())
) : ViewModel() {

    private val _dogImage = MutableLiveData<DogImageModel?>()
    val dogImage: LiveData<DogImageModel?> = _dogImage

    fun loadDogImage() {
        viewModelScope.launch {
            val result = getDogImageUseCase.execute()
            _dogImage.postValue(result)
        }
    }
}