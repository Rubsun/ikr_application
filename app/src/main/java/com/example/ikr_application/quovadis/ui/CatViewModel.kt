package quo.vadis.sirius.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import quo.vadis.sirius.data.Cat
import quo.vadis.sirius.domain.GetCatUseCase

class CatViewModel(private val getRandomCatUseCase: GetCatUseCase) : ViewModel() {
    private val _catLiveData = MutableLiveData<Cat>()
    val catLiveData: LiveData<Cat> = _catLiveData
    fun loadRandomCat(phrase: String? = null) {
        val cat = getRandomCatUseCase.getRandomCat(phrase)
        _catLiveData.value = cat
    }
}