package me.androidbox.scribbledash.home.model

import org.jetbrains.compose.resources.StringResource
import scribbledash.composeapp.generated.resources.Res
import scribbledash.composeapp.generated.resources.chart
import scribbledash.composeapp.generated.resources.drawing

enum class ScribbleDashCategories(val titleRes: StringResource) {
    DRAWING(titleRes = Res.string.drawing),
    CHART(titleRes = Res.string.chart),
}