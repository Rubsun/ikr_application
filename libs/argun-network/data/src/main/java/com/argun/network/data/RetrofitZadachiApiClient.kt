package com.argun.network.data

import com.argun.network.api.ZadachiApiClient
import com.argun.network.api.models.ZadachaDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class RetrofitZadachiApiClient(
    private val service: ZadachiService
) : ZadachiApiClient {
    
    override suspend fun vseZadachi(): List<ZadachaDto> = withContext(Dispatchers.IO) {
        val response = service.vseZadachi()
        response.map { it.toDto() }
    }
    
    override suspend fun zadachaPoId(id: Int): ZadachaDto = withContext(Dispatchers.IO) {
        val response = service.zadachaPoId(id)
        response.toDto()
    }
    
    override suspend fun sozdatZadachu(zadacha: ZadachaDto): ZadachaDto = withContext(Dispatchers.IO) {
        val response = service.sozdatZadachu(zadacha.toResponse())
        response.toDto()
    }
    
    override suspend fun obnovitZadachu(id: Int, zadacha: ZadachaDto): ZadachaDto = withContext(Dispatchers.IO) {
        val response = service.obnovitZadachu(id, zadacha.toResponse())
        response.toDto()
    }
    
    override suspend fun udalitZadachu(id: Int) = withContext(Dispatchers.IO) {
        service.udalitZadachu(id)
    }
    
    private fun com.argun.network.data.models.ZadachaResponse.toDto(): ZadachaDto {
        return ZadachaDto(
            id = id,
            title = title,
            completed = completed,
            userId = userId
        )
    }
    
    private fun ZadachaDto.toResponse(): com.argun.network.data.models.ZadachaResponse {
        return com.argun.network.data.models.ZadachaResponse(
            id = id,
            title = title,
            completed = completed,
            userId = userId
        )
    }
}

