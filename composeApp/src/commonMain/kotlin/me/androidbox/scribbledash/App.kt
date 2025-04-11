package me.androidbox.scribbledash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import me.androidbox.scribbledash.draw.screens.DrawingScreen
import me.androidbox.scribbledash.draw.screens.DrawingViewModel
import me.androidbox.scribbledash.navigation.Route
import me.androidbox.scribbledash.navigation.drawingGraph
import me.androidbox.scribbledash.theming.ScribbleDashTheme
import org.koin.compose.viewmodel.koinViewModel

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

     /*
     //   HomeScreen()
*//*        DrawingScreen(
            closeClicked = {}
        )*//*

        val drawingViewModel = koinViewModel<DrawingViewModel>()
        val state by drawingViewModel.drawingState.collectAsStateWithLifecycle()

        DrawingScreen(
            paths = state.paths,
            currentPath = state.currentPath,
            onAction = drawingViewModel::onAction,
            closeClicked = {

            }
        )*/
    }
}