package com.example.api

import kotlinx.coroutines.flow.Flow

interface CatRoomRepository {
    fun getAllCatsFlow(): Flow<List<CatDto>>

    suspend fun insertCat(
        name: String,
        phrase: String?,
        imageUrl: String?,
        fetchedFrom: String
    ): Long

    suspend fun deleteCat(catId: Long)

    suspend fun deleteAllCats()
}