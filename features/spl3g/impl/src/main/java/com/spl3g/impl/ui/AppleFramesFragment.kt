package com.spl3g.impl.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.spl3g.impl.R
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AppleFramesFragment : Fragment() {

    private val viewModel: AppleFramesViewModel by viewModel()
    private lateinit var currentFrameImageView: ImageView
    private lateinit var currentFrameLabel: TextView
    private lateinit var playPauseButton: Button
    private lateinit var speedSlider: SeekBar
    private lateinit var speedLabel: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.content_spl3g_content, container, false)
        currentFrameImageView = view.findViewById(R.id.apple_frame_image)
        currentFrameLabel = view.findViewById(R.id.current_frame_label)
        playPauseButton = view.findViewById(R.id.play_pause_button)
        speedSlider = view.findViewById(R.id.speed_slider)
        speedLabel = view.findViewById(R.id.speed_label)
        playPauseButton.text = "Play"
        progressBar = view.findViewById(R.id.progress_bar)

        playPauseButton.setOnClickListener {
            Log.d("AppleFramesFragment", "Play/Pause button clicked")
            viewModel.togglePlayback()
        }

        // Setup speed slider
        speedSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser && progress > 0) {  // Ensure we don't have 0 FPS
                    val fps = progress
                    viewModel.setPlaybackSpeed(fps)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    progressBar.isVisible = state.isLoading

                    if (state.frames.isNotEmpty() && state.currentFrameIndex < state.frames.size) {
                        val currentFrame = state.frames[state.currentFrameIndex]
                        // Only display the frame if it's not a tiny placeholder (indicating a real frame)
                        if (currentFrame.width > 1 && currentFrame.height > 1) {
                            currentFrameImageView.setImageBitmap(currentFrame)
                        }
                    }

                    // Update the current frame label
                    currentFrameLabel.text = "Frame: ${state.currentLogicalFrame}/${state.highestLoadedFrame}"
                    playPauseButton.text = if (state.isPlaying) "Pause" else "Play"

                    if (speedSlider.progress != state.fps) {
                        speedSlider.progress = state.fps
                    }
                    speedLabel.text = "Speed: ${state.fps} FPS"

                    if (state.error != null) {
                        Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        // Start loading frames
        if (viewModel.uiState.value.frames.isEmpty()) {
            viewModel.preloadFrames()  // Preload initial frames for playback
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("AppleFramesFragment", "Stopping playback in onDestroyView")
        viewModel.stopPlayback()
    }
}
