package com.example.ikr_application.rubsun.ui

import androidx.lifecycle.ViewModel
import com.example.ikr_application.rubsun.domain.GetNumberUseCase
import com.example.ikr_application.rubsun.domain.models.NumberDisplayModel

class NumberViewModel : ViewModel() {
    private val getNumberUseCase = GetNumberUseCase()

    fun getRandomNumber(): NumberDisplayModel {
        return getNumberUseCase.getRandomNumber()
    }

    fun getAllNumbers(): List<NumberDisplayModel> {
        return getNumberUseCase.getAllNumbers()
    }
}

