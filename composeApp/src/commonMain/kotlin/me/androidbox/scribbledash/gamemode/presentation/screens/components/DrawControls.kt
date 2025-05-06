package me.androidbox.scribbledash.gamemode.presentation.screens.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.androidbox.scribbledash.theming.success
import org.jetbrains.compose.resources.vectorResource
import scribbledash.composeapp.generated.resources.Res
import scribbledash.composeapp.generated.resources.forward
import scribbledash.composeapp.generated.resources.reply

@Composable
fun DrawControls(
    onUndoClicked: () -> Unit,
    onRedoClicked: () -> Unit,
    onClearClicked: () -> Unit,
    unDoEnabled: Boolean,
    redoEnabled: Boolean,
    clearEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Button(
            modifier = Modifier.size(size = 64.dp),
            shape = RoundedCornerShape(22.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerLow
            ),
            contentPadding = PaddingValues(0.dp),
            onClick = onUndoClicked,
            enabled = unDoEnabled
        ) {
            Icon(
                modifier = Modifier.size(28.dp),
                imageVector = vectorResource(resource = Res.drawable.reply),
                contentDescription = "undo",
                tint = if(unDoEnabled) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
            )
        }

        Button(
            modifier = Modifier.size(size = 64.dp),
            shape = RoundedCornerShape(22.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerLow
            ),
            contentPadding = PaddingValues(0.dp),
            onClick = onRedoClicked,
            enabled = redoEnabled
        ) {
            Icon(
                modifier = Modifier.size(28.dp),
                imageVector = vectorResource(resource = Res.drawable.forward),
                contentDescription = "redo",
                tint = if(unDoEnabled) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            modifier = Modifier
                .height(height = 64.dp)
                .width(width = 128.dp)
                .border(
                    width = 8.dp,
                    color = MaterialTheme.colorScheme.surfaceContainerHigh,
                    shape = RoundedCornerShape(size = 20.dp)
                ),
            enabled = clearEnabled,
            shape = RoundedCornerShape(20.dp),
            elevation = ButtonDefaults.elevatedButtonElevation(2.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if(clearEnabled) success else MaterialTheme.colorScheme.surfaceContainerLowest
            ),
            onClick = onClearClicked
        ) {
            Text(
                text = "Done".uppercase(),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}