package me.androidbox.scribbledash.draw.presentation

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import kotlin.time.Duration

data class DrawingState(
    val selectedColor: Color = Color.Black,
    val currentPath: PaintPath? = null,
    val paths: List<PaintPath> = emptyList(),
    val undonePaths: List<PaintPath> = emptyList(),
    val exampleToDrawPath: List<Path> = emptyList(),
    val exampleToSavePath: List<Path> = emptyList(),
    val vectorData: VectorData = VectorData(),
    val isTimeToDraw: Boolean = false,
    val secondsRemaining: Duration = Duration.ZERO,
    val bitmapToSave: ImageBitmap? = null
)

data class PathData(
    val pathData: String,
    val strokeWidth: Float,
    val fillColor: String,
    val strokeColor: String
)

val allColors = listOf(
    Color.Black,
    Color.Red,
    Color.Blue,
    Color.Green,
    Color.Yellow,
    Color.Magenta,
    Color.Cyan
)