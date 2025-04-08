package me.androidbox.scribbledash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import me.androidbox.scribbledash.draw.screens.CanvasScreen
import me.androidbox.scribbledash.draw.screens.DrawingViewModel
import me.androidbox.scribbledash.theming.ScribbleDashTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    ScribbleDashTheme {
     //   HomeScreen()
/*        DrawingScreen(
            closeClicked = {}
        )*/

        val drawingViewModel = koinViewModel<DrawingViewModel>()
        val state by drawingViewModel.drawingState.collectAsStateWithLifecycle()

        CanvasScreen(
            paths = state.paths,
            currentPath = state.currentPath,
            onAction = drawingViewModel::onAction,
            closeClicked = {

            }
        )
    }
}