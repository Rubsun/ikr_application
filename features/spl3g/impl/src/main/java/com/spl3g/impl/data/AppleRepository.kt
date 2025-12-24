package com.spl3g.impl.data

import com.example.primitivestorage.api.PrimitiveStorage
import com.spl3g.network.api.models.ImageData
import com.spl3g.network.api.AppleApiClient
import kotlinx.coroutines.flow.first
import kotlinx.serialization.Serializable
import java.io.IOException

@Serializable
data class Spl3gState(
    val selectedFps: Int,
    val lastFrame: Int,
)

private const val STORAGE_NAME = "spl3g_state.json"

class AppleRepository(
    private val appleApi: AppleApiClient,
    storageFactory: PrimitiveStorage.Factory
) {

    private val storage by lazy {
        storageFactory.create(STORAGE_NAME, Spl3gState.serializer())
    }

    suspend fun getSavedState(): Spl3gState? {
        return storage.get().first()
    }

    suspend fun saveState(fps: Int, frame: Int) {
        val state = Spl3gState(selectedFps = fps, lastFrame = frame)
        storage.put(state)
    }

    suspend fun getFrame(index: Int): ImageData {
        return appleApi.getFrameByIndex(index)
    }
}