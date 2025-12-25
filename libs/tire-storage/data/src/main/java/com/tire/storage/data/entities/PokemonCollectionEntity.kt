package com.tire.storage.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_collection")
internal data class PokemonCollectionEntity(
    @PrimaryKey
    val pokemonId: Int,
    val count: Int = 1,
    val firstObtainedAt: Long = System.currentTimeMillis(),
    val lastObtainedAt: Long = System.currentTimeMillis()
)
