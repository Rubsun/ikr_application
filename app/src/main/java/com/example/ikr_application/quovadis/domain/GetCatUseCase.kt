package com.example.ikr_application.quovadis.domain

import com.example.ikr_application.quovadis.data.Cat
import quo.vadis.sirius.data.CatRepository

class GetCatUseCase(
    private val repository: CatRepository
) {
    fun getRandomCat(text: String?): Cat {
        return repository.getCat(text)
    }
}