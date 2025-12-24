package com.dyatlova.impl.data

import com.dyatlova.api.domain.models.Destination
import com.dyatlova.impl.data.models.DestinationData
import com.dyatlova.impl.data.models.DestinationsStorage
import com.example.primitivestorage.api.PrimitiveStorage
import com.dyatlova.network.DestinationsRemoteSource
import com.dyatlova.network.RemoteDestination
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

private const val STORAGE_NAME = "dyatlova_destinations.json"

internal class DestinationRepository(
    private val storageFactory: PrimitiveStorage.Factory,
    private val remoteSource: DestinationsRemoteSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) {
    private val storage by lazy {
        storageFactory.create(STORAGE_NAME, DestinationsStorage.serializer())
    }

    private val destinations = MutableStateFlow<List<DestinationData>>(emptyList())

    private val scope = CoroutineScope(dispatcher + SupervisorJob())

    init {
        scope.launch {
            loadFromStorageOrRemote()
        }
    }

    fun observeDestinations(): Flow<List<DestinationData>> = destinations

    suspend fun addDestination(destination: DestinationData) {
        withContext(dispatcher) {
            destinations.update { current -> listOf(destination) + current }
            saveToStorage()
        }
    }

    private suspend fun loadFromStorageOrRemote() {
        val stored = storage.get().first()
        val local = stored?.destinations.orEmpty()
        if (local.isNotEmpty()) {
            destinations.value = local
            return
        }

        val remote = runCatching { remoteSource.loadDestinations() }
            .getOrNull()
            .orEmpty()
            .map(RemoteDestination::toData)
            .takeIf { it.isNotEmpty() }
            ?: createFakeDestinations()

        destinations.value = remote
        saveToStorage()
    }

    private suspend fun saveToStorage() {
        val current = destinations.value
        storage.put(DestinationsStorage(destinations = current))
    }

    private fun createFakeDestinations(): List<DestinationData> = listOf(
        DestinationData(
            id = UUID.randomUUID().toString(),
            title = "Kamchatka Valleys",
            country = "Russia",
            imageUrl = "https://images.unsplash.com/photo-1548595224-91709c3c4d1a?auto=format&fit=crop&w=800&q=80",
            tags = listOf("volcano", "hiking", "north"),
        ),
        DestinationData(
            id = UUID.randomUUID().toString(),
            title = "Lisbon Alfama",
            country = "Portugal",
            imageUrl = "https://images.unsplash.com/photo-1505761671935-60b3a7427bad?auto=format&fit=crop&w=800&q=80",
            tags = listOf("ocean", "tiles", "food"),
        ),
        DestinationData(
            id = UUID.randomUUID().toString(),
            title = "Kyoto Temples",
            country = "Japan",
            imageUrl = "https://images.unsplash.com/photo-1504788363733-507549153474?auto=format&fit=crop&w=800&q=80",
            tags = listOf("zen", "history", "tea"),
        ),
        DestinationData(
            id = UUID.randomUUID().toString(),
            title = "Patagonia Trails",
            country = "Chile",
            imageUrl = "https://images.unsplash.com/photo-1469474968028-56623f02e42e?auto=format&fit=crop&w=800&q=80",
            tags = listOf("glacier", "camping", "winds"),
        ),
    )
}

internal fun DestinationData.toDomain(): Destination = Destination(
    id = id,
    title = title,
    country = country,
    imageUrl = imageUrl,
    tags = tags,
)

internal fun Destination.toData(): DestinationData = DestinationData(
    id = id,
    title = title,
    country = country,
    imageUrl = imageUrl,
    tags = tags,
)

private fun RemoteDestination.toData(): DestinationData = DestinationData(
    id = id,
    title = title,
    country = country,
    imageUrl = imageUrl,
    tags = tags,
)
