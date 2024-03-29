package android.graphics.drawable

import android.graphics.Canvas
import org.w3c.dom.Image

external fun btoa(string: String): String

class SVGDrawable(source: String) : Drawable() {

    /**
    <vector
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp"
    android:height="24dp"
    android:viewportWidth="24.0"
    android:viewportHeight="24.0">
    <path
    android:fillColor="#FF000000"
    android:pathData="M20,11H7.83l5.59,-5.59L12,4l-8,8 8,8 1.41,-1.41L7.83,13H20v-2z"/>
    </vector>
     */

    private val image = Image()

    init {
        image.onerror = { a, b, c, d, e ->
            console.log("error in svg $source", listOf(a, b, c, d, e))
        }
        val bytes = btoa(source.replace("<svg", "<svg xmlns=\"http://www.w3.org/2000/svg\" "))
        image.src = "data:image/svg+xml;base64,$bytes"
    }

    override fun getMinimumHeight(): Int = 0
    override fun getMinimumWidth(): Int = 0
    override fun draw(canvas: Canvas, alpha: Float) {
        if (image.complete && image.naturalWidth != 0) {
            canvas.ctx.drawImage(
                image,
                left.toDouble(),
                top.toDouble(),
                (right - left).toDouble(),
                (bottom - top).toDouble()
            )
        }
    }
}