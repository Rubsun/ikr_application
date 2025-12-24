package com.tire.impl.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.tire.impl.data.local.converters.PokemonTypeListConverter
import com.tire.impl.data.local.converters.RarityConverter

@Entity(tableName = "pokemons")
@TypeConverters(PokemonTypeListConverter::class, RarityConverter::class)
internal data class PokemonEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val imageUrl: String,
    val types: String,  // JSON строка: ["FIRE", "FLYING"]
    val rarity: String, // "COMMON", "RARE", "EPIC", "LEGENDARY"
    val isOwned: Boolean = false,
    val duplicateCount: Int = 0,  // количество дубликатов
    val firstObtainedAt: Long? = null  // timestamp первого получения
)
