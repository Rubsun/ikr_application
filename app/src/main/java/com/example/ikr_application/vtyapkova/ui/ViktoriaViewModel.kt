package com.example.ikr_application.vtyapkova.ui

import androidx.lifecycle.ViewModel
import com.example.ikr_application.vtyapkova.domain.GetMultipleViktoriaUseCase
import com.example.ikr_application.vtyapkova.domain.GetRandomViktoriaUseCase
import com.example.ikr_application.vtyapkova.domain.models.ViktoriaDisplayModel

class ViktoriaViewModel : ViewModel() {
    private val getRandomViktoriaUseCase = GetRandomViktoriaUseCase()
    private val getMultipleViktoriaUseCase = GetMultipleViktoriaUseCase()

    fun getRandomViktoria(): ViktoriaDisplayModel {
        return getRandomViktoriaUseCase.execute()
    }

    fun getMultipleViktoria(count: Int = 5): List<ViktoriaDisplayModel> {
        return getMultipleViktoriaUseCase.execute(count)
    }
}

