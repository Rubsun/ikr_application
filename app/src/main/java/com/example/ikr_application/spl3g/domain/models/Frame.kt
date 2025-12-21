package com.example.ikr_application.spl3g.domain.models

sealed class FrameResult {
	data class Success(
		val index: Int,
		val bitmapData: ByteArray
	) : FrameResult() {
		override fun equals(other: Any?): Boolean {
			if (this === other) return true
			if (javaClass != other?.javaClass) return false
			other as Success
			if (index != other.index) return false
			if (!bitmapData.contentEquals(other.bitmapData)) return false
			return true
		}

		override fun hashCode(): Int {
			var result = index
			result = 31 * result + bitmapData.contentHashCode()
			return result
		}
	}

	data class Error(
		val index: Int,
		val error: String,
		val shouldRetry: Boolean = false
	) : FrameResult()
}
