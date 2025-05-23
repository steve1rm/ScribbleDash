package me.androidbox.scribbledash.statistics.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DrawingToolSelector(
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(0) }
    val contentColor = Color.White

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .padding(bottom = 0.dp) // Remove bottom padding
        ) {
            TabRow(selectedTab = selectedTab) { newTab ->
                selectedTab = newTab
            }
        }

        // Content container aligned with tabs
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp) // Same horizontal padding as tabs
                .weight(1f)
                .background(contentColor)
                // Move this up to overlap with the tabs
                .offset(y = (-8).dp)
        ) {
            when (selectedTab) {
                0 -> PenContent(selectedTab == 0)
                1 -> CanvasContent(selectedTab == 1)
            }
        }
    }
}

@Composable
private fun TabRow(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TabButton(
            title = "Pen",
            isSelected = selectedTab == 0,
            modifier = Modifier.weight(1f),
            onClick = { onTabSelected(0) }
        )

        TabButton(
            title = "Canvas",
            isSelected = selectedTab == 1,
            modifier = Modifier.weight(1f),
            onClick = { onTabSelected(1) }
        )
    }
}

@Composable
private fun TabButton(
    title: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .clip(shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.surfaceContainerLow else MaterialTheme.colorScheme.surfaceContainerLow.copy(alpha = 0.5f),
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
            color = if (isSelected) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onSurface
        )

        if(!isSelected) {
            HorizontalDivider(
                modifier = Modifier.align(alignment = Alignment.BottomCenter),
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.background
            )
        }
    }
}

@Composable
private fun PenContent(
    isSelected: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.surfaceContainerLow else MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Pen Content Goes Here",
            fontSize = 20.sp,
            color = Color.Gray
        )

        // You can add your PenOptionsRow() here when ready
    }
}

@Composable
private fun CanvasContent(
    isSelected: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.surfaceContainerLow else MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Canvas Content Goes Here",
            fontSize = 20.sp,
            color = Color.Gray
        )

        // Canvas specific options can go here
    }
}

@Preview
@Composable
fun DrawingToolSelectorFullPreview() {
    DrawingToolSelector()
}

@Preview
@Composable
fun DrawingToolSelectorCanvasTabPreview() {
    var selectedTab by remember { mutableStateOf(1) }
    // Copy of DrawingToolSelector with preset selection
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .padding(bottom = 0.dp)
        ) {
            TabRow(selectedTab = selectedTab) { newTab ->
                selectedTab = newTab
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .weight(1f)
                .background(Color.White)
                .offset(y = (-8).dp)
        ) {
            when (selectedTab) {
                0 -> PenContent(true)
                1 -> CanvasContent(false)
            }
        }
    }
}