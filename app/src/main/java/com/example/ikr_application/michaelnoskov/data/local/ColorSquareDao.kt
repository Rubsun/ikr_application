package com.example.ikr_application.michaelnoskov.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ColorSquareDao {
    // Square
    @Query("SELECT * FROM square_data LIMIT 1")
    fun getSquare(): Flow<SquareEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSquare(square: SquareEntity)

    // Items
    @Query("SELECT * FROM filtered_items WHERE isVisible = 1")
    fun getItems(): Flow<List<FilteredItemEntity>>

    @Query("SELECT * FROM filtered_items WHERE text LIKE '%' || :query || '%' AND isVisible = 1")
    fun searchItems(query: String): Flow<List<FilteredItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: FilteredItemEntity)

    @Query("DELETE FROM filtered_items WHERE id = :id")
    suspend fun deleteItem(id: String)

    @Query("UPDATE filtered_items SET isSynced = 1 WHERE id IN (:ids)")
    suspend fun markItemsAsSynced(ids: List<String>)

    @Query("SELECT * FROM filtered_items WHERE isSynced = 0")
    suspend fun getUnsyncedItems(): List<FilteredItemEntity>
}