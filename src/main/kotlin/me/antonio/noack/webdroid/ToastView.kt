package me.antonio.noack.webdroid

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlinx.browser.window
import me.antonio.noack.elementalcommunity.utils.Maths.mix
import me.antonio.noack.maths.MathsUtils.spToPx
import me.antonio.noack.webdroid.Runner.now
import kotlin.math.abs
import kotlin.math.max

class ToastView(ctx: Context?, attributeSet: AttributeSet?): View(ctx, attributeSet){

    var lastTime = now()
    var text = ""
    var time = 0f
    var duration = 10f
    var inTime = 0.3f
    var outTime = 0.3f
    var isDone = false

    val paint = Paint()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return

        val thisTime = now()
        time += (thisTime - lastTime).toFloat()
        lastTime = thisTime

        if(time > duration || text.isBlank()){
            isDone = true
            return
        }

        var canSleep = false

        paint.isBold = false
        paint.textSize = spToPx(20f)

        val padding = spToPx(5f)
        val elementHeight = paint.textSize + 2*padding

        val relativeHeight = if(time < inTime){
            time / inTime
        } else if(time > duration - outTime){
            (duration - time) / outTime
        } else {
            canSleep = true
            1f
        }

        val width = window.innerWidth.toFloat()
        val height = window.innerHeight.toFloat()

        canvas.translate(0f, height - elementHeight - mix(-1-elementHeight, padding, relativeHeight))

        val width0 = paint.measureText(text)

        paint.color = 0xff777777.toInt()
        canvas.drawRect(width*.5f - (width0*.5f + padding),  0f, width*.5f + (width0*.5f + padding), elementHeight, paint)
        paint.color = -1
        canvas.drawText(text, width*.5f, (elementHeight - (paint.ascent() + paint.descent())) * 0.5f, paint)

        if(canSleep){
            setTimeout({
                invalidate()
            }, max(0, (abs(time - (duration - outTime)) * 1000).toInt() - 250))
        } else invalidate()

    }

}