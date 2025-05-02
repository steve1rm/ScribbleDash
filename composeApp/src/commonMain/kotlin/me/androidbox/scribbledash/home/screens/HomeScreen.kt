@file:OptIn(ExperimentalMaterial3Api::class)

package me.androidbox.scribbledash.home.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.scribbledash.core.presentation.components.ScribbleDashLayout
import me.androidbox.scribbledash.home.screens.components.HomeGameCard
import me.androidbox.scribbledash.theming.success
import org.jetbrains.compose.resources.vectorResource
import scribbledash.composeapp.generated.resources.Res
import scribbledash.composeapp.generated.resources.endless_mode
import scribbledash.composeapp.generated.resources.one_round_wonder
import scribbledash.composeapp.generated.resources.speed_draw

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onGameCardClicked: () -> Unit
) {

    ScribbleDashLayout(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.background),
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
            Column(
                modifier = Modifier
                    .padding(paddingValues = paddingValues)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(64.dp))

                Text(
                    text = "Start drawing",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.W400)

                Text(
                    text = "Select game mode",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.W400)

                Spacer(modifier = Modifier.height(32.dp))

                HomeGameCard(
                    title = "One Round\nWonder",
                    borderColor = success,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    image = {
                        Image(
                            modifier = Modifier
                                .align(Alignment.End),
                            imageVector = vectorResource(resource = Res.drawable.one_round_wonder),
                            contentDescription = null
                        )
                    },
                    onGameCardClicked = onGameCardClicked
                )

                Spacer(modifier = Modifier.height(16.dp))

                HomeGameCard(
                    title = "Speed\nDraw",
                    borderColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    image = {
                        Image(
                            modifier = Modifier
                                .align(Alignment.End),
                            imageVector = vectorResource(resource = Res.drawable.speed_draw),
                            contentDescription = null
                        )
                    },
                    onGameCardClicked = onGameCardClicked
                )

                Spacer(modifier = Modifier.height(16.dp))

                HomeGameCard(
                    title = "Endless\nMode",
                    borderColor = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    image = {
                        Image(
                            modifier = Modifier
                                .align(Alignment.End),
                            imageVector = vectorResource(resource = Res.drawable.endless_mode),
                            contentDescription = null
                        )
                    },
                    onGameCardClicked = onGameCardClicked
                )
            }
        }
    )
}