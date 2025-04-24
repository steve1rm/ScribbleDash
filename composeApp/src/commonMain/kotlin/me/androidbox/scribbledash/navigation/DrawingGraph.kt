package me.androidbox.scribbledash.navigation

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import me.androidbox.scribbledash.core.presentation.utils.observeEvents
import me.androidbox.scribbledash.draw.presentation.DrawingEvent
import me.androidbox.scribbledash.draw.presentation.DrawingViewModel
import me.androidbox.scribbledash.draw.presentation.FeedbackViewModel
import me.androidbox.scribbledash.draw.presentation.screens.DifficultyLevelScreen
import me.androidbox.scribbledash.draw.presentation.screens.DrawingScreen
import me.androidbox.scribbledash.draw.presentation.screens.FeedbackScreen
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

            DrawingScreen(
                drawingState = drawingState,
                onAction = drawingViewModel::onAction,
                closeClicked = {
                    navController.navigateUp()
                }
            )
        }

        this.composable<Route.FeedbackScreen>(

        ) {
            val drawingViewModel = koinViewModel<FeedbackViewModel>()
      //      val drawingState by drawingViewModel.drawingState.collectAsStateWithLifecycle()

          //  val name = it.toRoute<Route.FeedbackScreen>().exampleToDrawPath

            FeedbackScreen(
     //           drawingState = drawingState,
                onAction = drawingViewModel::onAction,
                closeClicked = {
                    navController.navigateUp()
                }
            )
        }
    }
}