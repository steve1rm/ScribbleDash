package me.androidbox.scribbledash.home.model

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import scribbledash.composeapp.generated.resources.Res
import scribbledash.composeapp.generated.resources.chart
import scribbledash.composeapp.generated.resources.home
import scribbledash.composeapp.generated.resources.shop

data class ScribbleNavigationItem(
    val title: StringResource,
    val selectedIcon: DrawableResource,
    val unSelectedIcon: DrawableResource,
    val hasExtra: Boolean = false,
    val badgeCount: Int? = null
)

val listOfNavigationItems = listOf(
    ScribbleNavigationItem(
        title = ScribbleDashCategories.CHART.titleRes,
        selectedIcon = Res.drawable.chart,
        unSelectedIcon = Res.drawable.chart
    ),

    ScribbleNavigationItem(
        title = ScribbleDashCategories.HOME.titleRes,
        selectedIcon = Res.drawable.home,
        unSelectedIcon = Res.drawable.home
    ),

    ScribbleNavigationItem(
        title = ScribbleDashCategories.HOME.titleRes,
        selectedIcon = Res.drawable.shop,
        unSelectedIcon = Res.drawable.shop
    ),
)


