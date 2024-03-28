package androidx.appcompat.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.widget.Button
import me.antonio.noack.elementalcommunity.utils.Maths.mix
import kotlin.math.abs

class SwitchCompat(ctx: Context, attributeSet: AttributeSet?): Button(ctx, attributeSet){

    var relativeRadiusSize = 0.5f
    var relativeRadiusSmall = relativeRadiusSize * 2/3
    var relativeMotionSize = relativeRadiusSize * 1.5f
    var isChecked = false
    var position = 0.5f

    // track color is always 30% opacity
    // inactive switch color
    var inactiveSwitchColor = 0

    // active switch & track color
    var activeTrackNSwitchColor = 0

    // inactive track color
    var inactiveTrackColor = 0

    init {
        setOnClickListener {
            isChecked = !isChecked
            listener?.invoke(this, isChecked)
            invalidate()
        }
    }

    override fun onInit() {
        super.onInit()
        inactiveSwitchColor = attributeSet.getInt("colorSwitchThumbNormal", inactiveSwitchColor)
        inactiveTrackColor = attributeSet.getInt("colorForeground", inactiveTrackColor)
        activeTrackNSwitchColor = attributeSet.getInt("colorControlActivated", activeTrackNSwitchColor)
    }

    var listener: ((SwitchCompat, Boolean) -> Unit)? = null
    fun setOnCheckedChangeListener(listener: (SwitchCompat, Boolean) -> Unit){
        this.listener = listener
    }

    val rectf = RectF()
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val goalPosition = if(isChecked) 1f else 0f
        position = mix(position, goalPosition, 0.3f)
        var delta = goalPosition - position
        if(abs(delta) < 0.01f){
            delta = 0f
            position = goalPosition
        }

        // todo draw the switch state...
        val textSize = textSize
        val radius = relativeRadiusSize * textSize
        val motionSize = relativeMotionSize * textSize
        val width = measuredWidth - (mPaddingLeft + mPaddingRight)
        val height = measuredHeight - (mPaddingTop + mPaddingBottom)

        // val left = width - relativeMotionSize + 2 * radius

        val leftCenter = width - radius - motionSize
        val rightCenter = width - radius
        val circlePosition = mix(leftCenter, rightCenter, position)

        val y0 = height * 0.5f

        val smallRadius = relativeRadiusSmall * textSize
        rectf.setBounds(leftCenter - smallRadius, y0 - smallRadius, rightCenter + smallRadius, y0 + smallRadius)
        canvas.drawRoundRect(rectf, smallRadius, smallRadius, 0x42000000 or (if(isChecked) activeTrackNSwitchColor else inactiveTrackColor).and(0xffffff))

        canvas.drawCircle(circlePosition, y0, radius, 0xff000000.toInt() or (if(isChecked) activeTrackNSwitchColor else inactiveSwitchColor).and(0xffffff))

        if(delta != 0f){
            invalidate()
        }

    }

    override fun getDefaultTextAlign() = Paint.Align.LEFT
    override fun getDefaultIsBold() = false

}