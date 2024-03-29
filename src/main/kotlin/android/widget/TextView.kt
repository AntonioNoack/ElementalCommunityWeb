package android.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import me.antonio.noack.maths.MathsUtils.dpToPx
import me.antonio.noack.maths.MathsUtils.spToPx

open class TextView(ctx: Context, attributeSet: AttributeSet?) : View(ctx, attributeSet) {

    var text = ""
    var paint = Paint()
    var textAlign = Paint.Align.LEFT
    var textSize = paint.textSize
    var textColor = paint.color
    var isBold = false

    val watchers = ArrayList<TextWatcher>()

    override fun toString(): String {
        return super.toString() + "($text)"
    }

    init {
        paint.textAlign = Paint.Align.CENTER
    }

    override fun onInit() {
        super.onInit()
        text = attributeSet.getString("text", "")
        textSize = attributeSet.getFloat("textSize", getDefaultTextSize())
        textColor = attributeSet.getInt("textColor", getDefaultTextColor())
        isBold = when (attributeSet.getString("textStyle", "")) {
            "bold" -> true
            "normal" -> false
            else -> getDefaultIsBold()
        }
        textAlign = when (Gravity.getGravityX(layoutParams.gravity)) {
            Gravity.MIN -> Paint.Align.LEFT
            Gravity.CEN -> Paint.Align.CENTER
            Gravity.MAX -> Paint.Align.RIGHT
            else -> {
                when (attributeSet.getString("textAlignment", "")) {
                    "left", "start" -> Paint.Align.LEFT
                    "center" -> Paint.Align.CENTER
                    "right", "end" -> Paint.Align.RIGHT
                    else -> getDefaultTextAlign()
                }
            }
        }
    }

    open fun addTextChangedListener(watcher: TextWatcher) {
        watchers.add(watcher)
    }

    var measuredTextWidth = 0f

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (this !is EditText) {
            paint.isBold = isBold
            paint.textSize = textSize
            measuredTextWidth = paint.measureText(text)
            minimumWidth = measuredTextWidth.toInt() + paddingX
            minimumHeight = paint.textSize.toInt() + paddingY
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.translate(paddingLeft, paddingTop)

        paint.isBold = isBold
        paint.textSize = textSize
        paint.color = textColor
        paint.alpha = (alpha * 255).toInt()

        drawText(canvas, text, textAlign)
    }

    fun drawText(
        canvas: Canvas, text: String, align: Paint.Align, dx: Float = 0f, dy: Float = 0f,
        width: Int = this.measuredWidth - (paddingLeft + paddingRight),
        height: Int = this.measuredHeight - (paddingTop + paddingBottom),
        paint: Paint = this.paint
    ) = drawText(canvas, text, align, dx, dy, width.toFloat(), height.toFloat(), paint)

    fun drawText(
        canvas: Canvas, text: String, align: Paint.Align,
        dx: Float, dy: Float, width: Float, height: Float,
        paint: Paint
    ) {
        if (text.isBlank()) return
        val centerX = when (align) {
            Paint.Align.LEFT -> {
                measuredTextWidth * 0.5f
            }
            Paint.Align.CENTER -> {
                width * 0.5f
            }
            Paint.Align.RIGHT -> {
                width - measuredTextWidth * 0.5f
            }
        }
        canvas.drawText(text, centerX + dx, height * 0.5f + dy - 0.5f * (paint.ascent() + paint.descent()), paint)
    }

    open fun getDefaultTextAlign() = Paint.Align.LEFT
    open fun getDefaultTextColor() = 0xff555555.toInt()
    open fun getDefaultTextSize() = spToPx(12f)
    open fun getDefaultIsBold() = false

    override fun getDefaultPadding(): Int = dpToPx(5f).toInt()

}