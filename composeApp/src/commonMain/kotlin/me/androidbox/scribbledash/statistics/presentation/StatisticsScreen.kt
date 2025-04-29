@file:OptIn(ExperimentalMaterial3Api::class)

package me.androidbox.scribbledash.statistics.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.scribbledash.core.presentation.components.ScribbleDashLayout
import me.androidbox.scribbledash.statistics.presentation.components.StatisticsItem
import me.androidbox.scribbledash.theming.labelXLarge
import org.jetbrains.compose.resources.vectorResource
import scribbledash.composeapp.generated.resources.Res
import scribbledash.composeapp.generated.resources.eggtimer

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

        }
    )
}