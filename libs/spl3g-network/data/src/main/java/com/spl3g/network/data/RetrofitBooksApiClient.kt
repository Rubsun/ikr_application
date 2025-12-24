package com.egorik4.network.data

import com.spl3g.network.api.AppleApiClient
import com.spl3g.network.api.models.ImageData
import com.spl3g.network.data.AppleService
import java.io.IOException

internal class RetrofitAppleApiClient(
    private val service: AppleService
) : AppleApiClient {
    override suspend fun getFrameByIndex(index: Int): ImageData {
	try {
	// Format the index as a 4-digit number with leading zeros (e.g., 5 -> "0005")
	val formattedIndex = String.format("%04d", index)

	val response = service.getFrameByIndex(formattedIndex)

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

