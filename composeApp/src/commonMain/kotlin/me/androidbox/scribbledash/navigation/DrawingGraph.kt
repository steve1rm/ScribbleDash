package me.androidbox.scribbledash.navigation

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import me.androidbox.scribbledash.draw.screens.DifficultyLevelScreen
import me.androidbox.scribbledash.draw.screens.DrawingScreen
import me.androidbox.scribbledash.draw.screens.DrawingViewModel
import me.androidbox.scribbledash.home.model.ScribbleDashCategories
import me.androidbox.scribbledash.home.screens.HomeScreen
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.drawingGraph(navController: NavController) {
    navigation<Route.DrawingGraph>(
        startDestination = Route.HomeScreen)
    {
        this.composable<Route.HomeScreen> {
            HomeScreen(
                onGameCardClicked = {
                    navController.navigate(
                        route = Route.DifficultyLevelScreen
                    )
                },
                onBottomNavigationClicked = { scribbleDashCategories ->
                    when(scribbleDashCategories) {
                        ScribbleDashCategories.HOME -> {

                        }
                        ScribbleDashCategories.CHART -> {
                            
                        }
                    }
                }
            )
        }

        this.composable<Route.DifficultyLevelScreen> {
            DifficultyLevelScreen(
                closeClicked = {
                    navController.navigateUp()
                },
                beginnerClicked = {
                    navController.navigate(route = Route.DrawingScreen)
                },
                challengingClicked = {
                    navController.navigate(route = Route.DrawingScreen)
                },
                masterClicked = {
                    navController.navigate(route = Route.DrawingScreen)
                }
            )
        }

        this.composable<Route.DrawingScreen> {
            val drawingViewModel = koinViewModel<DrawingViewModel>()
            val drawingState by drawingViewModel.drawingState.collectAsStateWithLifecycle()

            DrawingScreen(
                drawingState = drawingState,
                onAction = drawingViewModel::onAction,
                closeClicked = {
                    navController.navigateUp()
                }
            )
        }
    }
}