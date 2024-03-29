package android.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import me.antonio.noack.maths.MathsUtils.spToPx

open class ProgressBar(ctx: Context, attributeSet: AttributeSet? = null): View(ctx, attributeSet){

    var min = 0
    var max = 5
    var progress = 0
    var tint = -1
    val paint = Paint()

    override fun onInit() {
        super.onInit()

        min = attributeSet.getInt("min", min)
        max = attributeSet.getInt("max", max)

        tint = attributeSet.getInt("progressTint", tint)

        paint.color = tint
        paint.style = Paint.Style.STROKE

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.translate(paddingLeft, paddingTop)
        val y = (getMeasuredHeight() - (paddingTop + paddingBottom)) * 0.5f
        paint.strokeWidth = spToPx(2f)
        canvas.drawLine(paddingLeft.toFloat(), y, (getMeasuredWidth() - paddingRight).toFloat(), y, paint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        minimumHeight = spToPx(10f).toInt()
        minimumWidth = (spToPx(1f) * max).toInt() + minimumHeight
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}