package me.androidbox.scribbledash.home.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import me.androidbox.scribbledash.core.presentation.components.ScribbleDashLayout
import me.androidbox.scribbledash.home.model.listOfNavigationItems

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {

    var selectedIndex by remember {
        mutableIntStateOf(0)
    }

    ScribbleDashLayout(
        modifier = modifier,
        toolBarTitle = "ScribbleDash",
        content = { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues = paddingValues)
            ) {

            }
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                content = {
                    this.HomeNavigationBottomBar(
                        listOfNavigationItems = listOfNavigationItems,
                        selectedItemIndex = selectedIndex,
                        onItemClicked = { index ->
                            selectedIndex = index
                        }
                    )
                }
            )
        }
    )
}