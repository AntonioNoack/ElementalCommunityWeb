package android.graphics.drawable

import android.graphics.Canvas
import android.graphics.RectF

abstract class Drawable: RectF(){
    abstract fun getMinimumWidth(): Int
    abstract fun getMinimumHeight(): Int
    abstract fun draw(canvas: Canvas, alpha: Float)
    fun Int.withAlpha(alpha: Float): Int {
        return if (alpha == 1f) this else
            this.and(0xffffff) or (this.ushr(24) * alpha).toInt().shl(24)
    }
}