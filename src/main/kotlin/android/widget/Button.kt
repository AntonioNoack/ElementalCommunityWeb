package android.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import me.antonio.noack.maths.MathsUtils.spToPx

open class Button(ctx: Context, attributeSet: AttributeSet?) : TextView(ctx, attributeSet) {

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (isButtonDown) {
            canvas.fillRect(mLeft, mTop, mRight, mBottom, 0x11111111)
        }
    }

    override fun getDefaultPadding(): Int = spToPx(15f).toInt()
    override fun getDefaultTextSize(): Float = spToPx(17f)
    override fun getDefaultTextAlign() = Paint.Align.CENTER
    override fun getDefaultIsBold(): Boolean = true

}