@file:OptIn(ExperimentalMaterial3Api::class)

package me.androidbox.scribbledash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import me.androidbox.scribbledash.core.presentation.components.ScribbleDashLayout
import me.androidbox.scribbledash.home.model.ScribbleDashCategories
import me.androidbox.scribbledash.home.model.listOfNavigationItems
import me.androidbox.scribbledash.home.screens.HomeNavigationBottomBar
import me.androidbox.scribbledash.navigation.Route
import me.androidbox.scribbledash.navigation.drawingGraph
import me.androidbox.scribbledash.statistics.presentation.StatisticsScreen
import me.androidbox.scribbledash.theming.ScribbleDashTheme

@Composable
fun App() {
    ScribbleDashTheme {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        // Type-safe route check using destination.route
        val currentDestinationRoute = navBackStackEntry?.destination?.route
        val showBottomBar = currentDestinationRoute?.endsWith(Route.HomeScreen::class.simpleName.toString()) == true ||
                currentDestinationRoute?.endsWith(Route.StatisticsScreen::class.simpleName.toString()) == true

        println("currentDestinationRoute $currentDestinationRoute | ${Route.StatisticsScreen::class.simpleName}")
        ScribbleDashLayout(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background),
            toolBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background
                    ),
                    title = {
                        Text(
                            text = "ScribbleDash",
                            fontWeight = FontWeight.W400,
                            style = MaterialTheme.typography.headlineLarge,
                            fontSize = 26.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                )
            },
            content = { paddingValues ->
                NavHost(
                    navController = navController,
                    startDestination = Route.DrawingGraph,
                    modifier = Modifier.padding(paddingValues)
                ) {
                    drawingGraph(navController)
                }
            },
            bottomBar = {
                if (showBottomBar) {
                    NavigationBar(
                        modifier = Modifier.fillMaxWidth(),
                        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
                    ) {
                        var selectedIndex by rememberSaveable { mutableStateOf(0) }

                        HomeNavigationBottomBar(
                            listOfNavigationItems = listOfNavigationItems,
                            selectedItemIndex = selectedIndex,
                            onItemClicked = { category ->
                                selectedIndex = category.ordinal
                                when (category) {
                                    ScribbleDashCategories.HOME -> {
                                        navController.navigate(Route.HomeScreen) {
                                            launchSingleTop = true
                                            popUpTo(Route.HomeScreen) { inclusive = false }
                                        }
                                    }
                                    ScribbleDashCategories.CHART -> {
                                        navController.navigate(Route.StatisticsScreen) {
                                            launchSingleTop = true
                                            popUpTo(Route.HomeScreen)
                                        }
                                    }
                                }
                            }
                        )
                    }
                }
            }
        )
    }
}