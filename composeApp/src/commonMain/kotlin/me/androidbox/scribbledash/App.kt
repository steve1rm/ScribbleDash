package me.androidbox.scribbledash

import androidx.compose.runtime.Composable
import me.androidbox.scribbledash.draw.screens.CanvasScreen
import me.androidbox.scribbledash.draw.screens.DrawingScreen
import me.androidbox.scribbledash.theming.Theme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    Theme {
     //   HomeScreen()
/*        DrawingScreen(
            closeClicked = {}
        )*/
        CanvasScreen {  }
    }
}