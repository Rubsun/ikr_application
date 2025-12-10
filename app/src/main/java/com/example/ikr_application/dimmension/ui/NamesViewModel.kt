package com.example.ikr_application.dimmension.ui

import androidx.lifecycle.ViewModel
import com.example.ikr_application.dimmension.domain.GetMultipleNamesUseCase
import com.example.ikr_application.dimmension.domain.GetRandomNameUseCase
import com.example.ikr_application.dimmension.domain.models.NameDisplayModel

class NamesViewModel : ViewModel() {
    private val getRandomNameUseCase = GetRandomNameUseCase()
    private val getMultipleNamesUseCase = GetMultipleNamesUseCase()

    fun getRandomName(): NameDisplayModel {
        return getRandomNameUseCase.execute()
    }

    fun getMultipleNames(count: Int = 5): List<NameDisplayModel> {
        return getMultipleNamesUseCase.execute(count)
    }
}

