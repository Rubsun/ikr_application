package com.tire.impl.data.local.converters

import androidx.room.TypeConverter
import com.tire.api.domain.PokemonRarity

internal class RarityConverter {

    @TypeConverter
    fun fromRarity(rarity: PokemonRarity): String {
        return rarity.name
    }

    @TypeConverter
    fun toRarity(rarityString: String): PokemonRarity {
        return try {
            PokemonRarity.valueOf(rarityString)
        } catch (e: Exception) {
            PokemonRarity.COMMON
        }
    }
}
