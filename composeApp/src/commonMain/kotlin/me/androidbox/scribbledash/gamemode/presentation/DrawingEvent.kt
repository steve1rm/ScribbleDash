package me.androidbox.scribbledash.gamemode.presentation

import androidx.compose.ui.graphics.Path

sealed interface DrawingEvent {
    data class OnDone(
        val userPath: List<PaintPath> = emptyList(),
        val exampleDrawing: List<Path> = emptyList(),
        val numberOfDrawings: Int = 0,
        val percentAccuracy: Int = 0
    ) : DrawingEvent
}