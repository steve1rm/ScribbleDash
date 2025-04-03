package me.androidbox.scribbledash.home.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import me.androidbox.scribbledash.theming.success
import org.jetbrains.compose.resources.vectorResource
import scribbledash.composeapp.generated.resources.Res
import scribbledash.composeapp.generated.resources.one_round_wonder

@Composable
fun HomeCard(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(128.dp)
            .fillMaxWidth()
            .border(
                color = success,
                shape = RoundedCornerShape(16.dp),
                width = 8.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(start = 22.dp),
                text = "One Round\nWonder",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.W400)
        }

        Image(
            modifier = Modifier
                .align(Alignment.Bottom),
            imageVector = vectorResource(resource = Res.drawable.one_round_wonder),
            contentDescription = null
        )
    }
}