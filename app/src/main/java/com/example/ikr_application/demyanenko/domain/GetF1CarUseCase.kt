package com.example.ikr_application.demyanenko.domain

import com.example.ikr_application.demyanenko.data.F1Car
import com.example.ikr_application.demyanenko.data.F1CarRepository

class GetF1CarUseCase(
    private val repository: F1CarRepository
) {
    fun getRandomF1Car(text: String?): F1Car {
        return repository.getF1Car(text)
    }
}