@file:OptIn(ExperimentalTime::class)

package me.androidbox.scribbledash.draw.presentation

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.androidbox.scribbledash.core.presentation.utils.ExampleDrawings
import me.androidbox.scribbledash.core.presentation.utils.countDownTimer
import me.androidbox.scribbledash.draw.data.SaveBitmapDrawing
import me.androidbox.scribbledash.draw.presentation.utils.ParseXmlDrawable
import kotlin.time.Clock
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

class DrawingViewModel(
    parseXmlDrawable: ParseXmlDrawable,
    private val saveBitmapDrawing: SaveBitmapDrawing
) : ViewModel() {

    private val _drawingState = MutableStateFlow(DrawingState())
    val drawingState = _drawingState.asStateFlow()

    init {
        val pathData = parseXmlDrawable.parser(ExampleDrawings.ALIEN.drawableName.lowercase())
        _drawingState.update { drawingState ->
            drawingState.copy(
                exampleToDrawPath = pathData,
                exampleToSavePath = pathData
            )
        }

        startCountdown()
    }

    private fun startCountdown() {
        countDownTimer(3.seconds)
            .onEach { second ->
                _drawingState.update { drawingState ->
                    drawingState.copy(
                        secondsRemaining = second
                    )
                }
            }
            .onCompletion {
                _drawingState.update { drawingState ->
                    drawingState.copy(
                        isTimeToDraw = true,
                        exampleToDrawPath = emptyList()
                    )
                }
            }
            .launchIn(viewModelScope)
    }

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

            DrawingAction.OnDone -> {
                saveBitmap()
            }
        }
    }

    private fun saveBitmap() {
        val examplePath = drawingState.value.exampleToSavePath

        if(examplePath.isNotEmpty()) {
            generateBitmapFromPaths1(examplePath, 11f)
        }
    }

    private fun generateBitmapFromPaths1(paths: List<Path>, scale: Float) {
        val originalWidth = 1155f // Or your actual unscaled width as Float
        val originalHeight = 1155f // Or your actual unscaled height as Float
        val width = (originalWidth * scale).toInt()
        val height = (originalHeight * scale).toInt()

        val bitmap = ImageBitmap(width, height)
        val canvas = Canvas(bitmap)

        val paint = Paint().apply {
            color = Color.Black
            style = PaintingStyle.Stroke
            strokeWidth = 30f  // Use a fixed stroke width, as paths are scaled
            strokeCap = StrokeCap.Round
        }

        paths.forEach { originalPath ->
            val scaledPath = Path().apply {
                // Create a new path and scale all of the points to the new scaled canvas

                val pathMeasure = PathMeasure()
                pathMeasure.setPath(originalPath, false)
                val pathLength = pathMeasure.length

                var currentDistance = 0f
                while (currentDistance < pathLength) {
                    val position = pathMeasure.getPosition(currentDistance)
                    val scaledX = position.x * scale
                    val scaledY = position.y * scale

                    if (currentDistance == 0f) {
                        moveTo(scaledX, scaledY)
                    } else {
                        lineTo(scaledX, scaledY)
                    }
                    currentDistance += 1f  // Adjust step as needed for smoother scaling
                }
            }

            canvas.drawPath(scaledPath, paint)
        }

        viewModelScope.launch {
            val path = saveBitmapDrawing.saveDrawing(bitmap)
            println(path)
        }
    }

    private fun generateBitmapFromPaths(paths: List<Path>) {
        val width = 1155
        val height = 1155

        val bitmap = ImageBitmap(width, height)
        val canvas = Canvas(bitmap)

        val paint = Paint()

        paint.color = Color.Black
        paint.style = PaintingStyle.Stroke
        paint.strokeWidth = 3f

        paths.forEach { path ->
            canvas.drawPath(
                path = path,
                paint = paint)
        }

        _drawingState.update { drawingState ->
            drawingState.copy(
                bitmapToSave = bitmap
            )
        }

        viewModelScope.launch {
            val path = saveBitmapDrawing.saveDrawing(bitmap)
            println(path)
        }
    }

    private fun onUndo() {
        _drawingState.update { currentState ->
            if (currentState.paths.isNotEmpty()) {
                val pathToUndo = currentState.paths.last()

                /** Only keep only 5 items in the undo list */
                val undoList = if(currentState.undonePaths.count() < 5) {
                    currentState.undonePaths + pathToUndo
                }
                else {
                    /** When we reach the peak, start dropping items from the
                     *  front of the list, and add the new item to the end */
                    currentState.undonePaths.drop(1) + pathToUndo
                }

                currentState.copy(
                    /** Remove last path */
                    paths = currentState.paths.dropLast(1),
                    /** Add to undone list */
                    undonePaths = undoList
                )
            } else {
                currentState
            }
        }
    }

    private fun onRedo() {
        _drawingState.update { currentState ->
            if (currentState.undonePaths.isNotEmpty()) {
                /** Get the last path added */
                val pathToRedo = currentState.undonePaths.last()
                currentState.copy(
                    /** Remove from undone list */
                    undonePaths = currentState.undonePaths.dropLast(1),
                    /** Add back to visible paths */
                    paths = currentState.paths + pathToRedo
                )
            } else {
                /**  No change if nothing to redo */
                currentState
            }
        }
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
                currentPath = PaintPath(
                    id = Clock.System.now().nanosecondsOfSecond.toString(),
                    color = drawingState.selectedColor,
                    path = emptyList(),
                    strokeWidth = 1f
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
                paths = emptyList(),
                undonePaths = emptyList()
            )
        }
    }
}