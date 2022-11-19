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
import org.w3c.dom.HTMLElement

open class TextView(ctx: Context, attributeSet: AttributeSet?): View(ctx, attributeSet){

    var text = ""
    var paint = Paint()
    var textAlign = Paint.Align.LEFT
    var textSize = paint.textSize
    var textColor = paint.color
    var isBold = false

    val watchers = ArrayList<TextWatcher>()

    override fun toString(): String {
        return super.toString()+"($text)"
    }

    init {

        paint.textAlign = Paint.Align.CENTER

        setOnClickListener {
            println("ignored click on $this")
        }

    }

    override fun onInit() {
        super.onInit()
        text = attributeSet.getString("text", "")
        textSize = attributeSet.getFloat("textSize", getDefaultTextSize())
        textColor = attributeSet.getInt("textColor", getDefaultTextColor())
        isBold = when(attributeSet.getString("textStyle", "")){
            "bold" -> true
            "normal" -> false
            else -> getDefaultIsBold()
        }
        val xGravity = layoutParams.gravity and Gravity.HORIZONTAL_GRAVITY_MASK
        textAlign =
        when(xGravity){
            Gravity.LEFT and Gravity.HORIZONTAL_GRAVITY_MASK -> Paint.Align.LEFT
            Gravity.CENTER and Gravity.HORIZONTAL_GRAVITY_MASK -> Paint.Align.CENTER
            Gravity.RIGHT and Gravity.HORIZONTAL_GRAVITY_MASK -> Paint.Align.RIGHT
            else -> {
                when(attributeSet.getString("textAlignment", "")){
                    "left", "start" ->  Paint.Align.LEFT
                    "center" ->  Paint.Align.CENTER
                    "right", "end" ->  Paint.Align.RIGHT
                    else -> getDefaultTextAlign()
                }
            }
        }
        // println("text $text := ${attributeSet.getString("textSize", "")}")
        // println("tv $text ${layoutParams.gravity} $xGravity, ${Gravity.LEFT} ${Gravity.CENTER} ${Gravity.RIGHT} -> $textAlign")
    }

    open fun addTextChangedListener(watcher: TextWatcher){
        watchers.add(watcher)
    }

    var measuredTextWidth = 0f

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        if(this::class.simpleName != "EditText"){
            if(text.isNotBlank()){
                paint.isBold = isBold
                paint.textSize = textSize
                measuredTextWidth = paint.measureText(text)
                mMinWidth = measuredTextWidth.toInt() + mPaddingLeft + mPaddingRight
                mMinHeight = paint.textSize.toInt() + mPaddingTop + mPaddingBottom
            } else {
                measuredTextWidth = 0f
                mMinWidth = mPaddingLeft + mPaddingRight
                mMinHeight = mPaddingTop + mPaddingBottom
            }
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return

        canvas.translate(mPaddingLeft, mPaddingTop)

        paint.isBold = isBold
        paint.textSize = textSize
        paint.color = textColor

        drawText(canvas, text, textAlign)
    }

    fun drawText(canvas: Canvas, text: String, align: Paint.Align, dx: Float = 0f, dy: Float = 0f,
                 width: Int = measuredWidth - (mPaddingLeft + mPaddingRight),
                 height: Int = measuredHeight - (mPaddingTop + mPaddingBottom),
                 paint: Paint = this.paint) =
            drawText(canvas, text, align, dx, dy, width.toFloat(), height.toFloat(), paint)

    fun drawText(canvas: Canvas, text: String, align: Paint.Align, dx: Float, dy: Float, width: Float, height: Float, paint: Paint){
        if(text.isBlank()) return
        val centerX = when(align){
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