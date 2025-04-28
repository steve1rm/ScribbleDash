package me.androidbox.scribbledash.statistics.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.androidbox.scribbledash.theming.onBackgroundVariant

@Composable
fun StatisticsItem(
    icon: @Composable () -> Unit,
    title: String,
    percentage: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(76.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 12.dp, end = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            icon()

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackgroundVariant
            )

            Text(
                modifier = Modifier
                    .wrapContentWidth(Alignment.End)
                    .weight(1f),
                text = percentage,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

/*
StatisticsItem(
icon = {
    Box(
        modifier = Modifier
            .size(52.dp)
            .background(color = Color(0xff742efc).copy(alpha = 0.1f), shape = RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(height = 32.dp, width = 28.dp),
            imageVector = vectorResource(resource = Res.drawable.eggtimer),
            contentDescription = null,
            tint = Color.Unspecified)
    }
},
title = "Nothing to track for now",
percentage = 89,
)
*/


/*
@Preview
@Composable
fun StatisticsItemPreview() {
    StatisticsItem(modifier = Modifier.fillMaxSize())
}*/
