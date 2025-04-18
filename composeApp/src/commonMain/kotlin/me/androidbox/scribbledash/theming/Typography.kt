package me.androidbox.scribbledash.theming

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import scribbledash.composeapp.generated.resources.BagelFatOne_Regular
import scribbledash.composeapp.generated.resources.Res

private val bagelFatOne: FontFamily
    @Composable
    get() = FontFamily(
        Font(Res.font.BagelFatOne_Regular, FontWeight.Normal)
    )

val Typography.headLineXSmall: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = bagelFatOne,
            fontWeight = FontWeight.Normal,
            lineHeight = 26.sp,
            fontSize = 18.sp
        )
    }


val Typography.labelXLarge: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontWeight = FontWeight.SemiBold,
            lineHeight = 28.sp,
            fontSize = 24.sp
        )
    }

@Composable
fun AppTypography(): Typography {
    val bagelFatOne = bagelFatOne

    return Typography(
        displayLarge = TextStyle(
            fontFamily = bagelFatOne,
            fontWeight = FontWeight.Normal,
            lineHeight = 80.sp,
            fontSize = 66.sp
        ),
        displayMedium = TextStyle(
            fontFamily = bagelFatOne,
            fontWeight = FontWeight.Normal,
            lineHeight = 44.sp,
            fontSize = 40.sp
        ),
        headlineLarge = TextStyle(
            fontFamily = bagelFatOne,
            fontWeight = FontWeight.Normal,
            lineHeight = 48.sp,
            fontSize = 34.sp
        ),
        headlineMedium = TextStyle(
            fontFamily = bagelFatOne,
            fontWeight = FontWeight.Normal,
            lineHeight = 30.sp,
            fontSize = 26.sp
        ),
        headlineSmall = TextStyle(
            fontFamily = bagelFatOne,
            fontWeight = FontWeight.Normal,
            lineHeight = 26.sp,
            fontSize = 18.sp
        )
    )
}