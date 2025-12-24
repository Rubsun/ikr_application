package com.alexcode69.impl.domain

import com.alexcode69.api.domain.usecases.FetchRequestInfoUseCase
import com.alexcode69.api.domain.usecases.RequestInfo
import com.alexcode69.impl.data.DeviceRepository

internal class FetchRequestInfoUseCaseImpl(
    private val repository: DeviceRepository
) : FetchRequestInfoUseCase {
    override suspend fun execute(): RequestInfo {
        val dto = repository.fetchRequestInfo()
        return RequestInfo(
            url = dto.url,
            origin = dto.origin
        )
    }
}

