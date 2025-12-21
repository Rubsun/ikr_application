package com.tire.api.domain.usecases

import com.tire.api.domain.models.DeviceInfo

interface GetDevicesUseCase{
    suspend operator fun invoke(filter: String): List<DeviceInfo>
}
