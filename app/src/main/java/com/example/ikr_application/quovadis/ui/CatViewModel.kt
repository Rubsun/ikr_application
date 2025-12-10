package com.example.ikr_application.quovadis.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ikr_application.quovadis.data.Cat
import com.example.ikr_application.quovadis.domain.GetCatUseCase

class CatViewModel(private val getRandomCatUseCase: GetCatUseCase) : ViewModel() {
    private val _catLiveData = MutableLiveData<Cat>()
    val catLiveData: LiveData<Cat> = _catLiveData
    fun loadRandomCat(phrase: String? = null) {
        val cat = getRandomCatUseCase.getRandomCat(phrase)
        _catLiveData.value = cat
    }
}