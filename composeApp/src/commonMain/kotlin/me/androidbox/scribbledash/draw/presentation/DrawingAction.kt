package me.androidbox.scribbledash.draw.presentation

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlin.time.Duration

sealed interface DrawingAction {
    data object OnNewPathStart : DrawingAction
    data class OnDraw(val offset: Offset) : DrawingAction
    data object OnPathEnd : DrawingAction
    data object Undo : DrawingAction
    data object Redo : DrawingAction
    data object OnClearCanvasClicked: DrawingAction
    data class OnSelectColor(val color: Color) : DrawingAction
}