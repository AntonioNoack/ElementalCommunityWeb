package android.graphics.drawable

import android.graphics.Canvas

class ColorDrawable(val color: Int): Drawable(){
    override fun getMinimumHeight(): Int = 0
    override fun getMinimumWidth(): Int = 0
    override fun draw(canvas: Canvas) {
        canvas?.fillRect(left, top, right, bottom, color)
    }
}