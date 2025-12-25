package com.tire.storage.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.tire.storage.data.converters.PokemonTypeListConverter
import com.tire.storage.data.converters.RarityConverter

@Entity(tableName = "pokemons")
@TypeConverters(PokemonTypeListConverter::class, RarityConverter::class)
internal data class PokemonEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val imageUrl: String,
    val types: String,  // JSON ["FIRE", "FLYING"]
    val rarity: String,
    val ownedCount: Int = 0,
    val firstObtainedAt: Long? = null
)
