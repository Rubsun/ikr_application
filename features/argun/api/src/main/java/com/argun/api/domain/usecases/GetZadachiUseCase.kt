package com.argun.api.domain.usecases

import com.argun.api.domain.models.Zadacha

interface GetZadachiUseCase {
    suspend fun invoke(): Result<List<Zadacha>>
}
