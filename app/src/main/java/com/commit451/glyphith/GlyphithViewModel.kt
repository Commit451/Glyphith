package com.commit451.glyphith

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.commit451.glyphith.util.Util.formatTime

class GlyphithViewModel(
    application: Application
) : AndroidViewModel(application) {

    private var _uiState = createDefault()

    val uiState: LiveData<GlyphithState> = _uiState
    private var internalState = InternalState()

    private data class InternalState(
        val startTime: Long = 0,
        val stopTime: Long = 0,
    )

    private fun createDefault(): MutableLiveData<GlyphithState> {
        return MutableLiveData(GlyphithState())
    }

    fun onStartRecording() {
        internalState = internalState.copy(
            startTime = System.currentTimeMillis()
        )
        _uiState.value = _uiState.value?.copy(
            counter = "0:00",
            currentlyRecording = true,
        )
    }

    fun onStopRecording() {
        internalState = internalState.copy(
            stopTime = System.currentTimeMillis()
        )
        val counter = (internalState.stopTime - internalState.startTime).formatTime()
        _uiState.value = _uiState.value?.copy(
            counter = counter,
            currentlyRecording = false,
        )
    }

}
