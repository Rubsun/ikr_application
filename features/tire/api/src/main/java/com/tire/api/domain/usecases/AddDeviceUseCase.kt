package com.tire.api.domain.usecases


interface AddDeviceUseCase {
    suspend operator fun invoke(deviceName: String)
}
