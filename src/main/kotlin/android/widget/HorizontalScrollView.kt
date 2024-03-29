package android.widget

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.core.math.MathUtils
import kotlin.math.max

class HorizontalScrollView(ctx: Context, attributeSet: AttributeSet?) : ViewGroup(ctx, attributeSet) {

    var scroll = 0f
    var shownScroll = 0
    var contentLength = 0

    fun checkScroll(dx: Float, invalidate: Boolean) {
        val newScroll = MathUtils.clamp(scroll + dx, 0f, max(0, contentLength - this.measuredHeight).toFloat())
        scroll = newScroll
        if (newScroll.toInt() != shownScroll) {
            if (invalidate) invalidate()
        }
    }

    init {
        val gestureDetector = GestureDetector(object : GestureDetector.OnGestureListener {
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
        val child = children.firstOrNull()
        if (child == null) {
            measureByEmpty(widthMeasureSpec, heightMeasureSpec)
        } else {
            measureChildWithMargins(child, MeasureSpec.UNSPECIFIED, heightMeasureSpec)
            shownScroll = scroll.toInt()
            placeChild(child, -shownScroll, 0)
            contentLength = idealWidth(child, 1)
            measureByChild(widthMeasureSpec, heightMeasureSpec, child)
        }
    }
}