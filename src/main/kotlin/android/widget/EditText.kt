package android.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.Editable
import android.util.AttributeSet
import me.antonio.noack.maths.MathsUtils.dpToPx
import me.antonio.noack.maths.MathsUtils.spToPx
import me.antonio.noack.webdroid.Runner.now
import org.w3c.dom.HTMLInputElement
import java.lang.StrictMath.colorWithOpacity
import kotlin.browser.document
import kotlin.browser.window
import kotlin.math.abs

class EditText(ctx: Context, attributeSet: AttributeSet?): TextView(ctx, attributeSet){

    // todo add settings, so the user can decide between text field and question field...

    var hint = "..."

    companion object {

        var edited: EditText? = null
        val element = document.createElement("input") as HTMLInputElement
        init {
            element.inputMode = "text"
            element.style.position = "fixed"
            element.style.background = "transparent"
            element.style.border = "none"
            element.style.top = "-1000px"
            element.style.fontFamily = "Verdana"
            document.body?.appendChild(element)
        }

    }

    fun onChange(){
        if(edited == this){
            val newText = element.value
            if(newText != text){
                for(watcher in watchers){
                    watcher.beforeTextChanged(newText, 0, newText.count(), 0)
                }
                text = newText
                val editable = Editable(newText)
                for(watcher in watchers){
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

        // var lastTime = now()
        setOnClickListener {
            // val time = now()
            // if(abs(time - lastTime) < 0.5) return@setOnClickListener
            if(edited != this){ element.value = text }
            edited = this
            element.focus()
            invalidate()
            /*val newText = if(hint.isEmpty() || hint == "..."){
                window.prompt("Input:")
            } else {
                window.prompt(hint)
            } ?: text
            lastTime = now()
            if(newText != text){
                for(watcher in watchers){
                    watcher.beforeTextChanged(newText, 0, newText.count(), 0)
                }
                text = newText
                val editable = Editable(newText)
                for(watcher in watchers){
                    watcher.onTextChanged(editable, 0, 0, editable.count())
                    watcher.afterTextChanged(editable)
                }
                text = editable.internal
                invalidate()
            }*/

        }

    }

    override fun onVisibilityChanged(){
        if(edited == this && !isEffectivelyVisible()){
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
        val text = if(helpOnly) hint else text

        paint.isBold = isBold && !helpOnly
        paint.textSize = textSize
        measuredTextWidth = paint.measureText(text)
        mMinWidth = measuredWidth + mPaddingLeft + mPaddingRight
        mMinHeight = textSize.toInt() + mPaddingTop + mPaddingBottom

        // println("$text @ ${paint.textSize} = $mMinWidth x $mMinHeight")
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        println("min: $mMinWidth -> $measuredWidth, $mMinHeight -> $measuredHeight, ${MeasureSpec.toString(widthMeasureSpec)}, ${MeasureSpec.toString(heightMeasureSpec)}")

        onVisibilityChanged()

    }

    // todo line; left-right padding has no influence on it
    override fun onDraw(canvas: Canvas?) {
        canvas ?: return
        drawBackground(canvas)

        val pad = dpToPx(5f)
        paint.color = 0xff333333.toInt()
        paint.textSize = textSize
        val lineY = measuredHeight * .5f + paint.descent() + textSize * 0.5f
        canvas.drawLine(pad, lineY, measuredWidth-pad, lineY, paint)

        canvas.translate(mPaddingLeft, mPaddingTop)

        if(text.isBlank()){
            paint.isBold = false
            paint.textSize = textSize
            paint.color = colorWithOpacity(textColor, 0.5f)
            drawText(canvas, hint, textAlign)
        } else {
            if(edited != this){
                paint.isBold = isBold
                paint.textSize = textSize
                paint.color = textColor
                drawText(canvas, text, textAlign)
            }
        }

        if(edited == this){

            element.style.display = ""
            val global = getGlobalPosition()
            element.style.fontSize = "${textSize}px"
            val height = element.offsetHeight
            element.style.left = "${global.first + mPaddingLeft}px"
            element.style.top = "${global.second + mPaddingTop + (measuredHeight - (mPaddingTop + mPaddingBottom) - height)/2}px"

        }

    }

    override fun getDefaultTextColor(): Int = 0xff000000.toInt()
    override fun getDefaultTextSize(): Float = spToPx(18f)
    override fun getDefaultIsBold(): Boolean = true

}