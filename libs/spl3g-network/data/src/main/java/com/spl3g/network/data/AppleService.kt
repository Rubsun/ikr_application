package com.spl3g.network.data

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Streaming

interface AppleService {
	@GET("apple/frames/output_{index}.jpg")
	@Streaming  // Important for large files like images
	suspend fun getFrameByIndex(
		@Path("index") index: String
	): Response<ResponseBody>
}