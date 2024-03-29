package android.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.ColorCircleDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.math.MathUtils.clamp
import me.antonio.noack.webdroid.Runner.now
import kotlin.math.round

class SeekBar(ctx: Context, attributeSet: AttributeSet?) : ProgressBar(ctx, attributeSet) {

    interface OnSeekBarChangeListener {
        fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean)
        fun onStartTrackingTouch(seekBar: SeekBar?)
        fun onStopTrackingTouch(seekBar: SeekBar?)
    }

    var listener: OnSeekBarChangeListener? = null
    fun setOnSeekBarChangeListener(listener: OnSeekBarChangeListener) {
        this.listener = listener
    }

    var lastTime = 0.0
    var draggedTimer = 0.0
    var thumb: Drawable? = null

    fun makeTransparent(color: Int, opacity: Float = 0.9f): Int {
        return ((color.shr(24).and(255) * opacity).toInt().shl(24)) or (color.and(0xffffff))
    }

    override fun onInit() {
        super.onInit()

        thumb = ColorCircleDrawable(makeTransparent(attributeSet.getInt("thumbTint", -1)))

        setOnTouchListener { _, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_MOVE, MotionEvent.ACTION_DOWN, MotionEvent.ACTION_UP -> {
                    if (event.x.toInt() in paddingLeft..mRight - paddingRight) {
                        val progress = clamp(round(min + (event.x - paddingLeft - xZero) / xStep).toInt(), min, max)
                        if (progress != this.progress) {
                            this.progress = progress
                            listener?.onProgressChanged(this, progress, true)
                            lastTime = now()
                            draggedTimer = 1.0
                            invalidate()
                        }
                    }
                }
            }
            true
        }

    }

    var centerX = 0f
    var centerY = 0f
    var xZero = 0f
    var xStep = 0f

    override fun onDraw(canvas: Canvas) {
        val save = canvas.save()
        super.onDraw(canvas)
        canvas.restoreToCount(save)

        canvas.translate(paddingLeft, paddingTop)

        // draw the circle
        // todo give feedback when moving...

        val thumb = thumb ?: return

        val width = (getMeasuredWidth() - paddingLeft - paddingRight).toFloat()
        val height = (this.measuredHeight - (paddingTop + paddingBottom)).toFloat()
        xZero = height * 0.5f
        xStep = (width - 2f * height) / (max - min)


        centerY = height * 0.5f
        centerX = xZero + xStep * (progress - min)

        canvas.translate(centerX, centerY)

        if (draggedTimer > 0.0) {

            val thisTime = now()
            val dt = clamp(thisTime - lastTime, 0.0, 0.5)
            lastTime = thisTime
            draggedTimer -= dt * 3

            if (draggedTimer > 0.0) {

                // draw the thumb with half the size
                val half = centerY * 0.5f * draggedTimer.toFloat()
                thumb.setBounds(-half, -half, half, half)
                thumb.draw(canvas, alpha)

                invalidate()
            }
        }

        thumb.setBounds(-centerY * .5f, -centerY * .5f, centerY * .5f, centerY * .5f)
        thumb.draw(canvas, alpha)
    }
}