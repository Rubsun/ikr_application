package com.example.ikr_application.spl3g.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ikr_application.spl3g.data.AppleApi
import com.example.ikr_application.spl3g.data.AppleRepository
import com.example.ikr_application.spl3g.domain.GetAppleFramesUseCase
import com.example.ikr_application.spl3g.domain.models.FrameResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.min
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

data class AppleFramesState(
    val frames: List<Bitmap> = emptyList(),
    val frameIndices: List<Int> = emptyList(), // The logical index for each frame in the frames list
    val currentFrameIndex: Int = 0,
    val currentLogicalFrame: Int = 0,  // The logical index of the currently displayed frame
    val highestLoadedFrame: Int = 0,   // The highest frame index we've loaded so far
    val isPlaying: Boolean = false,
    val error: String? = null,
    val isLoading: Boolean = false,
    val playbackPosition: Int = 0  // Track the overall playback position for loading decisions
)

class AppleFramesViewModel : ViewModel() {

    private val appleApi = Retrofit.Builder()
        .baseUrl("https://kcu.su/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(AppleApi::class.java)
    private val appleRepository = AppleRepository(appleApi)
    private val getAppleFramesUseCase = GetAppleFramesUseCase(appleRepository)

    private val _uiState = MutableStateFlow(AppleFramesState())
    val uiState: StateFlow<AppleFramesState> = _uiState.asStateFlow()

    private var startIndex = 1
    private val maxFramesInMemory = 30  // Maximum frames to keep in memory
    private var isFetching = false
    private var playJob: kotlinx.coroutines.Job? = null

    fun loadNextPage() {
        if (isFetching) return
        isFetching = true

        viewModelScope.launch {
            getAppleFramesUseCase.execute(
                startIndex = startIndex,
                maxFrames = 50,  // Load larger batches to improve performance
                parallelism = 15  // Load frames in parallel within each batch
            )
                .flowOn(Dispatchers.IO)
                .onStart {
                    _uiState.value = _uiState.value.copy(isLoading = true)
                }
                .catch { e ->
                    _uiState.value = _uiState.value.copy(error = e.message, isLoading = false)
                    isFetching = false
                }
                .collect { result ->
                    when (result) {
                        is FrameResult.Success -> {
                            // Decode bitmap on IO thread to avoid UI jank
                            val bitmap = withContext(Dispatchers.IO) {
                                BitmapFactory.decodeByteArray(result.bitmapData, 0, result.bitmapData.size)
                            }
                            
                            val currentFrames = _uiState.value.frames.toMutableList()
                            val currentIndices = _uiState.value.frameIndices.toMutableList()

                            // Check if this frame already exists in our buffer to avoid duplicates
                            val existingIndex = currentIndices.indexOf(result.index)
                            if (existingIndex == -1) {
                                // Add frame to the buffer
                                currentFrames.add(bitmap)
                                currentIndices.add(result.index)
                                
                                // Keep frames sorted by index
                                val sortedPairs = currentIndices.zip(currentFrames).sortedBy { it.first }
                                currentIndices.clear()
                                currentIndices.addAll(sortedPairs.map { it.first })
                                currentFrames.clear()
                                currentFrames.addAll(sortedPairs.map { it.second })

                                // If we exceed max frames, remove frames that are well behind current playback
                                // Only remove if we have more than max capacity
                                while (currentFrames.size > maxFramesInMemory) {
                                    // Find the oldest frame that's well behind current playback position
                                    // Safe to remove frames that are more than 20 positions behind
                                    val removeThreshold = maxOf(1, _uiState.value.playbackPosition - 20)
                                    val frameToRemove = currentIndices.indexOfFirst { it < removeThreshold }

                                    if (frameToRemove != -1) {
                                        currentFrames.removeAt(frameToRemove)
                                        currentIndices.removeAt(frameToRemove)
                                    } else {
                                        // If we can't find safe frames to remove, remove the oldest (first)
                                        if (currentFrames.size > maxFramesInMemory) {
                                            currentFrames.removeAt(0)
                                            currentIndices.removeAt(0)
                                        }
                                        break
                                    }
                                }
                            } else {
                                // Update the existing frame if needed (re-download might have newer version)
                                currentFrames[existingIndex] = bitmap
                            }

                            _uiState.value = _uiState.value.copy(
                                frames = currentFrames,
                                frameIndices = currentIndices,
                                highestLoadedFrame = maxOf(_uiState.value.highestLoadedFrame, result.index),
                                isLoading = false
                            )
                            startIndex = maxOf(startIndex, result.index + 1)
                        }
                        is FrameResult.Error -> {
                            _uiState.value = _uiState.value.copy(
                                error = result.error,
                                isLoading = false
                            )
                        }
                    }
                    isFetching = false
                }
        }
    }

    fun preloadFrames(count: Int = 100) {
        // Don't interrupt current loading
        if (!isFetching) {
            viewModelScope.launch {
                getAppleFramesUseCase.execute(
                    startIndex = startIndex,
                    maxFrames = min(count, maxFramesInMemory), // Don't preload more than max in memory
                    parallelism = 15  // Load frames in parallel within each batch
                )
                    .flowOn(Dispatchers.IO)
                    .onStart {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    .catch { e ->
                        _uiState.value = _uiState.value.copy(error = e.message, isLoading = false)
                    }
                    .collect { result ->
                        when (result) {
                            is FrameResult.Success -> {
                                // Decode bitmap on IO thread
                                val bitmap = withContext(Dispatchers.IO) {
                                    BitmapFactory.decodeByteArray(result.bitmapData, 0, result.bitmapData.size)
                                }
                                
                                val currentFrames = _uiState.value.frames.toMutableList()
                                val currentIndices = _uiState.value.frameIndices.toMutableList()

                                // Check if this frame already exists in our buffer to avoid duplicates
                                val existingIndex = currentIndices.indexOf(result.index)
                                if (existingIndex == -1) {
                                    // Add frame to the buffer
                                    currentFrames.add(bitmap)
                                    currentIndices.add(result.index)
                                    
                                    // Keep sorted
                                    val sortedPairs = currentIndices.zip(currentFrames).sortedBy { it.first }
                                    currentIndices.clear()
                                    currentIndices.addAll(sortedPairs.map { it.first })
                                    currentFrames.clear()
                                    currentFrames.addAll(sortedPairs.map { it.second })

                                    // If we exceed max frames, remove frames that are well behind current playback
                                    // Only remove if we have more than max capacity
                                    while (currentFrames.size > maxFramesInMemory) {
                                        // Find the oldest frame that's well behind current playback position
                                        // Safe to remove frames that are more than 20 positions behind
                                        val removeThreshold = maxOf(1, _uiState.value.playbackPosition - 20)
                                        val frameToRemove = currentIndices.indexOfFirst { it < removeThreshold }

                                        if (frameToRemove != -1) {
                                            currentFrames.removeAt(frameToRemove)
                                            currentIndices.removeAt(frameToRemove)
                                        } else {
                                            // If we can't find safe frames to remove, remove the oldest (first)
                                            if (currentFrames.size > maxFramesInMemory) {
                                                currentFrames.removeAt(0)
                                                currentIndices.removeAt(0)
                                            }
                                            break
                                        }
                                    }
                                } else {
                                    // Update the existing frame if needed (re-download might have newer version)
                                    currentFrames[existingIndex] = bitmap
                                }

                                _uiState.value = _uiState.value.copy(
                                    frames = currentFrames,
                                    frameIndices = currentIndices,
                                    highestLoadedFrame = maxOf(_uiState.value.highestLoadedFrame, result.index),
                                    isLoading = false
                                )
                                startIndex = maxOf(startIndex, result.index + 1)
                            }
                            is FrameResult.Error -> {
                                _uiState.value = _uiState.value.copy(
                                    error = result.error,
                                    isLoading = false
                                )
                            }
                        }
                    }
            }
        }
    }

    fun startPlayback() {
        if (playJob?.isActive == true) return

        playJob = viewModelScope.launch {
            // If we are resetting, ensure we don't skip frames if the buffer has shifted
            if (_uiState.value.playbackPosition == 0 && _uiState.value.frameIndices.isNotEmpty()) {
               _uiState.value = _uiState.value.copy(playbackPosition = _uiState.value.frameIndices.first())
            }

            while (true) {
                val currentState = _uiState.value

                if (currentState.frames.isNotEmpty()) {
                    // Find the index in our current buffer that corresponds to the next logical frame
                    // If we just started or wrapped, we need to find the right spot
                    val currentBufferIndex = currentState.frameIndices.indexOf(currentState.playbackPosition)
                    
                    var nextBufferIndex = -1
                    var nextLogicalFrame = currentState.playbackPosition
                    
                    if (currentBufferIndex != -1) {
                        // We found our current frame in the buffer
                        if (currentBufferIndex + 1 < currentState.frames.size) {
                            nextBufferIndex = currentBufferIndex + 1
                            nextLogicalFrame = currentState.frameIndices[nextBufferIndex]
                        } else {
                             // End of buffer, but maybe we have more frames loading or we loop
                             // For now, let's just stay on last frame or loop to start of buffer if it's contiguous
                             // But actually we want to play smoothly.
                             // If we are at the end, let's try to wrap around to the first frame in buffer
                             // ONLY IF the buffer is contiguous (which it should be mostly)
                             nextBufferIndex = 0
                             nextLogicalFrame = currentState.frameIndices[0]
                        }
                    } else {
                        // Current playback position is not in buffer (maybe it was removed?)
                        // Jump to the first available frame in buffer
                        if (currentState.frames.isNotEmpty()) {
                            nextBufferIndex = 0
                            nextLogicalFrame = currentState.frameIndices[0]
                        }
                    }

                    if (nextBufferIndex != -1) {
                         // Update both the visual frame and the logical frame based on the buffer index
                        val updatedState = currentState.copy(
                            currentFrameIndex = nextBufferIndex,
                            currentLogicalFrame = nextLogicalFrame,
                            playbackPosition = nextLogicalFrame
                        )
                        _uiState.value = updatedState
                    }
                } else {
                    // No frames loaded yet, try to load some
                    if (!isFetching) {
                        loadNextPage()
                    }
                }

                // Continue loading frames to ensure we have enough for continuous playback
                // Load more if we're approaching the end of our loaded frames
                val lastBufferedFrame = if (currentState.frameIndices.isNotEmpty()) currentState.frameIndices.last() else 0
                
                // If we are within 15 frames of the end of the buffer, load more
                // Also ensure we track highestLoadedFrame correctly
                if (lastBufferedFrame <= _uiState.value.playbackPosition + 15 &&
                    !isFetching) {
                    loadNextPage()
                }

                val delayMs = (1000L / currentFps).toLong() // Convert FPS to milliseconds
                delay(delayMs)
            }
        }
    }

    fun stopPlayback() {
        playJob?.cancel()
        playJob = null
        _uiState.value = _uiState.value.copy(isPlaying = false)
    }

    private var currentFps = 10 // Default to 10 FPS

    fun setPlaybackSpeed(fps: Int) {
        currentFps = fps.coerceIn(1, 60) // Limit FPS between 1 and 60

        // Restart playback if currently playing to apply the new speed
        if (_uiState.value.isPlaying) {
            stopPlayback()
            startPlayback()
        }
    }

    fun togglePlayback() {
        if (_uiState.value.isPlaying) {
            stopPlayback()
        } else {
            startPlayback()
            _uiState.value = _uiState.value.copy(isPlaying = true)
        }
    }
}
