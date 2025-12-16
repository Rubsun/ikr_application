package com.example.ikr_application.quovadis.domain

import com.example.ikr_application.quovadis.data.Cat
import com.example.ikr_application.quovadis.data.CatRepository

class GetCatUseCase(
    private val repository: CatRepository
) {
    fun getRandomCat(phrase: String?): Cat {
        return repository.getCat(phrase)
    }
}