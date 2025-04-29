package me.androidbox.scribbledash.draw.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import me.androidbox.scribbledash.home.screens.components.HomeCard

@Composable
fun StartDrawingScreen(
    modifier: Modifier = Modifier,
    onGameCardClicked: () -> Unit
) {
    Column(
        modifier = modifier
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

        HomeCard(
            modifier = Modifier.padding(horizontal = 16.dp),
            onGameCardClicked = onGameCardClicked
        )
    }
}