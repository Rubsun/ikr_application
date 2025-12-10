package com.example.ikr_application.michaelnoskov.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ikr_application.michaelnoskov.domain.CurrentDateUseCase
import com.example.ikr_application.michaelnoskov.domain.ElapsedTimeUseCase
import com.example.ikr_application.michaelnoskov.domain.TimePrecisions
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class MyViewModel : ViewModel() {
    private val currentDateUseCase = CurrentDateUseCase()
    private val elapsedTimeUseCase = ElapsedTimeUseCase()

    private val _currentTimeText = MutableLiveData<String>()
    val currentTimeText: LiveData<String> = _currentTimeText

    private val _elapsedTimeText = MutableLiveData<String>()
    val elapsedTimeText: LiveData<String> = _elapsedTimeText

    private val _circleColor = MutableLiveData<Int>()
    val circleColor: LiveData<Int> = _circleColor

    private val _currentPrecision = MutableLiveData<TimePrecisions>()
    val currentPrecision: LiveData<TimePrecisions> = _currentPrecision

    private var updateJob: Job? = null
    private var currentPrecisionIndex = 0

    init {
        _currentPrecision.value = TimePrecisions.S
        startTimeUpdates()
    }

    fun startTimeUpdates() {
        updateJob?.cancel()
        updateJob = viewModelScope.launch {
            while (true) {
                updateAllTimeData()
                delay(100)
            }
        }
    }

    fun stopTimeUpdates() {
        updateJob?.cancel()
        updateJob = null
    }

    fun changePrecision() {
        val allPrecisions = timePrecisions()
        currentPrecisionIndex = (currentPrecisionIndex + 1) % allPrecisions.size
        _currentPrecision.value = allPrecisions[currentPrecisionIndex]
    }

    private fun updateAllTimeData() {
        val currentDate = currentDateUseCase.date()

        val timeFormat = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())
        _currentTimeText.value = "Current: ${timeFormat.format(currentDate)}"

        val precision = _currentPrecision.value ?: TimePrecisions.S

        val elapsedValue = elapsedTimeUseCase.value(precision)
        _elapsedTimeText.value = "Elapsed: $elapsedValue ${precision.typeName}"

        val elapsedMs = elapsedTimeUseCase.value(TimePrecisions.MS)
        updateCircleColor(elapsedMs, precision)
    }

    private fun updateCircleColor(elapsedMs: Long, precision: TimePrecisions) {

        when (precision) {
            TimePrecisions.MS -> {
                val colorIndex = (elapsedMs / 500) % 3
                _circleColor.value = getColorByIndex(colorIndex)
            }
            TimePrecisions.S -> {
                val seconds = elapsedMs / 1000
                val colorIndex = seconds % 3
                _circleColor.value = getColorByIndex(colorIndex)
            }
            TimePrecisions.M -> {
                val hours = elapsedMs / (60 * 1000)
                val colorIndex = hours % 3
                _circleColor.value = getColorByIndex(colorIndex)
            }
            TimePrecisions.H -> {
                val hours = elapsedMs / (60 * 60 * 1000)
                val colorIndex = hours % 3
                _circleColor.value = getColorByIndex(colorIndex)
            }
        }
    }

    private fun getColorByIndex(index: Long): Int {
        return when (index % 3) {
            0L -> android.graphics.Color.RED
            1L -> android.graphics.Color.GREEN
            2L -> android.graphics.Color.BLUE
            else -> android.graphics.Color.GRAY
        }
    }

    fun timePrecisions(): List<TimePrecisions> {
        return TimePrecisions.entries
    }

    fun getCurrentPrecisionName(): String {
        return _currentPrecision.value?.typeName ?: "s"
    }

    override fun onCleared() {
        super.onCleared()
        stopTimeUpdates()
    }
}