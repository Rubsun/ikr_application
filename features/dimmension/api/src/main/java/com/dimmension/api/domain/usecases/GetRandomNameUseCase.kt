package com.dimmension.api.domain.usecases

import com.dimmension.api.domain.models.NameDisplayModel

interface GetRandomNameUseCase {
    suspend operator fun invoke(): NameDisplayModel
}


