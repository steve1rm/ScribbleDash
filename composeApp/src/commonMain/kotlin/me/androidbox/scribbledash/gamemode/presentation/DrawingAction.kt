package me.androidbox.scribbledash.gamemode.presentation

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

sealed interface DrawingAction {
    data object OnNewPathStart : DrawingAction
    data class OnDraw(val offset: Offset) : DrawingAction
    data object OnPathEnd : DrawingAction
    data object Undo : DrawingAction
    data object Redo : DrawingAction
    data object OnClearCanvasClicked: DrawingAction
    data class OnSelectColor(val color: Color) : DrawingAction
    data object OnDone : DrawingAction
    data object OnClose : DrawingAction
}