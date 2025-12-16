package com.example.ikr_application.dyatlova.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import java.util.UUID

class DestinationRepository(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) {
    private val destinations = MutableStateFlow(createFakeDestinations())

    fun observeDestinations(): Flow<List<DestinationData>> = destinations

    suspend fun addDestination(destination: DestinationData) {
        withContext(dispatcher) {
            destinations.update { current -> listOf(destination) + current }
        }
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

