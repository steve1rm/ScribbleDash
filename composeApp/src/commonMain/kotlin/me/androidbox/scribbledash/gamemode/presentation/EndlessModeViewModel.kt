package me.androidbox.scribbledash.gamemode.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class EndlessModeViewModel : ViewModel() {

    private val _drawingState = MutableStateFlow(DrawingState())
    val drawingState = _drawingState.asStateFlow()

    private val _eventChannel = Channel<DrawingEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()


    fun onAction(action: DrawingAction) {
        when(action) {
            DrawingAction.OnClearCanvasClicked -> TODO()
            DrawingAction.OnClose -> TODO()
            DrawingAction.OnDone -> TODO()
            is DrawingAction.OnDraw -> TODO()
            DrawingAction.OnNewPathStart -> TODO()
            DrawingAction.OnPathEnd -> TODO()
            is DrawingAction.OnSelectColor -> TODO()
            DrawingAction.Redo -> TODO()
            DrawingAction.Undo -> TODO()
        }
    }
}