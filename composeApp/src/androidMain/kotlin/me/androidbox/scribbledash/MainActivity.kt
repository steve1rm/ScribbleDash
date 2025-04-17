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
import androidx.core.graphics.PathParser
import me.androidbox.scribbledash.draw.presentation.screens.components.DrawingCanvas
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import kotlin.math.min

data class VectorData(
    val viewportWidth: Float = 24f, // Default values
    val viewportHeight: Float = 24f,
    val paths: List<String> = emptyList()
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
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
                Canvas(modifier = Modifier.fillMaxSize()) { // Fill the screen
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
    }
}

fun parseXmlDrawable(context: Context, drawableId: Int): VectorData {
    val TAG = "VectorParse" // Tag for logging
    var viewportWidth = 24f // Default
    var viewportHeight = 24f // Default
    val pathDataList = mutableListOf<String>()
    val androidNamespace = "http://schemas.android.com/apk/res/android"
    var parser: XmlPullParser? = null // Declare parser outside try

    try {
        parser = context.resources.getXml(drawableId)
        var eventType = parser.eventType

        while (eventType != XmlPullParser.END_DOCUMENT) {
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    val tagName = parser.name // Get tag name once
                    // Log.v(TAG, "Start Tag: $tagName") // Verbose logging if needed
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
                            // Log.v(TAG, "Found <path>: pathData=$pathData") // Verbose logging
                            if (!pathData.isNullOrBlank()) {
                                pathDataList.add(pathData)
                            } else {
                                Log.w(TAG, "Found <path> tag with null or blank pathData.")
                            }
                        }
                        // Add handling for <group> if needed later
                    }
                }
                // Add other event types like END_TAG if needed for complex parsing
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
    } finally {
        // Although getXml doesn't strictly require closing like an InputStream,
        // checking for XmlResourceParser and calling close() is good practice if it implements it.
        // However, in this simplified case, it might not be necessary.
    }


    return VectorData(viewportWidth, viewportHeight, pathDataList)
}

@Preview(showBackground = true)
@Composable
fun DrawingCanvasPreview() {
    DrawingCanvas(
        paths = emptyList(),
        currentPath = null,
        onAction = {}
    )
}

@Preview
@Composable
fun AppAndroidPreview() {
   // App()
}