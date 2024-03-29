package android.util

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.ImageDrawable
import android.graphics.drawable.SVGDrawable
import android.view.View
import me.antonio.noack.maths.MathsUtils.dpToPx
import me.antonio.noack.maths.MathsUtils.spToPx
import org.w3c.dom.Element

class AttributeSet(val element: Element? = null) {

    val values = HashMap<String, String>()

    fun getSize(key: String, default: Int): Int {
        val value = values[key]?.toLowerCase() ?: return default
        return when {
            value == "match_parent" -> View.LayoutParams.MATCH_PARENT
            value == "wrap_content" -> View.LayoutParams.WRAP_CONTENT
            value.endsWith("sp") -> {
                spToPx(value.substring(0, value.length - 2).toFloat()).toInt()
            }

            value.endsWith("dp") -> {
                dpToPx(value.substring(0, value.length - 2).toFloat()).toInt()
            }

            else -> value.toInt()
        }
    }

    fun getString(key: String, default: String) = values[key] ?: default
    fun getInt(key: String, default: Int) = values[key]?.toInt() ?: default
    fun getFloat(key: String, default: Float): Float {
        val value = values[key]?.toLowerCase() ?: return default
        return when {
            value.endsWith("sp") -> spToPx(value.substring(0, value.length - 2).toFloat())
            value.endsWith("dp") -> dpToPx(value.substring(0, value.length - 2).toFloat())
            else -> value.toFloat()
        }
    }

    fun getDrawable(key: String, default: Drawable?): Drawable? {
        val value = values[key] ?: return default
        val toInt = value.toIntOrNull()
        if (toInt != null) {
            return ColorDrawable(toInt)
        }
        val svg = value.trim()
        return when {
            svg.startsWith("<") -> SVGDrawable(svg)
            svg.startsWith("drawable/") -> ImageDrawable(svg)
            else -> throw RuntimeException("Unknown drawable $value")
        }
    }
}