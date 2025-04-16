package me.androidbox.scribbledash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import me.androidbox.scribbledash.draw.presentation.screens.components.DrawingCanvas

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DrawingCanvasPreview() {
    DrawingCanvas(
        paths = emptyList(),
        currentPath = null,
        onAction = {}
    )
}

@Preview
@Composable
fun AppAndroidPreview() {
   // App()
}