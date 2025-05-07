@file:OptIn(ExperimentalTime::class)

package me.androidbox.scribbledash.gamemode.presentation

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import me.androidbox.scribbledash.core.presentation.utils.countDownTimer
import me.androidbox.scribbledash.gamemode.presentation.utils.ParseXmlDrawable
import kotlin.time.Clock
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

class EndlessModeViewModel(
    private val parseXmlDrawable: ParseXmlDrawable,
) : ViewModel() {

    private val _drawingState = MutableStateFlow(DrawingState())
    val drawingState = _drawingState.asStateFlow()

    private val _eventChannel = Channel<DrawingEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    init {
        println("INIT VIEWMODEL")
        initializeDrawing()
    }

    fun initializeDrawing() {
        getExampleDrawing()
    }

    private fun getExampleDrawing() {
        val pathData = parseXmlDrawable.parser()

        _drawingState.update { drawingState ->
            drawingState.copy(
                exampleToDrawPath = pathData,
                exampleToSavePath = pathData,
                paths = emptyList(),
                drawingCount = drawingState.drawingCount + 1
            )
        }

        startCountdown()
    }

    private fun drawingCountdown() {
        /** update the initial time so its continuous countdown until the end, otherwise the initial time will
         *  always reset at 2.minutes */
        countDownTimer(initialTime = drawingState.value.drawingSecondsRemaining)
            .onEach { duration ->
                _drawingState.update { drawingState ->
                    drawingState.copy(
                        drawingSecondsRemaining = duration,
                        hasReachedFinalDuration = duration <= 30.seconds
                    )
                }
            }
            .onCompletion {
                /** Navigate to the result screen */
                _eventChannel.send(
                    DrawingEvent.OnDone(
                        numberOfDrawings = drawingState.value.drawingCount
                    )
                )
            }
            .launchIn(viewModelScope)
    }

    /** When the countdown for 3 seconds completes for start drawing, start the 2 minute countdown timer */
    private fun startCountdown() {
        countDownTimer(3.seconds)
            .onStart {
                _drawingState.update { drawingState ->
                    drawingState.copy(
                        isTimeToDraw = false,
                    )
                }
            }
            .onEach { second ->
                _drawingState.update { drawingState ->
                    drawingState.copy(
                        timeToDrawSecondsRemaining = second
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

                println("DRAWING RESUMED")
            }
            .launchIn(viewModelScope)
    }

    fun onAction(drawingAction: DrawingAction) {
        when(drawingAction) {
            DrawingAction.OnClearCanvasClicked -> onClearCanvas()
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
                /** For each click of done add another random example drawing */
                onClearCanvas()
                getExampleDrawing()
            }

            DrawingAction.OnClose -> {
                /** no-op */
            }
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

    private fun onClearCanvas() {
        _drawingState.update { drawingState ->
            drawingState.copy(
                currentPath = null,
                paths = emptyList(),
                undonePaths = emptyList()
            )
        }
    }

    override fun onCleared() {
        println("VIEWMODEL SpeedDrawViewModel Cleared")
        super.onCleared()
    }
}