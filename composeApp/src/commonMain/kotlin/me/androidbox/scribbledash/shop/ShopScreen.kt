package me.androidbox.scribbledash.shop

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.androidbox.scribbledash.core.presentation.components.ScribbleDashLayout
import me.androidbox.scribbledash.statistics.presentation.DrawingToolSelector
import me.androidbox.scribbledash.theming.ScribbleDashTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@ExperimentalMaterial3Api
@Composable
fun ShopScreen(
    modifier: Modifier = Modifier
) {
    ScribbleDashLayout(
        modifier = modifier,
        toolBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                title = { },
                actions = {
                    // Title
                    Text(
                        text = "Shop"
                    )

                    Spacer(modifier.weight(1f))

                    Text(
                        text = "Coins"
                    )
                }
            )
        },
        content = { paddingValues ->
            DrawingToolSelector(
                modifier = Modifier.padding(paddingValues)
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ShopScreenPreview() {
    ScribbleDashTheme {
        ShopScreen()
    }
}