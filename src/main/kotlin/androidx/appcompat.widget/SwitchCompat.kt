package androidx.appcompat.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.widget.Button
import me.antonio.noack.elementalcommunity.utils.Maths.mix
import kotlin.math.abs

class SwitchCompat(ctx: Context, attributeSet: AttributeSet?) : Button(ctx, attributeSet) {

    companion object {
        private val helperRect = RectF()
        private const val relativeRadiusSize = 0.5f
        private const val relativeRadiusSmall = relativeRadiusSize * 2 / 3
        private const val relativeMotionSize = relativeRadiusSize * 1.5f
    }

    var isChecked = false
    var position = 0.5f

    private var inactiveSwitchColor = 0
    private var activeTrackNSwitchColor = 0
    private var inactiveTrackColor = 0

    init {
        setOnClickListener {
            isChecked = !isChecked
            listener?.invoke(this, isChecked)
            invalidate()
        }
    }

    override fun onInit() {
        super.onInit()
        inactiveSwitchColor = attributeSet.getInt("colorSwitchThumbNormal", 0)
        inactiveTrackColor = attributeSet.getInt("colorForeground", 0)
        activeTrackNSwitchColor = attributeSet.getInt("colorControlActivated", 0)
    }

    private var listener: ((SwitchCompat, Boolean) -> Unit)? = null
    fun setOnCheckedChangeListener(listener: (SwitchCompat, Boolean) -> Unit) {
        this.listener = listener
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val goalPosition = if (isChecked) 1f else 0f
        position = mix(position, goalPosition, 0.3f)
        var delta = goalPosition - position
        if (abs(delta) < 0.01f) {
            delta = 0f
            position = goalPosition
        }

        // todo draw the switch state...
        val textSize = textSize
        val radius = relativeRadiusSize * textSize
        val motionSize = relativeMotionSize * textSize
        val width = this.measuredWidth - (paddingLeft + paddingRight)
        val height = this.measuredHeight - (paddingTop + paddingBottom)

        // val left = width - relativeMotionSize + 2 * radius

        val leftCenter = width - radius - motionSize
        val rightCenter = width - radius
        val circlePosition = mix(leftCenter, rightCenter, position)

        val y0 = height * 0.5f

        val smallRadius = relativeRadiusSmall * textSize
        helperRect.setBounds(leftCenter - smallRadius, y0 - smallRadius, rightCenter + smallRadius, y0 + smallRadius)
        canvas.drawRoundRect(
            helperRect, smallRadius, smallRadius,
            0x42000000 or (if (isChecked) activeTrackNSwitchColor else inactiveTrackColor).and(0xffffff)
        )
        canvas.drawCircle(
            circlePosition, y0, radius,
            0xff000000.toInt() or (if (isChecked) activeTrackNSwitchColor else inactiveSwitchColor).and(0xffffff)
        )

        if (delta != 0f) {
            invalidate()
        }

    }

    override fun getDefaultTextAlign() = Paint.Align.LEFT
    override fun getDefaultIsBold() = false

}