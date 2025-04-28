package me.androidbox.scribbledash.statistics.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import me.androidbox.scribbledash.core.presentation.components.ScribbleDashLayout
import me.androidbox.scribbledash.statistics.presentation.components.StatisticsItem
import me.androidbox.scribbledash.theming.labelXLarge
import org.jetbrains.compose.resources.vectorResource
import scribbledash.composeapp.generated.resources.Res
import scribbledash.composeapp.generated.resources.eggtimer
import scribbledash.composeapp.generated.resources.lighting

@Composable
fun StatisticsScreen(
    modifier: Modifier = Modifier
) {
    ScribbleDashLayout(
        modifier = modifier,
        toolBar = {
            Text(
                text = "Statistics",
                style = MaterialTheme.typography.labelXLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues = paddingValues)
            ) {

                Spacer(modifier = Modifier.height(32.dp))

                StatisticsItem(
                    title = "Nothing to track...for now",
                    percentage = "0%",
                    icon = {
                        Icon(
                            imageVector = vectorResource(resource = Res.drawable.eggtimer),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                StatisticsItem(
                    title = "Nothing to track...for now",
                    percentage = "0",
                    icon = {
                        Icon(
                            imageVector = vectorResource(resource = Res.drawable.lighting),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                    }
                )
            }
        }
    )
}