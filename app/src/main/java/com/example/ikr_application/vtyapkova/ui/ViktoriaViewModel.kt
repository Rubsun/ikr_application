package com.example.ikr_application.vtyapkova.ui

import androidx.lifecycle.ViewModel
import com.example.ikr_application.vtyapkova.domain.GetMultipleNamesUseCase
import com.example.ikr_application.vtyapkova.domain.GetRandomNameUseCase
import com.example.ikr_application.vtyapkova.domain.models.NameDisplayModel

class ViktoriaViewModel : ViewModel() {
    private val getRandomNameUseCase = GetRandomNameUseCase()
    private val getMultipleNamesUseCase = GetMultipleNamesUseCase()

    fun getRandomName(): NameDisplayModel {
        return getRandomNameUseCase.execute()
    }

    fun getMultipleNames(count: Int = 5): List<NameDisplayModel> {
        return getMultipleNamesUseCase.execute(count)
    }
}

