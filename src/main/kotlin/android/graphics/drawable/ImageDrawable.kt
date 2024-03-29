package android.graphics.drawable

import android.graphics.Canvas
import org.w3c.dom.Image

class ImageDrawable(source: String) : Drawable() {

    private val image = cache.getOrPut(source) {
        val image = Image()
        image.onload = {
            println("Loaded $source")
        }
        image.onerror = { _, _, _, _, _ ->
            println("Error loading $source as image")
        }
        image.src = source
        image
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

    companion object {
        private val cache = HashMap<String, Image>()
    }
}