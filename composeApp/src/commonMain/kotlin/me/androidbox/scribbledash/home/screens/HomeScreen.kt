package me.androidbox.scribbledash.home.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.androidbox.scribbledash.core.presentation.components.ScribbleDashLayout

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    ScribbleDashLayout(
        modifier = modifier,
        toolBarTitle = "ScribbleDash",
        content = { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues = paddingValues)
            ) {

            }
        }
    )
}