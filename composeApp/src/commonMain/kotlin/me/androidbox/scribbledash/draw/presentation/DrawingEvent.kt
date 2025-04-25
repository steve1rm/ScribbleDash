package me.androidbox.scribbledash.draw.presentation

import androidx.compose.ui.graphics.Path

sealed interface DrawingEvent {
    data class OnDone(
        val userPath: String,
        val exampleDrawing: List<Path>
    ) : DrawingEvent
}