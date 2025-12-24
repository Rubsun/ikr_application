package com.spl3g.impl.domain

import com.spl3g.api.domain.GetAppleFramesUseCase
import com.spl3g.api.domain.models.FrameResult
import com.spl3g.impl.data.AppleRepository
import com.spl3g.network.api.models.ImageData
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch

class GetAppleFramesUseCaseImpl(private val repository: AppleRepository) : GetAppleFramesUseCase {

    override fun execute(
        startIndex: Int,
        maxFrames: Int?,
        retryOnError: Boolean,
        parallelism: Int
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
                        when (val result = repository.getFrame(index)) {
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
