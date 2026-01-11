package com.michaelnoskov.impl.data.datasource

import android.content.Context
import android.content.SharedPreferences
import com.example.primitivestorage.api.PrimitiveStorage
import com.michaelnoskov.api.domain.model.FilteredItem
import com.michaelnoskov.api.domain.model.SquareData
import com.michaelnoskov.api.domain.repository.TemperaturePoint
import com.michaelnoskov.impl.data.mapper.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer

internal class LocalDataSourceImpl(
    private val context: Context,
    private val primitiveStorageFactory: PrimitiveStorage.Factory,
    private val mapper: DataMapper = DataMapper()
) : LocalDataSource {

    private val preferences: SharedPreferences by lazy {
        context.getSharedPreferences("color_square_prefs", Context.MODE_PRIVATE)
    }

    // Хранилище для квадрата
    private val squareStorage: PrimitiveStorage<DataSourceSquareEntity> by lazy {
        primitiveStorageFactory.create("square_storage", DataSourceSquareEntity.serializer())
    }

    // Хранилище для списка элементов
    private val itemsStorage: PrimitiveStorage<List<DataSourceFilteredItemEntity>> by lazy {
        primitiveStorageFactory.create("items_storage", ListSerializer(DataSourceFilteredItemEntity.serializer()))
    }

    // Хранилище для истории температур
    private val temperatureHistoryStorage: PrimitiveStorage<List<DataSourceTemperaturePointEntity>> by lazy {
        primitiveStorageFactory.create("temperature_history_storage", ListSerializer(DataSourceTemperaturePointEntity.serializer()))
    }

    override fun getSquareState(): Flow<SquareData> {
        return squareStorage.get().map { entity ->
            entity?.let { mapper.mapToSquareData(it) } ?: getDefaultSquareData()
        }
    }

    override suspend fun saveSquareState(squareData: SquareData) {
        val entity = mapper.mapToDataSourceSquareEntity(squareData)
        squareStorage.put(entity)
    }

    override fun getItems(): Flow<List<FilteredItem>> {
        return itemsStorage.get().map { entities ->
            entities?.map { mapper.mapToFilteredItem(it) } ?: getDefaultItems()
        }
    }

    override fun searchItems(query: String): Flow<List<FilteredItem>> {
        return getItems().map { items ->
            if (query.isBlank()) {
                items
            } else {
                items.filter { item ->
                    item.text.contains(query, ignoreCase = true)
                }
            }
        }
    }

    override suspend fun saveItems(items: List<FilteredItem>) {
        val entities = items.map { mapper.mapToFilteredItemEntity(it) }
        itemsStorage.put(entities)
    }

    override suspend fun addItem(item: FilteredItem) {
        itemsStorage.patch { currentEntities ->
            val currentList = currentEntities ?: emptyList()
            val newEntity = mapper.mapToFilteredItemEntity(item)
            currentList + newEntity
        }
    }

    override suspend fun deleteItem(id: String) {
        itemsStorage.patch { currentEntities ->
            currentEntities?.filter { it.id != id }
        }
    }

    override suspend fun saveLastSyncTime(timestamp: Long) {
        preferences.edit().putLong("last_sync_time", timestamp).apply()
    }

    override suspend fun getLastSyncTime(): Long {
        return preferences.getLong("last_sync_time", 0L)
    }

    override fun getTemperatureHistory(): Flow<List<TemperaturePoint>> {
        return temperatureHistoryStorage.get().map { entities ->
            entities?.map { TemperaturePoint(it.temperature, it.timestamp) } ?: emptyList()
        }
    }

    override suspend fun addTemperaturePoint(point: TemperaturePoint) {
        temperatureHistoryStorage.patch { currentEntities ->
            val currentList = currentEntities ?: emptyList()
            val newEntity = DataSourceTemperaturePointEntity(point.temperature, point.timestamp)
            (currentList + newEntity).takeLast(50) // Храним последние 50 точек
        }
    }

    private fun getDefaultSquareData(): SquareData {
        return SquareData(
            id = "default",
            color = 0xFF6200EE.toInt(),
            size = 200,
            rotation = 0f,
            alpha = 1f
        )
    }

    private fun getDefaultItems(): List<FilteredItem> {
        return listOf(
            FilteredItem(
                id = "1",
                text = "Красный квадрат",
                timestamp = System.currentTimeMillis()
            ),
            FilteredItem(
                id = "2",
                text = "Зеленый треугольник",
                timestamp = System.currentTimeMillis()
            ),
            FilteredItem(
                id = "3",
                text = "Синий круг",
                timestamp = System.currentTimeMillis()
            ),
            FilteredItem(
                id = "4",
                text = "Желтый прямоугольник",
                timestamp = System.currentTimeMillis()
            ),
            FilteredItem(
                id = "5",
                text = "Фиолетовый овал",
                timestamp = System.currentTimeMillis()
            )
        )
    }
}

// Сериализуемые сущности для хранения в DataSource пакете
@Serializable
internal data class DataSourceSquareEntity(
    val id: String = "default",
    val color: Int,
    val size: Int,
    val rotation: Float,
    val alpha: Float,
    val updatedAt: Long
)

@Serializable
internal data class DataSourceFilteredItemEntity(
    val id: String,
    val text: String,
    val timestamp: Long,
    val isVisible: Boolean = true,
    val isSynced: Boolean = false
)

@Serializable
internal data class DataSourceTemperaturePointEntity(
    val temperature: Double,
    val timestamp: Long
)

