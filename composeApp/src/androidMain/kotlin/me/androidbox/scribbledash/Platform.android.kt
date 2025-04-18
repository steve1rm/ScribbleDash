@file:OptIn(ExperimentalResourceApi::class)

package me.androidbox.scribbledash

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.util.Log
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asComposePath
import androidx.core.graphics.PathParser
import me.androidbox.scribbledash.draw.presentation.VectorData
import me.androidbox.scribbledash.draw.presentation.utils.ParseXmlDrawable
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual class ParseXmlDrawableImp(private val context: Context) : ParseXmlDrawable {

    actual override fun parser(drawableId: DrawableResource): List<Path> {

        val TAG = "VectorParse" // Tag for logging
        var viewportWidth = 24f // Default
        var viewportHeight = 24f // Default
        val pathDataList = mutableListOf<String>()
        val androidNamespace = "http://schemas.android.com/apk/res/android"
        var parser: XmlPullParser?

        var paths = emptyList<Path>()

        try {
            val id = context.resources.getIdentifier("alien", "drawable", context.packageName)

            parser = context.resources.getXml(id)
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

            paths = pathDataList.mapNotNull { pathData ->
                PathParser.createPathFromPathData(pathData).asComposePath()
            }
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

        return paths
    }
}