package com.example.ikr_application.spl3g.data

import android.graphics.BitmapFactory
import com.example.ikr_application.spl3g.data.AppleApi
import com.example.ikr_application.spl3g.data.models.ImageData
import java.io.IOException

class AppleRepository(private val appleApi: AppleApi) {

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
