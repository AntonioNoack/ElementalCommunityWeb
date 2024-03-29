package android.widget

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.core.math.MathUtils.clamp
import kotlin.math.max

open class ScrollView(ctx: Context?, attributeSet: AttributeSet?) : FrameLayout(ctx, attributeSet) {

    var scroll = 0f
    var shownScroll = 0
    var contentLength = 0

    fun checkScroll(dy: Float, invalidate: Boolean) {
        val newScroll = clamp(scroll + dy, 0f, max(0, contentLength - this.measuredHeight).toFloat())
        scroll = newScroll
        if (newScroll.toInt() != shownScroll && invalidate) {
            invalidate()
        }
    }

    init {
        val gestureDetector = GestureDetector(object : GestureDetector.OnGestureListener {
            override fun onDown(event: MotionEvent): Boolean = false
            override fun onFling(e1: MotionEvent?, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean = false
            override fun onLongPress(e: MotionEvent) {}
            override fun onScroll(event: MotionEvent?, e2: MotionEvent, dx: Float, dy: Float): Boolean {
                checkScroll(dy, true)
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
            measureChildWithMargins(child, widthMeasureSpec, MeasureSpec.UNSPECIFIED)
            shownScroll = scroll.toInt()
            placeChild(child, 0, -shownScroll)
            contentLength = idealHeight(child)
            measureByChild(widthMeasureSpec, heightMeasureSpec, child)
        }
    }
}