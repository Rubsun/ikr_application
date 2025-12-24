package com.spl3g.network.api

import com.spl3g.network.api.models.ImageData

interface AppleApiClient {
	suspend fun getFrameByIndex(
		index: Int
	): ImageData
}