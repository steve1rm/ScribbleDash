package me.androidbox.scribbledash

import android.content.ContentValues.TAG
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.PathParser
import me.androidbox.scribbledash.draw.presentation.VectorData
import me.androidbox.scribbledash.draw.presentation.screens.components.DrawingCanvas
import me.androidbox.scribbledash.statistics.presentation.components.StatisticsItem
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import scribbledash.composeapp.generated.resources.Res
import scribbledash.composeapp.generated.resources.alien
import scribbledash.composeapp.generated.resources.allDrawableResources
import java.io.IOException
import kotlin.math.min

/*
data class VectorData(
    val viewportWidth: Float = 24f, // Default values
    val viewportHeight: Float = 24f,
    val paths: List<String> = emptyList()
)
*/

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val parser = ParseXmlDrawableImp(this)

        val resource = Res.drawable.alien

        parser.parser(Res.drawable.alien)
        setContent {
            App()
         /*   parser.parser(resource)
            drawSample()*/
        }
    }
}

@Composable
private fun MainActivity.drawSample() {
    val vectorData = remember { parseXmlDrawable(this, R.drawable.alien) }

    // Remember the parsed paths
    val composePaths = remember(vectorData.paths) {
        vectorData.paths.mapNotNull { pathData ->
            try {
                PathParser.createPathFromPathData(pathData).asComposePath()
            } catch (e: Exception) {
                // Handle potential parsing errors for individual paths
                println("Error parsing path data: $pathData \n $e")
                null
            }
        }
    }

    if (composePaths.isNotEmpty() && vectorData.viewportWidth > 0 && vectorData.viewportHeight > 0) {
        Canvas(modifier = Modifier.size(300.dp, 300.dp)) { // Fill the screen
            val canvasWidth = size.width
            val canvasHeight = size.height

            // Calculate scale factors
            val scaleX = canvasWidth / vectorData.viewportWidth
            val scaleY = canvasHeight / vectorData.viewportHeight

            // Use the smaller scale factor to fit entire image (maintaining aspect ratio)
            val scale = min(scaleX, scaleY)

            // Calculate translation to center the scaled image
            val scaledWidth = vectorData.viewportWidth * scale
            val scaledHeight = vectorData.viewportHeight * scale
            val translateX = (canvasWidth - scaledWidth) / 2f
            val translateY = (canvasHeight - scaledHeight) / 2f

            // Apply transformations: first translate, then scale
            // Origin for scaling is top-left (0,0) by default
            withTransform({
                translate(left = translateX, top = translateY)
                scale(scaleX = scale, scaleY = scale, pivot = Offset.Zero)
            }) {
                composePaths.forEachIndexed { index, path ->
                    Log.d(TAG, "Drawing path $index with thick Stroke")
                    drawPath(
                        path = path,
                        color = Color.Red, // Bright color
                        // Use a large width in the original coordinate space
                        style = Stroke(width = 1f) // Try 5f, or even 10f relative to 100x100
                    )
                }
            }
        }
    } else {
        // Handle case where parsing failed or viewport is invalid
        // Display placeholder or error message
    }
}

fun parseXmlDrawable(context: Context, drawableId: Int): VectorData {
    val TAG = "VectorParse" // Tag for logging
    var viewportWidth = 24f // Default
    var viewportHeight = 24f // Default
    val pathDataList = mutableListOf<String>()
    val androidNamespace = "http://schemas.android.com/apk/res/android"
    var parser: XmlPullParser?

    try {
        parser = context.resources.getXml(drawableId)
        var eventType = parser.eventType

        while (eventType != XmlPullParser.END_DOCUMENT) {
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    val tagName = parser.name // Get tag name once

                    when (tagName) {
                        "vector" -> {
                            val vwStr = parser.getAttributeValue(androidNamespace, "viewportWidth")
                            val vhStr = parser.getAttributeValue(androidNamespace, "viewportHeight")

                            viewportWidth = vwStr?.toFloatOrNull() ?: viewportWidth
                            viewportHeight = vhStr?.toFloatOrNull() ?: viewportHeight
                            Log.d(TAG, "Found <vector>: viewportWidth=$viewportWidth ($vwStr), viewportHeight=$viewportHeight ($vhStr)")
                        }
                        "path" -> {
                            val pathData = parser.getAttributeValue(androidNamespace, "pathData")

                            if (!pathData.isNullOrBlank()) {
                                pathDataList.add(pathData)
                            } else {
                                Log.w(TAG, "Found <path> tag with null or blank pathData.")
                            }
                        }
                    }
                }
            }
            eventType = parser.next()
        }
        Log.d(TAG, "Parsing finished. Found ${pathDataList.size} paths.")

    } catch (ex: Resources.NotFoundException) {
        Log.e(TAG, "Drawable resource not found: $drawableId", ex)
    } catch (ex: XmlPullParserException) {
        Log.e(TAG, "XML parsing error", ex)
    } catch (ex: IOException) {
        Log.e(TAG, "XML reading IO error", ex)
    } catch (ex: NumberFormatException) {
        Log.e(TAG, "Error parsing float for viewport dimensions", ex)
    } catch (ex: Exception) { // Catch general exceptions during parsing
        Log.e(TAG, "Unexpected error during parsing", ex)
    }

    return VectorData(viewportWidth, viewportHeight, pathDataList)
}


@Preview(showBackground = true)
@Composable
fun DrawingCanvasPreview() {
    DrawingCanvas(
        paths = emptyList(),
        currentPath = null,
        onAction = {},
        vectorData = VectorData(),
        samplePath = emptyList()
    )
}

@Preview
@Composable
fun AppAndroidPreview() {
   // App()
}