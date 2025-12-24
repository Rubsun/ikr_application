package com.tire.impl.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tire.impl.data.local.converters.PokemonTypeListConverter
import com.tire.impl.data.local.converters.RarityConverter
import com.tire.impl.data.local.dao.PokemonDao
import com.tire.impl.data.local.entities.PokemonEntity

@Database(
    entities = [
        PokemonEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    PokemonTypeListConverter::class,
    RarityConverter::class
)
internal abstract class PokeBase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao

    companion object {
        const val DATABASE_NAME = "pokemon_database"
    }
}
