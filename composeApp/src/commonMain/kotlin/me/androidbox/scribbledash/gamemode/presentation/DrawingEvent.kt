package me.androidbox.scribbledash.gamemode.presentation

import androidx.compose.ui.graphics.Path

sealed interface DrawingEvent {
    data class OnDone(
        val userPath: List<PaintPath>,
        val exampleDrawing: List<Path>
    ) : DrawingEvent
}