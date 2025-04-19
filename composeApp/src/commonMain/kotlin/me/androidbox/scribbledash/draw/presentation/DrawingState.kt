package me.androidbox.scribbledash.draw.presentation

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import kotlin.time.Duration

data class DrawingState(
    val selectedColor: Color = Color.Black,
    val currentPath: PathData? = null,
    val paths: List<PathData> = emptyList(),
    val undonePaths: List<PathData> = emptyList(),
    val examplePath: List<Path> = emptyList(),
    val vectorData: VectorData = VectorData(),
    val isTimeToDraw: Boolean = false,
    val secondsRemaining: Duration = Duration.ZERO
)

data class PathData(
    val id: String,
    val color: Color,
    val path: List<Offset>
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