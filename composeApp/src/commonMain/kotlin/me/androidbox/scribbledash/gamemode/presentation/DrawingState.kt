package me.androidbox.scribbledash.gamemode.presentation

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

data class DrawingState(
    val selectedColor: Color = Color.Black,
    val currentPath: PaintPath? = null,
    val paths: List<PaintPath> = emptyList(),
    val undonePaths: List<PaintPath> = emptyList(),
    val exampleToDrawPath: List<Path> = emptyList(),
    val exampleToSavePath: List<Path> = emptyList(),
    val vectorData: VectorData = VectorData(),
    val isTimeToDraw: Boolean = false,
    val timeToDrawSecondsRemaining: Duration = Duration.ZERO,
    val drawingSecondsRemaining: Duration = 1.minutes,
    val countDownSecondsTextColor: Color = Color.Black,
    val hasReachedFinalDuration: Boolean = false,
    val bitmapToSave: ImageBitmap? = null,
    val drawingCount: Int = 0,
    val shouldPauseDrawingCountdown: Boolean = false
)

data class PathData(
    val pathData: String,
    val strokeWidth: Float,
    val fillColor: String,
    val strokeColor: String
)