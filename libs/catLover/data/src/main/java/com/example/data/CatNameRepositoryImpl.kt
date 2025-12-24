package com.example.data

import com.example.api.CatNameRepository

class CatNameRepositoryImpl(
    private val api: CatNameApi
) : CatNameRepository {
    override suspend fun getCatName(): String {
        return api.randomCatName().first()
    }
}