package me.androidbox.scribbledash.navigation

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import me.androidbox.scribbledash.core.presentation.utils.getSharedViewModel
import me.androidbox.scribbledash.core.presentation.utils.observeEvents
import me.androidbox.scribbledash.gamemode.presentation.DrawingAction
import me.androidbox.scribbledash.gamemode.presentation.DrawingEvent
import me.androidbox.scribbledash.gamemode.presentation.EndlessModeViewModel
import me.androidbox.scribbledash.gamemode.presentation.FeedbackAction
import me.androidbox.scribbledash.gamemode.presentation.FeedbackViewModel
import me.androidbox.scribbledash.gamemode.presentation.screens.EndlessModeScreen
import me.androidbox.scribbledash.gamemode.presentation.screens.FeedbackEndlessModeScreen
import me.androidbox.scribbledash.home.model.DifficultyLevelType
import me.androidbox.scribbledash.statistics.presentation.StatisticsData
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.endlessModeGraph(
    navController: NavController
) {
    this.navigation<Route.EndlessModeGraph>(
        startDestination = Route.EndlessModeScreen(difficultyLevelType = DifficultyLevelType.BEGINNER)
    ) {
        this.composable<Route.EndlessModeScreen> {
            val endlessModeViewModel = it.getSharedViewModel<EndlessModeViewModel>(navController)
            val drawingState by endlessModeViewModel.drawingState.collectAsStateWithLifecycle()

            val difficultyLevelType = it.toRoute<Route.EndlessModeScreen>().difficultyLevelType

            observeEvents(
                flow = endlessModeViewModel.eventChannel,
                onEvent = { drawingEvent ->
                    when (drawingEvent) {
                        is DrawingEvent.OnDone -> {
                            navController.navigate(
                                Route.FeedbackEndlessModeScreen(
                                    drawingState.drawingCount
                                )
                            )
                        }
                    }
                })

            /** Using saved state handle */
            val drawingCount = it.savedStateHandle.get<Int>("drawing_count") ?: 0

            EndlessModeScreen(
                drawingState = drawingState,
                onAction = { drawingAction ->
                    when (drawingAction) {
                        /* DrawingAction.OnDone -> {
                             StatisticsData.endlessDrawingCount = drawingState.drawingCount + 1
                             endlessModeViewModel.onClearCanvas()
                             navController.navigate(Route.FeedbackEndlessModeScreen)
                         }*/
                        DrawingAction.OnClose -> {
                            navController.popBackStack()
                        }

                        else -> {
                            endlessModeViewModel.onAction(drawingAction)
                        }
                    }
                }
            )
        }

        this.composable<Route.FeedbackEndlessModeScreen> {
            val endlessModeViewModel = it.getSharedViewModel<EndlessModeViewModel>(navController)
            val feedbackViewModel = koinViewModel<FeedbackViewModel>()
            val drawingState by endlessModeViewModel.drawingState.collectAsStateWithLifecycle()
            val feedbackState by feedbackViewModel.feedbackState.collectAsStateWithLifecycle()

            val drawingCount = it.toRoute<Route.FeedbackEndlessModeScreen>().drawingCount

            FeedbackEndlessModeScreen(
                paths = drawingState.paths,
                exampleToDrawPath = drawingState.exampleToSavePath,
                feedbackState = feedbackState,
                onAction = { action ->
                    when (action) {
                        is FeedbackAction.OnRetry -> {
                            //  endlessModeViewModel.initializeDrawing()
                            navController.previousBackStackEntry
                                ?.savedStateHandle
                                ?.set("drawing_count", drawingState.drawingCount)

                            println("Current Back Stack before navigateUp: ${navController.currentBackStack.value.joinToString { it.destination.route ?: "no_route" }}")
                            println("Current Destination: ${navController.currentDestination?.route}")
                            navController.navigateUp()
                        }

                        FeedbackAction.OnFinish -> {
                            StatisticsData.endlessDrawingCount = drawingState.drawingCount
                            navController.navigate(Route.FinalFeedbackScreen(StatisticsData.endlessDrawingCount))
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