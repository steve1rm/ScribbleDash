@file:OptIn(ExperimentalTime::class)

package me.androidbox.scribbledash.gamemode.presentation

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

data class ParsedPath(
    val paths: List<PathData>,
    val width: Int,
    val height: Int,
    val viewportWidth: Float,
    val viewportHeight: Float
)

data class PaintPath(
    val id: String = Clock.System.now().toEpochMilliseconds().toString(),
    val path: List<Offset>,
    val color: Color = Color.Black,
    val strokeWidth: Float,
    val strokeCap: StrokeCap = StrokeCap.Round,
    val strokeJoin: StrokeJoin = StrokeJoin.Round
)