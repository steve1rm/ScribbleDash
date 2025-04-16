package me.androidbox.scribbledash

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import me.androidbox.scribbledash.navigation.Route
import me.androidbox.scribbledash.navigation.drawingGraph
import me.androidbox.scribbledash.theming.ScribbleDashTheme

@Composable
fun App() {
    ScribbleDashTheme {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Route.DrawingGraph
        ) {
            this.drawingGraph(navController)
        }
    }
}