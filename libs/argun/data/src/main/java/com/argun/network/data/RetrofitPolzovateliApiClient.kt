package com.argun.network.data

import com.argun.network.api.PolzovateliApiClient
import com.argun.network.api.models.PolzovatelDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class RetrofitPolzovateliApiClient(
    private val service: PolzovateliService
) : PolzovateliApiClient {
    
    override suspend fun vsePolzovateli(): List<PolzovatelDto> = withContext(Dispatchers.IO) {
        val response = service.vsePolzovateli()
        response.map { it.toDto() }
    }
    
    override suspend fun polzovatelPoId(id: Int): PolzovatelDto = withContext(Dispatchers.IO) {
        val response = service.polzovatelPoId(id)
        response.toDto()
    }
    
    override suspend fun sozdatPolzovatelya(polzovatel: PolzovatelDto): PolzovatelDto = withContext(Dispatchers.IO) {
        val response = service.sozdatPolzovatelya(polzovatel.toResponse())
        response.toDto()
    }
    
    override suspend fun obnovitPolzovatelya(id: Int, polzovatel: PolzovatelDto): PolzovatelDto = withContext(Dispatchers.IO) {
        val response = service.obnovitPolzovatelya(id, polzovatel.toResponse())
        response.toDto()
    }
    
    override suspend fun udalitPolzovatelya(id: Int) = withContext(Dispatchers.IO) {
        service.udalitPolzovatelya(id)
    }
    
    private fun com.argun.network.data.models.PolzovatelResponse.toDto(): PolzovatelDto {
        return PolzovatelDto(
            id = id,
            name = name,
            username = username,
            email = email
        )
    }
    
    private fun PolzovatelDto.toResponse(): com.argun.network.data.models.PolzovatelResponse {
        return com.argun.network.data.models.PolzovatelResponse(
            id = id,
            name = name,
            username = username,
            email = email
        )
    }
}

