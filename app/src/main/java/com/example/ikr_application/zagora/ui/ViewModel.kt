package com.example.ikr_application.zagora.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ikr_application.zagora.data.Repository
import com.example.ikr_application.zagora.domain.DogImageModel
import com.example.ikr_application.zagora.domain.GetDogImageUseCase
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {

    private val getDogImageUseCase: GetDogImageUseCase

    init {
        val repository = Repository()
        getDogImageUseCase = GetDogImageUseCase(repository)
    }

    private val _dogImage = MutableLiveData<DogImageModel?>()
    val dogImage: LiveData<DogImageModel?> = _dogImage

    fun loadDogImage() {
        viewModelScope.launch {
            val result = getDogImageUseCase.execute()
            _dogImage.postValue(result)
        }
    }
}