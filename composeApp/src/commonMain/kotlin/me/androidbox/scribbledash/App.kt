@file:OptIn(ExperimentalMaterial3Api::class)

package me.androidbox.scribbledash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import me.androidbox.scribbledash.core.presentation.components.ScribbleDashLayout
import me.androidbox.scribbledash.home.model.ScribbleDashCategories
import me.androidbox.scribbledash.home.model.listOfNavigationItems
import me.androidbox.scribbledash.home.screens.HomeNavigationBottomBar
import me.androidbox.scribbledash.navigation.Route
import me.androidbox.scribbledash.navigation.drawingGraph
import me.androidbox.scribbledash.theming.ScribbleDashTheme

@Composable
fun App() {
    ScribbleDashTheme {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        var selectedIndex by remember {
            mutableIntStateOf(0)
        }

        ScribbleDashLayout(
            modifier = Modifier.background(color = MaterialTheme.colorScheme.background),
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
            content = { paddingValues -> //**NavHost will be within the content**
                NavHost(
                    modifier = Modifier,
                    navController = navController,
                    startDestination = Route.DrawingGraph
                ) {
                    this.drawingGraph(navController)
                }
            },
            bottomBar = {
                // Conditionally show the bottom bar
                if (currentRoute == Route.HomeScreen || currentRoute == Route.StatisticsScreen) {
                    NavigationBar(
                        modifier = Modifier.fillMaxWidth(),
                        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                        content = {
                            this.HomeNavigationBottomBar(
                                selectedItemIndex = selectedIndex,
                                listOfNavigationItems = listOfNavigationItems,
                                onItemClicked = { scribbleDashCategories ->
                                    selectedIndex = scribbleDashCategories.ordinal
                                    navigateToRoute(navController = navController, scribbleDashCategories = scribbleDashCategories)
                                }
                            )
                        }
                    )
                }


                /*NavigationBar(
                    modifier = Modifier.fillMaxWidth(),
                    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                    content = {
                        this.HomeNavigationBottomBar(
                            selectedItemIndex = selectedIndex,
                            listOfNavigationItems = listOfNavigationItems
                           *//* onItemClicked = { scribbleDashCategories ->
                                selectedIndex = scribbleDashCategories.ordinal
                                // onBottomNavigationClicked(scribbleDashCategories) // This is no longer needed
                            }*//*
                        )
                    }
                )*/
            }
        )
    }
}

private fun navigateToRoute(
    navController: NavHostController,
    scribbleDashCategories: ScribbleDashCategories
) {
    when (scribbleDashCategories) {
        ScribbleDashCategories.HOME -> {
            navController.navigate(Route.HomeScreen)
        }

        ScribbleDashCategories.CHART -> {
            navController.navigate(Route.StatisticsScreen)
        }
    }
}