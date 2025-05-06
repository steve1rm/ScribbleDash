package me.androidbox.scribbledash.home.screens

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import me.androidbox.scribbledash.home.model.ScribbleDashCategories
import me.androidbox.scribbledash.home.model.ScribbleNavigationItem
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun RowScope.HomeNavigationBottomBar(
    listOfNavigationItems: List<ScribbleNavigationItem>,
    selectedItemIndex: Int,
    onItemClicked: (category: ScribbleDashCategories) -> Unit
) {
    listOfNavigationItems.forEachIndexed { index, item ->
        this.NavigationBarItem(
            colors = NavigationBarItemDefaults.colors(
              indicatorColor = Color.Transparent
            ),
            selected = selectedItemIndex == index,
            onClick = {
                onItemClicked(ScribbleDashCategories.entries[index])
            },
            icon = {
                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = if (selectedItemIndex == index)
                        vectorResource(resource = item.selectedIcon)
                    else
                        vectorResource(resource = item.unSelectedIcon),
                    contentDescription = stringResource(item.title),
                    tint = if (selectedItemIndex == index) {
                        if(index == 0) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary
                    }
                    else MaterialTheme.colorScheme.surfaceContainerLowest
                )
            }
        )
    }
}