package android.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.Editable
import android.util.AttributeSet
import kotlinx.browser.document
import me.antonio.noack.maths.MathsUtils.dpToPx
import me.antonio.noack.maths.MathsUtils.spToPx
import org.w3c.dom.HTMLInputElement
import java.lang.StrictMath.colorWithOpacity

class EditText(ctx: Context, attributeSet: AttributeSet?) : TextView(ctx, attributeSet) {

    companion object {
        var edited: EditText? = null
        val element = (document.createElement("input") as HTMLInputElement)
            .apply {
                inputMode = "text"
                style.position = "fixed"
                style.background = "transparent"
                style.border = "none"
                style.top = "-1000px"
                style.fontFamily = "Verdana"
                document.body?.appendChild(this)
            }
    }

    private var hint = "..."

    private fun onChange() {
        if (edited == this) {
            val newText = element.value
            if (newText != text) {
                for (watcher in watchers) {
                    watcher.beforeTextChanged(newText, 0, newText.count(), 0)
                }
                text = newText
                val editable = Editable(newText)
                for (watcher in watchers) {
                    watcher.onTextChanged(editable, 0, 0, editable.count())
                    watcher.afterTextChanged(editable)
                }
                text = editable.internal
                invalidate()
            }
        }
    }

    init {

        element.addEventListener("change", { onChange() })
        element.addEventListener("keyup", { onChange() })
        element.addEventListener("keypressed", { onChange() })

        setOnClickListener {
            if (edited != this) {
                element.value = text
            }
            edited = this
            element.focus()
            invalidate()
        }
    }

    override fun onVisibilityChanged() {
        if (edited == this && !isEffectivelyVisible()) {
            element.style.display = "none"
        }
    }

    override fun onInit() {
        super.onInit()
        hint = attributeSet.getString("hint", "...")
        paint.textAlign = Paint.Align.CENTER
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val helpOnly = text.isBlank()
        val text = if (helpOnly) hint else text

        paint.isBold = isBold && !helpOnly
        paint.textSize = textSize
        measuredTextWidth = paint.measureText(text)
        minimumWidth = this.measuredWidth + paddingLeft + paddingRight
        minimumHeight = textSize.toInt() + paddingTop + paddingBottom

        // println("$text @ ${paint.textSize} = $mMinWidth x $mMinHeight")
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        println(
            "min: $minimumWidth -> ${this.measuredWidth}, $minimumHeight -> ${this.measuredHeight}, ${
                MeasureSpec.toString(
                    widthMeasureSpec
                )
            }, ${MeasureSpec.toString(heightMeasureSpec)}"
        )

        onVisibilityChanged()

    }

    override fun onDraw(canvas: Canvas) {
        drawBackground(canvas)

        val pad = dpToPx(5f)
        paint.color = 0xff333333.toInt()
        paint.textSize = textSize
        val lineY = this.measuredHeight * .5f + paint.descent() + textSize * 0.5f
        canvas.drawLine(pad, lineY, this.measuredWidth - pad, lineY, paint)

        canvas.translate(paddingLeft, paddingTop)

        if (text.isBlank()) {
            paint.isBold = false
            paint.textSize = textSize
            paint.color = colorWithOpacity(textColor, 0.5f)
            drawText(canvas, hint, textAlign)
        } else {
            if (edited != this) {
                paint.isBold = isBold
                paint.textSize = textSize
                paint.color = textColor
                drawText(canvas, text, textAlign)
            }
        }

        if (edited == this) {
            element.style.display = ""
            val global = getGlobalPosition()
            element.style.fontSize = "${textSize}px"
            val height = element.offsetHeight
            element.style.left = "${global.x + paddingLeft}px"
            element.style.top =
                "${global.y + paddingTop + (this.measuredHeight - (paddingTop + paddingBottom) - height) / 2}px"

        }
    }

    override fun getDefaultTextColor(): Int = 0xff000000.toInt()
    override fun getDefaultTextSize(): Float = spToPx(18f)
    override fun getDefaultIsBold(): Boolean = true

}