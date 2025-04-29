package me.androidbox.scribbledash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import me.androidbox.scribbledash.core.presentation.components.ScribbleDashLayout
import me.androidbox.scribbledash.home.model.ScribbleDashCategories
import me.androidbox.scribbledash.home.model.listOfNavigationItems
import me.androidbox.scribbledash.home.screens.HomeNavigationBottomBar
import me.androidbox.scribbledash.navigation.Route
import me.androidbox.scribbledash.navigation.drawingGraph
import me.androidbox.scribbledash.navigation.routeName
import me.androidbox.scribbledash.theming.ScribbleDashTheme

@Composable
fun App() {
    ScribbleDashTheme {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        // Type-safe route check using destination.route
        val currentDestinationRoute = navBackStackEntry?.destination?.route
        val showBottomBar = currentDestinationRoute?.endsWith(Route.HomeScreen.routeName) == true ||
                currentDestinationRoute?.endsWith(Route.StatisticsScreen.routeName) == true

        println("currentDestinationRoute $currentDestinationRoute | ${Route.StatisticsScreen::class.simpleName}")
        ScribbleDashLayout(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background),
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
                        var selectedIndex by rememberSaveable { mutableStateOf(1) }

                        HomeNavigationBottomBar(
                            listOfNavigationItems = listOfNavigationItems,
                            selectedItemIndex = selectedIndex,
                            onItemClicked = { category ->
                                selectedIndex = category.ordinal

                                when (category) {
                                    ScribbleDashCategories.CHART -> {
                                        if (currentDestinationRoute != Route.HomeScreen.routeName) {
                                            navController.navigate(Route.HomeScreen) {
                                                launchSingleTop = true
                                                popUpTo(Route.HomeScreen.routeName) {
                                                    inclusive = false
                                                }
                                            }
                                        }
                                    }

                                    ScribbleDashCategories.HOME -> {
                                        if (currentDestinationRoute != Route.StatisticsScreen.routeName) {
                                            navController.navigate(Route.StatisticsScreen) {
                                                launchSingleTop = true
                                                popUpTo(Route.HomeScreen.routeName)
                                            }
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