package com.example.ikr_application.spl3g.data.models

sealed class ImageData {
	data class BitmapData(
		val index: Int,
		val data: ByteArray  // Renamed from 'bitmap' to 'data' for clarity
	) : ImageData() {
		override fun equals(other: Any?): Boolean {
			if (this === other) return true
			if (javaClass != other?.javaClass) return false
			other as BitmapData
			if (index != other.index) return false
			if (!data.contentEquals(other.data)) return false
			return true
		}

		override fun hashCode(): Int {
			var result = index
			result = 31 * result + data.contentHashCode()
			return result
		}
	}

	data class Error(
		val index: Int,
		val error: String
	) : ImageData()
}
