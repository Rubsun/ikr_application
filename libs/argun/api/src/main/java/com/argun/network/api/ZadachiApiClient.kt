package com.argun.network.api

import com.argun.network.api.models.ZadachaDto

interface ZadachiApiClient {
    suspend fun vseZadachi(): List<ZadachaDto>
    suspend fun zadachaPoId(id: Int): ZadachaDto
    suspend fun sozdatZadachu(zadacha: ZadachaDto): ZadachaDto
    suspend fun obnovitZadachu(id: Int, zadacha: ZadachaDto): ZadachaDto
    suspend fun udalitZadachu(id: Int)
}

