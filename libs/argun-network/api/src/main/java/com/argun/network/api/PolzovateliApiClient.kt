package com.argun.network.api

import com.argun.network.api.models.PolzovatelDto

interface PolzovateliApiClient {
    suspend fun vsePolzovateli(): List<PolzovatelDto>
    suspend fun polzovatelPoId(id: Int): PolzovatelDto
    suspend fun sozdatPolzovatelya(polzovatel: PolzovatelDto): PolzovatelDto
    suspend fun obnovitPolzovatelya(id: Int, polzovatel: PolzovatelDto): PolzovatelDto
    suspend fun udalitPolzovatelya(id: Int)
}

