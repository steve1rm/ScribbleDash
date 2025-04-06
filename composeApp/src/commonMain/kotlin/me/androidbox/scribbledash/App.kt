package me.androidbox.scribbledash

import androidx.compose.runtime.Composable
import me.androidbox.scribbledash.draw.screens.CanvasScreen
import me.androidbox.scribbledash.theming.ScribbleDashTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    ScribbleDashTheme {
     //   HomeScreen()
/*        DrawingScreen(
            closeClicked = {}
        )*/
        CanvasScreen {  }
    }
}