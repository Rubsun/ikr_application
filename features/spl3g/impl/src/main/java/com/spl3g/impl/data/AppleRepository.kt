package com.spl3g.impl.data

import com.example.primitivestorage.api.PrimitiveStorage
import com.spl3g.impl.data.models.ImageData
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
    private val appleApi: AppleApi,
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
        try {
            // Format the index as a 4-digit number with leading zeros (e.g., 5 -> "0005")
            val formattedIndex = String.format("%04d", index)

            val response = appleApi.getFrameByIndex(formattedIndex)

            if (response.isSuccessful) {
                val bytes = response.body()?.bytes()
                    ?: throw IOException("Empty response")
                return ImageData.BitmapData(
                    index = index,
                    data = bytes  // Updated to use 'data' instead of 'bitmap'
                )
            } else {
                throw IOException("Failed to fetch frame: ${response.code()}")
            }
        } catch (e: Exception) {
            return ImageData.Error(
                index = index,
                error = e.message ?: "Unknown error"
            )
        }
    }
}