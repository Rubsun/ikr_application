package com.example.ikr_application.demyanenko.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ikr_application.demyanenko.data.F1Car
import com.example.ikr_application.demyanenko.domain.GetF1CarUseCase


class F1CarViewModel(private val getRandomF1CarUseCase: GetF1CarUseCase) : ViewModel() {
    private val _f1carLiveData = MutableLiveData<F1Car>()
    val f1carLiveData: LiveData<F1Car> = _f1carLiveData
    fun loadRandomF1Car(sound: String? = null) {
        val f1car = getRandomF1CarUseCase.getRandomF1Car(sound)
        _f1carLiveData.value = f1car
    }
}