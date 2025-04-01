package me.androidbox.scribbledash.theming

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import scribbledash.composeapp.generated.resources.BagelFatOne_Regular
import scribbledash.composeapp.generated.resources.Res
import androidx.compose.material3.Typography

@Composable
fun AppTypography(): Typography {
    val bagelFatOne = FontFamily(
        Font(Res.font.BagelFatOne_Regular, FontWeight.Normal)
    )

    return Typography(
        headlineLarge = TextStyle(
            fontFamily = bagelFatOne,
            fontWeight = FontWeight.Normal,
            fontSize = 24.sp
        )
    )
}