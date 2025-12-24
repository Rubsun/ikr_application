package com.example.api

interface CatNameRepository {
    suspend fun getCatName(): String
}