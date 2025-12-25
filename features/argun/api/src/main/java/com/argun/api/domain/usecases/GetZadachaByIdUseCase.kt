package com.argun.api.domain.usecases

import com.argun.api.domain.models.Zadacha

interface GetZadachaByIdUseCase {
    suspend fun invoke(id: Int): Result<Zadacha>
}

