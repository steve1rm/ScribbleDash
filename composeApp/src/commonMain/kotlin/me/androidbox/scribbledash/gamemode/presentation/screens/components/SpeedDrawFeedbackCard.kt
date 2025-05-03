package me.androidbox.scribbledash.gamemode.presentation.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import me.androidbox.scribbledash.theming.headLineXSmall
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import scribbledash.composeapp.generated.resources.Res
import scribbledash.composeapp.generated.resources.high_score

@Composable
fun SpeedDrawFeedbackCard(
    modifier: Modifier = Modifier
) {

}

@Composable
fun HighScoreBadge(
    modifier: Modifier = Modifier) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        Row{
            Spacer(modifier = Modifier.width(14.dp))

            Row(
                modifier = Modifier
                    .height(height = 30.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(Color(0xFFFFDA35), Color(0xFFFF9600))
                        ),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .border(width = 2.dp, color = Color.White, shape = RoundedCornerShape(16.dp))
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "New High Score",
                    style = MaterialTheme.typography.headLineXSmall,
                    color = Color.White
                )
            }
        }

        Image(
            modifier = Modifier.align(Alignment.CenterStart).size(28.dp),
            imageVector = vectorResource(resource = Res.drawable.high_score),
            contentDescription = null
        )
    }
}

@Preview
@Composable
fun HighScoreBadgePreview() {
    HighScoreBadge()
}