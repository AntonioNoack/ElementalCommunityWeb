package android.graphics.drawable

import android.graphics.Canvas

class ColorCircleDrawable(val color: Int) : Drawable() {
    override fun getMinimumHeight(): Int = 0
    override fun getMinimumWidth(): Int = 0
    override fun draw(canvas: Canvas, alpha: Float) {
        canvas.drawCircle(
            (left + right) / 2.0, (top + bottom) / 2.0, width() * .35,
            color.withAlpha(alpha)
        )
    }
}