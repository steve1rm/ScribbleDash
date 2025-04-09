@file:OptIn(ExperimentalTime::class)

package me.androidbox.scribbledash.draw.screens

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class DrawingViewModel : ViewModel() {

    private val _drawingState = MutableStateFlow(DrawingState())
    val drawingState = _drawingState.asStateFlow()

    fun onAction(drawingAction: DrawingAction) {
        when(drawingAction) {
            DrawingAction.OnClearCanvasClicked -> onClearCanvasClick()
            is DrawingAction.OnDraw -> onDraw(drawingAction.offset)
            DrawingAction.OnNewPathStart -> onNewPathStart()
            DrawingAction.OnPathEnd -> onPathEnd()
            is DrawingAction.OnSelectColor -> onSelectColor(drawingAction.color)
            DrawingAction.Redo -> {
                onRedo()
            }
            DrawingAction.Undo -> {
                onUndo()
            }
        }
    }

    private fun onUndo() {
        _drawingState.update { currentState ->
            if (currentState.paths.isNotEmpty()) {
                val pathToUndo = currentState.paths.last()
                currentState.copy(
                    paths = currentState.paths.dropLast(1), // Remove last path
                    undonePaths = currentState.undonePaths + pathToUndo // Add to undone list
                )
            } else {
                currentState // No change if nothing to undo
            }
        }
    }

    private fun onRedo() {
        _drawingState.update { currentState ->
            if (currentState.undonePaths.isNotEmpty()) {
                val pathToRedo = currentState.undonePaths.last()
                currentState.copy(
                    undonePaths = currentState.undonePaths.dropLast(1), // Remove from undone list
                    paths = currentState.paths + pathToRedo // Add back to visible paths
                )
            } else {
                currentState // No change if nothing to redo
            }
        }
    }

    val canUndo: Boolean
        get() {
            return drawingState.value.paths.isNotEmpty()
        }

    val canRedo: Boolean
        get() {
            return drawingState.value.paths.isNotEmpty()
        }

    private fun onSelectColor(color: Color) {
        TODO("Not yet implemented")
    }

    private fun onPathEnd() {
        val currentPathData = drawingState.value.currentPath ?: return

        _drawingState.update { drawingState ->
            drawingState.copy(
                currentPath = null,
                paths = drawingState.paths + currentPathData
            )
        }
    }

    private fun onNewPathStart() {
        _drawingState.update { drawingState ->
            drawingState.copy(
                currentPath = PathData(
                    id = Clock.System.now().nanosecondsOfSecond.toString(),
                    color = drawingState.selectedColor,
                    path = emptyList()
                )
            )
        }
    }

    private fun onDraw(offset: Offset) {
        if(drawingState.value.currentPath != null) {
            _drawingState.update { drawingState ->
                drawingState.copy(
                    currentPath = drawingState.currentPath?.copy(
                        path = drawingState.currentPath.path + offset
                    )
                )
            }
        }
    }

    private fun onClearCanvasClick() {
        _drawingState.update { drawingState ->
            drawingState.copy(
                currentPath = null,
                paths = emptyList()
            )
        }
    }
}