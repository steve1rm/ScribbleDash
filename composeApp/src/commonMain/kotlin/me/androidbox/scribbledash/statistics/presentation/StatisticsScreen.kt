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
import scribbledash.composeapp.generated.resources.frame
import scribbledash.composeapp.generated.resources.lighting
import scribbledash.composeapp.generated.resources.plalet

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
                modifier = Modifier
                    .padding(paddingValues = paddingValues)
                    .padding(horizontal = 12.dp)
            ) {

                Spacer(modifier = Modifier.height(32.dp))

                StatisticsItem(
                    title = "Highest SpeedDraw accuracy %",
                    percentage = "100%",
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
                    title = "Most Meth + drawings in SpeedDraw",
                    percentage = "12",
                    icon = {
                        Icon(
                            imageVector = vectorResource(resource = Res.drawable.lighting),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))

                StatisticsItem(
                    title = "Highest Endless Mode accuracy %",
                    percentage = "100%",
                    icon = {
                        Icon(
                            imageVector = vectorResource(resource = Res.drawable.frame),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                StatisticsItem(
                    title = "Most drawings completed in Endless Mode",
                    percentage = "12",
                    icon = {
                        Icon(
                            imageVector = vectorResource(resource = Res.drawable.plalet),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                    }
                )
            }
        }
    )
}