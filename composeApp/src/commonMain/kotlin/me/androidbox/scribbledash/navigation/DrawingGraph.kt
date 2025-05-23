@file:OptIn(ExperimentalMaterial3Api::class)

package me.androidbox.scribbledash.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
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
import me.androidbox.scribbledash.gamemode.presentation.DrawingViewModel
import me.androidbox.scribbledash.gamemode.presentation.FeedbackAction
import me.androidbox.scribbledash.gamemode.presentation.FeedbackViewModel
import me.androidbox.scribbledash.gamemode.presentation.SpeedDrawViewModel
import me.androidbox.scribbledash.gamemode.presentation.screens.DifficultyLevelScreen
import me.androidbox.scribbledash.gamemode.presentation.screens.FeedbackOneGameWonderScreen
import me.androidbox.scribbledash.gamemode.presentation.screens.FinalFeedbackScreen
import me.androidbox.scribbledash.gamemode.presentation.screens.OneGameWonderScreen
import me.androidbox.scribbledash.gamemode.presentation.screens.SpeedDrawScreen
import me.androidbox.scribbledash.home.model.DifficultyLevelType
import me.androidbox.scribbledash.home.model.GameType
import me.androidbox.scribbledash.home.screens.HomeScreen
import me.androidbox.scribbledash.shop.ShopScreen
import me.androidbox.scribbledash.statistics.presentation.StatisticsData
import me.androidbox.scribbledash.statistics.presentation.StatisticsScreen
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.drawingGraph(navController: NavController) {
    navigation<Route.DrawingGraph>(
        startDestination = Route.HomeScreen)
    {
        this.composable<Route.HomeScreen> {
            HomeScreen(
                onGameCardClicked = { gameType ->
                  when(gameType) {
                      GameType.ONE_ROUND_WONDER -> {
                          navController.navigate(Route.DifficultyLevelScreen(gameType))
                      }
                      GameType.SPEED_DRAW -> {
                          navController.navigate(Route.DifficultyLevelScreen(gameType))
                      }
                      GameType.ENDLESS_MODE -> {
                          navController.navigate(Route.DifficultyLevelScreen(gameType))
                      }
                  }
                }
            )
        }

        this.composable<Route.StatisticsScreen> {
            StatisticsScreen()
        }

        this.composable<Route.ShopScreen> {
            ShopScreen()
        }

        this.composable<Route.DifficultyLevelScreen> {
            val gameType = it.toRoute<Route.DifficultyLevelScreen>().gameType

            DifficultyLevelScreen(
                closeClicked = {
                    navController.popBackStack()
                },
                beginnerClicked = {
                    when(gameType) {
                        GameType.ONE_ROUND_WONDER -> {
                            navController.navigate(route = Route.OneRoundWonderScreen(
                                DifficultyLevelType.BEGINNER))
                        }
                        GameType.SPEED_DRAW -> {
                            navController.navigate(route = Route.SpeedDrawScreen(
                                DifficultyLevelType.BEGINNER))

                        }
                        GameType.ENDLESS_MODE -> {
                            navController.navigate(route = Route.EndlessModeScreen(
                                DifficultyLevelType.BEGINNER))
                        }
                    }
                },
                challengingClicked = {
                    when(gameType) {
                        GameType.ONE_ROUND_WONDER -> {
                            navController.navigate(route = Route.OneRoundWonderScreen(
                                DifficultyLevelType.MASTER))
                        }
                        GameType.SPEED_DRAW -> {
                            navController.navigate(route = Route.SpeedDrawScreen(
                                DifficultyLevelType.MASTER))

                        }
                        GameType.ENDLESS_MODE -> {
                            navController.navigate(route = Route.EndlessModeScreen(
                                DifficultyLevelType.MASTER))
                        }
                    }
                },
                masterClicked = {
                    when(gameType) {
                        GameType.ONE_ROUND_WONDER -> {
                            navController.navigate(route = Route.OneRoundWonderScreen(
                                DifficultyLevelType.CHALLENGING))
                        }
                        GameType.SPEED_DRAW -> {
                            navController.navigate(route = Route.SpeedDrawScreen(
                                DifficultyLevelType.CHALLENGING))

                        }
                        GameType.ENDLESS_MODE -> {
                            navController.navigate(route = Route.EndlessModeScreen(
                                DifficultyLevelType.CHALLENGING))
                        }
                    }
                }
            )
        }

        this.composable<Route.OneRoundWonderScreen> {
            val drawingViewModel = it.getSharedViewModel<DrawingViewModel>(navController)
            val drawingState by drawingViewModel.drawingState.collectAsStateWithLifecycle()

            val difficultyLevelType = it.toRoute<Route.OneRoundWonderScreen>().difficultyLevelType

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
                onAction = { drawingAction ->
                    when(drawingAction) {
                        is DrawingAction.OnClose -> {
                            navController.popBackStack()
                        }
                        else -> {
                            drawingViewModel.onAction(drawingAction)
                        }
                    }
                }
            )
        }

        this.composable<Route.SpeedDrawScreen> {
            val speedDrawViewModel = koinViewModel<SpeedDrawViewModel>()
            val drawingState by speedDrawViewModel.drawingState.collectAsStateWithLifecycle()

            val difficultyLevelType = it.toRoute<Route.SpeedDrawScreen>().difficultyLevelType

            observeEvents(
                flow = speedDrawViewModel.eventChannel,
                onEvent = { event ->
                    when(event) {
                        is DrawingEvent.OnDone -> {
                            println("EVENT ${event.exampleDrawing} : ${event.userPath}")
                            StatisticsData.speedDrawCount = event.numberOfDrawings
                            StatisticsData.speedDrawAccuracy = event.numberOfDrawings
                            navController.navigate(Route.FinalFeedbackScreen(
                                drawingCount = event.numberOfDrawings,
                                percentageAccuracy = event.percentAccuracy,
                                gameType = GameType.SPEED_DRAW
                            ))
                        }
                    }
                }
            )

            SpeedDrawScreen(
                drawingState = drawingState,
                onAction = { drawingAction ->
                    when(drawingAction) {
                        is DrawingAction.OnClose -> {
                            navController.popBackStack()
                        }
                        else -> {
                            speedDrawViewModel.onAction(drawingAction)
                        }
                    }
                }
            )
        }

        this.composable<Route.FinalFeedbackScreen> {
            val gameType = it.toRoute<Route.FinalFeedbackScreen>().gameType
            val drawingCount = it.toRoute<Route.FinalFeedbackScreen>().drawingCount
            val percentAccuracy = it.toRoute<Route.FinalFeedbackScreen>().percentageAccuracy

            when(gameType) {
                GameType.ONE_ROUND_WONDER -> { /** no-op */}
                GameType.SPEED_DRAW -> {
                    StatisticsData.speedDrawAccuracy = percentAccuracy
                }
                GameType.ENDLESS_MODE -> {
                    StatisticsData.endlessDrawAccuracy = percentAccuracy
                }
            }

            FinalFeedbackScreen(
                drawingCount = drawingCount,
                percentageAccuracy = percentAccuracy,
                gameType = gameType,
                onCloseClicked = {
                    navController.navigate(Route.HomeScreen)
                }
            )
        }

        this.composable<Route.FeedbackScreen> {
            val drawingViewModel = it.getSharedViewModel<DrawingViewModel>(navController)
            val feedbackViewModel = koinViewModel<FeedbackViewModel>()
            val drawingState by drawingViewModel.drawingState.collectAsStateWithLifecycle()
            val feedbackState by feedbackViewModel.feedbackState.collectAsStateWithLifecycle()

            FeedbackOneGameWonderScreen(
                paths = drawingState.paths,
                exampleToDrawPath = drawingState.exampleToSavePath,
                feedbackState = feedbackState,
                onAction = { action ->
                    when(action) {
                        is FeedbackAction.OnRetry -> {
                            navController.navigate(Route.DrawingGraph) {
                                this.popUpTo(Route.DrawingGraph) {
                                    this.inclusive = true
                                }
                            }
                        }

                        FeedbackAction.OnFinish -> {
                            /* no-op */
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