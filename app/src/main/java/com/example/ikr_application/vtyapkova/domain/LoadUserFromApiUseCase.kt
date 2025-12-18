package com.example.ikr_application.vtyapkova.domain

import com.example.ikr_application.vtyapkova.data.api.RandomUserApiClient
import com.example.ikr_application.vtyapkova.data.models.ViktoriaData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LoadUserFromApiUseCase {
    private val apiService = RandomUserApiClient.apiService

    fun execute(): Flow<ViktoriaData> = flow {
        val response = apiService.getRandomUser()
        val user = response.results.first()
        emit(
            ViktoriaData(
                firstName = user.name.first,
                lastName = user.name.last,
                fullName = "${user.name.first} ${user.name.last}",
                initials = "${user.name.first.first()}.${user.name.last.first()}."
            )
        )
    }.flowOn(Dispatchers.IO)
}

