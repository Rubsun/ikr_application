package com.example.ikr_application.spl3g.domain

import com.example.ikr_application.spl3g.data.AppleRepository
import com.example.ikr_application.spl3g.data.models.ImageData
import com.example.ikr_application.spl3g.domain.models.FrameResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch

class GetAppleFramesUseCase(private val repository: AppleRepository) {

    fun execute(
        startIndex: Int,
        maxFrames: Int? = null,
        retryOnError: Boolean = false,
        parallelism: Int = 10
    ): Flow<FrameResult> = channelFlow {
        val actualMaxFrames = maxFrames ?: Int.MAX_VALUE
        // Just a sanity check to avoid integer overflow or excessive looping
        val limit = minOf(actualMaxFrames, 10000)
        
        val indicesChannel = Channel<Int>(Channel.BUFFERED)

        // Producer: feed indices
        launch {
            for (i in startIndex until (startIndex + limit)) {
                indicesChannel.send(i)
            }
            indicesChannel.close()
        }

        // Consumers: process in parallel
        repeat(parallelism) {
            launch {
                for (index in indicesChannel) {
                    try {
                        val result = repository.getFrame(index)
                        when (result) {
                            is ImageData.BitmapData -> send(FrameResult.Success(result.index, result.data))
                            is ImageData.Error -> send(FrameResult.Error(result.index, result.error, retryOnError))
                        }
                    } catch (e: Exception) {
                        send(FrameResult.Error(index, e.message ?: "Unknown error", retryOnError))
                    }
                }
            }
        }
    }
}
