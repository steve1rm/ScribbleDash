package me.androidbox.scribbledash.gamemode.presentation.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import me.androidbox.scribbledash.theming.ScribbleDashTheme
import me.androidbox.scribbledash.theming.pink
import org.jetbrains.compose.ui.tooling.preview.Preview
import scribbledash.composeapp.generated.resources.Res
import scribbledash.composeapp.generated.resources.plalet

@Composable
fun SpeedDrawFeedbackCard(
    percent: String,
    rating: String,
    description: String,
    drawingCount: String,
    shouldShowHighScore: Boolean,
    modifier: Modifier = Modifier
) {
    Box {
        ElevatedCard(
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainerHigh,
                        RoundedCornerShape(14.dp)
                    )
                    .padding(horizontal = 8.dp)
                    .padding(bottom = 16.dp, top = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ResultTitleHeader(
                    percent = percent
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = rating,
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                DisplayCounter(
                    imageRes = Res.drawable.plalet,
                    drawingCount = drawingCount,
                    backgroundColor = MaterialTheme.colorScheme.pink,
                    shouldShowHighScore = false
                )
            }
        }

        if(shouldShowHighScore) {
            HighScoreBadge(
                modifier = Modifier
                    .offset(
                        y = -(12).dp
                    )
                    .align(alignment = Alignment.TopCenter)
            )
        }
    }
}

@Preview
@Composable
fun SpeedDrawFeedbackCardPreview() {
    ScribbleDashTheme {
        SpeedDrawFeedbackCard(
            percent = "80",
            rating = "Woohoo!",
            description = "You've officially raised the bar!\nI'm going to need a ladder to reach it!\"",
            drawingCount = "10",
            shouldShowHighScore = false
        )
    }
}
