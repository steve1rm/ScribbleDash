package me.androidbox.scribbledash.home.screens.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import me.androidbox.scribbledash.home.model.GameType
import me.androidbox.scribbledash.theming.success

@Composable
fun HomeGameCard(
    modifier: Modifier = Modifier,
    title: String,
    borderColor: Color,
    image: @Composable () -> Unit,
    onGameCardClicked: () -> Unit
) {
    Row(
        modifier = modifier
            .height(128.dp)
            .fillMaxWidth()
            .border(
                color = borderColor,
                shape = RoundedCornerShape(16.dp),
                width = 8.dp
            )
            .clickable(
                onClick = onGameCardClicked
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
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.W400)
        }

        image()
    }
}