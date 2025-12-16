package com.example.ikr_application.alexcode69.domain

import com.example.ikr_application.alexcode69.data.DeviceRepository

class AddTimeEntryUseCase(private val repository: DeviceRepository = DeviceRepository.INSTANCE) {
    fun execute(label: String) {
        repository.addTimeEntry(label)
    }
}
