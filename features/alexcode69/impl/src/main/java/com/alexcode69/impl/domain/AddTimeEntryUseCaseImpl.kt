package com.alexcode69.impl.domain

import com.alexcode69.api.domain.usecases.AddTimeEntryUseCase
import com.alexcode69.impl.data.DeviceRepository

internal class AddTimeEntryUseCaseImpl(
    private val repository: DeviceRepository
) : AddTimeEntryUseCase {
    override suspend fun execute(label: String) {
        repository.addTimeEntry(label)
    }
}

