package com.tire.storage.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tire.storage.data.converters.PokemonTypeListConverter
import com.tire.storage.data.converters.RarityConverter
import com.tire.storage.data.dao.PokemonDao
import com.tire.storage.data.entities.PokemonEntity

@Database(
    entities = [
        PokemonEntity::class
    ],
    version = 2,
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