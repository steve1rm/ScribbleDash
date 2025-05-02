package me.androidbox.scribbledash.navigation

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import me.androidbox.scribbledash.core.presentation.utils.getSharedViewModel
import me.androidbox.scribbledash.core.presentation.utils.observeEvents
import me.androidbox.scribbledash.gamemode.presentation.DrawingEvent
import me.androidbox.scribbledash.gamemode.presentation.DrawingViewModel
import me.androidbox.scribbledash.gamemode.presentation.FeedbackAction
import me.androidbox.scribbledash.gamemode.presentation.FeedbackViewModel
import me.androidbox.scribbledash.gamemode.presentation.screens.DifficultyLevelScreen
import me.androidbox.scribbledash.gamemode.presentation.screens.OneGameWonderScreen
import me.androidbox.scribbledash.gamemode.presentation.screens.FeedbackScreen
import me.androidbox.scribbledash.home.model.ScribbleDashCategories
import me.androidbox.scribbledash.home.screens.HomeScreen
import me.androidbox.scribbledash.statistics.presentation.StatisticsScreen
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
                }
            )
        }

        composable<Route.StatisticsScreen> {
            StatisticsScreen()
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
            val drawingViewModel = it.getSharedViewModel<DrawingViewModel>(navController)
            val drawingState by drawingViewModel.drawingState.collectAsStateWithLifecycle()

            observeEvents(
                flow = drawingViewModel.eventChannel,
                onEvent = { event ->
                    when(event) {
                        is DrawingEvent.OnDone -> {
                            println("EVENT ${event.exampleDrawing} : ${event.userPath}")
                            navController.navigate(Route.FeedbackScreen)
                        }
                    }
                }
            )

            OneGameWonderScreen(
                drawingState = drawingState,
                onAction = drawingViewModel::onAction,
                closeClicked = {
                    navController.navigateUp()
                }
            )
        }

        this.composable<Route.FeedbackScreen>(

        ) {
            val drawingViewModel = it.getSharedViewModel<DrawingViewModel>(navController)
            val feedbackViewModel = koinViewModel<FeedbackViewModel>()
            val drawingState by drawingViewModel.drawingState.collectAsStateWithLifecycle()
            val feedbackState by feedbackViewModel.feedbackState.collectAsStateWithLifecycle()

            FeedbackScreen(
                paths = drawingState.paths,
                exampleToDrawPath = drawingState.exampleToSavePath,
                feedbackState= feedbackState,
                onAction = { action ->
                    when(action) {
                        is FeedbackAction.OnRetry -> {
                            navController.navigate(Route.DrawingGraph) {
                                this.popUpTo(Route.DrawingGraph) {
                                    this.inclusive = true
                                }
                            }
                        }
                    }
                },
                closeClicked = {
                    navController.navigate(Route.HomeScreen) {
                        this.popUpTo(navController.graph.id) {
                            this.inclusive = true
                        }
                    }
                }
            )
        }
    }
}