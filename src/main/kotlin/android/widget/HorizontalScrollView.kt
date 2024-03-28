package android.widget

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.core.math.MathUtils
import java.util.*
import kotlin.math.max
import kotlin.math.min

class HorizontalScrollView(ctx: Context, attributeSet: AttributeSet?): ViewGroup(ctx, attributeSet){

    var scroll = 0f
    var shownScroll = 0
    var contentLength = 0

    fun checkScroll(dx: Float, invalidate: Boolean){
        val newScroll = MathUtils.clamp(scroll+dx, 0f, max(0, contentLength - measuredHeight).toFloat())
        scroll = newScroll
        if(newScroll.toInt() != shownScroll){
            if(invalidate) invalidate()
        }
    }

    init {
        val gestureDetector = GestureDetector(object: GestureDetector.OnGestureListener {
            override fun onDown(event: MotionEvent): Boolean = false
            override fun onFling(e1: MotionEvent?, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean = false
            override fun onLongPress(e: MotionEvent) {}
            override fun onScroll(event: MotionEvent?, e2: MotionEvent, dx: Float, dy: Float): Boolean {
                checkScroll(dx, true)
                return true
            }
            override fun onShowPress(e: MotionEvent) {}
            override fun onSingleTapUp(event: MotionEvent): Boolean = false
        })
        setOnTouchListener { _, event -> gestureDetector.onTouchEvent(event) }
    }

    override fun onDraw(canvas: Canvas) {
        checkScroll(0f, false)
        super.onDraw(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        if(children.isEmpty()){
            setMeasuredDimension(mPaddingLeft + mPaddingRight, mPaddingTop + mPaddingBottom)
            return
        }

        assert(children.size == 1)

        val child = children.first()
        val lp = child.layoutParams

        val wms = MeasureSpec.makeMeasureSpec(1024 * 16, MeasureSpec.UNSPECIFIED)
        val hms = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec) - (lp.topMargin + lp.bottomMargin + mPaddingTop + mPaddingBottom), MeasureSpec.AT_MOST)

        child.measure(wms, hms)

        shownScroll = scroll.toInt()

        child.mLeft = mPaddingLeft + lp.leftMargin - shownScroll
        child.mTop = mPaddingTop + lp.topMargin
        child.mRight = child.mLeft + child.measuredWidth
        child.mBottom = child.mTop + child.measuredHeight

        contentLength = mPaddingLeft + mPaddingRight + lp.leftMargin + lp.rightMargin + child.measuredWidth

        setMeasuredDimension(when(MeasureSpec.getMode(widthMeasureSpec)){
            MeasureSpec.EXACTLY -> MeasureSpec.getSize(widthMeasureSpec)
            MeasureSpec.AT_MOST -> {
                min(MeasureSpec.getSize(widthMeasureSpec),
                        mPaddingLeft + mPaddingRight + lp.leftMargin + lp.rightMargin + child.measuredWidth)
            }
            MeasureSpec.UNSPECIFIED -> mPaddingLeft + mPaddingRight + lp.leftMargin + lp.rightMargin + child.measuredWidth
            else -> 0
        }, when(MeasureSpec.getMode(heightMeasureSpec)){
            MeasureSpec.EXACTLY -> MeasureSpec.getSize(heightMeasureSpec)
            MeasureSpec.AT_MOST -> {
                min(MeasureSpec.getSize(heightMeasureSpec),mPaddingTop + mPaddingBottom + lp.topMargin + lp.bottomMargin + child.measuredHeight)
            }
            MeasureSpec.UNSPECIFIED -> mPaddingTop + mPaddingBottom + lp.topMargin + lp.bottomMargin + child.measuredHeight
            else -> 0
        })

    }

}