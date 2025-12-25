package com.tire.storage.data.dao

import androidx.room.*
import com.tire.storage.data.entities.PokemonEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface PokemonDao {

    // Получить всех покемонов из кэша
    @Query("SELECT * FROM pokemons ORDER BY id ASC")
    fun getAllPokemons(): Flow<List<PokemonEntity>>

    // Получить покемона по ID
    @Query("SELECT * FROM pokemons WHERE id = :pokemonId")
    suspend fun getPokemonById(pokemonId: Int): PokemonEntity?

    // Получить покемонов по списку ID
    @Query("SELECT * FROM pokemons WHERE id IN (:ids)")
    suspend fun getPokemonsByIds(ids: List<Int>): List<PokemonEntity>

    // Получить коллекцию пользователя (только owned покемоны)
    @Query("SELECT * FROM pokemons WHERE ownedCount > 0 ORDER BY firstObtainedAt DESC")
    fun getMyCollection(): Flow<List<PokemonEntity>>

    // Поиск покемонов по имени
    @Query("SELECT * FROM pokemons WHERE name LIKE '%' || :query || '%' ORDER BY name ASC")
    fun searchPokemons(query: String): Flow<List<PokemonEntity>>

    // Получить количество owned покемона
    @Query("SELECT ownedCount FROM pokemons WHERE id = :pokemonId")
    suspend fun getDuplicateCount(pokemonId: Int): Int?

    // Вставить покемона (заменить если уже есть)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemon: PokemonEntity)

    // Вставить список покемонов
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemons(pokemons: List<PokemonEntity>)

    // Обновить покемона
    @Update
    suspend fun updatePokemon(pokemon: PokemonEntity)

    // Добавить покемона в коллекцию (увеличить счетчик дубликатов)
    @Query("""
        UPDATE pokemons 
        SET ownedCount = ownedCount + 1,
            firstObtainedAt = CASE 
                WHEN firstObtainedAt IS NULL THEN :timestamp 
                ELSE firstObtainedAt 
            END
        WHERE id = :pokemonId
    """)
    suspend fun addToCollection(pokemonId: Int, timestamp: Long)

    // Удалить покемона
    @Delete
    suspend fun deletePokemon(pokemon: PokemonEntity)

    // Удалить всех покемонов
    @Query("DELETE FROM pokemons")
    suspend fun deleteAll()

    @Query("SELECT * FROM pokemons ORDER BY id ASC LIMIT :limit OFFSET :offset")
    suspend fun getAllPokemonPage(offset: Int, limit: Int): List<PokemonEntity>

    @Query("SELECT * FROM pokemons WHERE name = :name LIMIT 1")
    suspend fun getByName(name: String): PokemonEntity?
}
