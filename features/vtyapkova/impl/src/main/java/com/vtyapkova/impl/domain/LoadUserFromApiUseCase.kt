package com.vtyapkova.impl.domain

import com.vtyapkova.impl.data.RandomUserApiService
import com.vtyapkova.impl.data.storage.StoredViktoriaData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class LoadUserFromApiUseCase(
    private val apiService: RandomUserApiService,
) {
    fun execute(): Flow<StoredViktoriaData> = flow {
        val response = apiService.getRandomUser()
        val user = response.results.first()
        emit(
            StoredViktoriaData(
                firstName = user.name.first,
                lastName = user.name.last,
                fullName = "${user.name.first} ${user.name.last}",
                initials = "${user.name.first.first()}.${user.name.last.first()}."
            )
        )
    }.flowOn(Dispatchers.IO)
}
