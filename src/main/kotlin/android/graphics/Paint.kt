package android.graphics

import androidx.core.math.MathUtils.clamp
import me.antonio.noack.maths.MathsUtils.spToPx
import me.antonio.noack.webdroid.Runner
import org.w3c.dom.CENTER
import org.w3c.dom.CanvasTextAlign
import org.w3c.dom.LEFT
import org.w3c.dom.RIGHT

class Paint(val flags: Int = 0){

    var textAlign = Align.CENTER
    var color = black
    var strokeWidth = 1f
    var textSize = spToPx(17f)
    var style = Style.FILL
    var isBold = false

    fun ascent() = textSize * -0.8f
    fun descent() = textSize * 0.2f

    var alpha
        get() = color.shr(24).and(255)
        set(value) {
            color = color.and(0xffffff) or clamp(value, 0, 255).shl(24)
        }

    companion object {
        const val ANTI_ALIAS_FLAG = 1
        const val black = 0xff000000.toInt()
    }

    enum class Align(val jsName: CanvasTextAlign){
        LEFT(CanvasTextAlign.LEFT),
        RIGHT(CanvasTextAlign.RIGHT),
        CENTER(CanvasTextAlign.CENTER)
    }

    enum class Style {
        FILL,
        STROKE,
        FILL_AND_STROKE
    }

    fun measureText(text: String): Float {
        val ctx = Runner.ctx
        ctx.font = Canvas.fontString(this)
        ctx.fillStyle = Canvas.rgbaString(color)
        return ctx.measureText(text).width.toFloat()
    }

}