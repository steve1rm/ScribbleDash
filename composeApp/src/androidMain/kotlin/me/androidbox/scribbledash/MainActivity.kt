package me.androidbox.scribbledash

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.PathParser
import me.androidbox.scribbledash.draw.presentation.screens.components.DrawingCanvas
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val datalist = parseXmlDrawable(context = this.baseContext, R.drawable.alien)
            println(datalist.count())

            Canvas(modifier = Modifier.size(800.dp)) {
                datalist.forEach { pathData ->
                    val path = PathParser.createPathFromPathData(pathData).asComposePath()

                    drawPath(
                        path = path,
                        color = Color.Black,
                        style = Stroke()
                    )
                }
            }
        }
    }
}

fun parseXmlDrawable(context: Context, drawableId: Int): List<String> {

    return try {
        val pathDataList = mutableListOf<String>()
        val androidNamespace = "http://schemas.android.com/apk/res/android"

        val parser = context.resources.getXml(drawableId)

        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            if (parser.eventType == XmlPullParser.START_TAG && parser.name == "path") {
                val pathData = parser.getAttributeValue(androidNamespace, "pathData")
                if (pathData != null) {  // Correct attribute name
                    pathDataList.add(pathData)
                }
            }
        }

        return pathDataList
    }
    catch (ex: Resources.NotFoundException){
        ex.printStackTrace()
        emptyList()
    }
    catch(ex: XmlPullParserException) {
        ex.printStackTrace()
        emptyList()
    }
}

fun parseXmlDrawableImproved(context: Context, drawableId: Int): List<String> {
    val pathDataList = mutableListOf<String>()
    // Define the Android namespace URI
    val androidNamespace = "http://schemas.android.com/apk/res/android"

    try {
        val parser = context.resources.getXml(drawableId)
        var eventType = parser.eventType

        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG && parser.name == "path") {
                // More robust way to get the attribute value using namespace
                val pathData = parser.getAttributeValue(androidNamespace, "pathData")
                if (pathData != null) {
                    pathDataList.add(pathData)
                }
                // No need to loop through attributes if using getAttributeValue(namespace, name)
            }
            eventType = parser.next()
        }
    } catch (e: Resources.NotFoundException) {
        // Handle case where the drawable resource doesn't exist
        System.err.println("Error: Drawable resource ID #$drawableId not found.")
        // Optionally re-throw or return empty list/null depending on desired behavior
    } catch (e: XmlPullParserException) {
        // Handle XML parsing errors
        System.err.println("Error parsing XML for drawable ID #$drawableId: ${e.message}")
    } catch (e: IOException) {
        // Handle I/O errors during parsing
        System.err.println("I/O error parsing XML for drawable ID #$drawableId: ${e.message}")
    }
    // Note: XmlPullParser obtained via getXml doesn't strictly require closing,
    // but ensure resources are managed if obtained differently.

    return pathDataList
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