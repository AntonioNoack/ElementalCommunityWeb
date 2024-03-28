package android.graphics.drawable

import android.graphics.Canvas
import org.w3c.dom.HTMLImageElement
import kotlinx.browser.document

class ImageDrawable(source: String): Drawable(){

    var image = cache[source] ?: document.createElement("img") as HTMLImageElement

    init {
        cache[source] = image
        if(image.src.isEmpty()){
            image.onload = {
                println("loaded img")
            }
            image.onerror = { a, b, x, d, e ->
                println("error in img $source")
            }
            image.src = source
        }
    }

    override fun getMinimumHeight(): Int = 0
    override fun getMinimumWidth(): Int = 0
    override fun draw(canvas: Canvas) {
        if(image.complete && image.naturalWidth != 0){
            canvas.ctx.drawImage(image, left.toDouble(), top.toDouble(), (right-left).toDouble(), (bottom-top).toDouble())
        }// else println("sth went wrong with an svg")
    }

    companion object {
        val cache = HashMap<String, HTMLImageElement>()
    }

}