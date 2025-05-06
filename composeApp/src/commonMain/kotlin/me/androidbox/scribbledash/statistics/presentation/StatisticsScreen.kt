@file:OptIn(ExperimentalMaterial3Api::class)

package me.androidbox.scribbledash.statistics.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.scribbledash.core.presentation.components.ScribbleDashLayout
import me.androidbox.scribbledash.statistics.presentation.components.StatisticsItem
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
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                title = {
                    Text(
                        text = "Statistics",
                        fontWeight = FontWeight.W400,
                        style = MaterialTheme.typography.headlineLarge,
                        fontSize = 26.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
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