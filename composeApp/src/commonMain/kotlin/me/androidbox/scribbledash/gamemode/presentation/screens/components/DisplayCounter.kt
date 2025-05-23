package me.androidbox.scribbledash.gamemode.presentation.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.scribbledash.theming.ScribbleDashTheme
import me.androidbox.scribbledash.theming.pink
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import scribbledash.composeapp.generated.resources.Res
import scribbledash.composeapp.generated.resources.paints

@Composable
fun DisplayCounter(
    imageRes: DrawableResource,
    drawingCount: String,
    backgroundColor: Color,
    shouldShowHighScore: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.wrapContentWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = modifier
                .width(76.dp)
                .height(28.dp),
            contentAlignment = Alignment.Center
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 1.dp, bottom = 1.dp),
            ) {
                Spacer(modifier = Modifier.width(12.dp))

                Row(
                    modifier = Modifier
                        .width(60.dp)
                        .background(
                            color = backgroundColor,
                            shape = RoundedCornerShape(100f)
                        ),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = drawingCount,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            Image(
                modifier = Modifier.align(Alignment.CenterStart),
                imageVector = vectorResource(resource = imageRes),
                contentDescription = null
            )
        }

        if(shouldShowHighScore) {
            Text(
                text = "New High",
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
        }
    }
}

@Preview
@Composable
fun DisplayCounterPreview() {
    ScribbleDashTheme {
        DisplayCounter(
            imageRes = Res.drawable.paints,
            drawingCount = "5",
            backgroundColor = MaterialTheme.colorScheme.pink,
            shouldShowHighScore = false
        )
    }
}