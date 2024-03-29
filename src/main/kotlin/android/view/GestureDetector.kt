package android.view

import android.content.Context
import kotlin.math.sqrt

class GestureDetector(val ctx: Context?, val listener: OnGestureListener) {

    constructor(listener: OnGestureListener) : this(null, listener)

    interface OnGestureListener {
        fun onShowPress(e: MotionEvent)
        fun onDown(event: MotionEvent): Boolean
        fun onFling(e1: MotionEvent?, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean
        fun onScroll(event: MotionEvent?, e2: MotionEvent, dx: Float, dy: Float): Boolean
        fun onLongPress(e: MotionEvent)
        fun onSingleTapUp(event: MotionEvent): Boolean
    }

    open class SimpleOnGestureListener : OnGestureListener {
        override fun onShowPress(e: MotionEvent) {}
        override fun onDown(event: MotionEvent): Boolean = false
        override fun onFling(e1: MotionEvent?, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean = false
        override fun onScroll(event: MotionEvent?, e2: MotionEvent, dx: Float, dy: Float): Boolean = false
        override fun onLongPress(e: MotionEvent) {}
        override fun onSingleTapUp(event: MotionEvent): Boolean = false
    }

    private var motionDistance = 0f
    private var startTime = 0.0
    private var isDown = false

    fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return false
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                motionDistance = 0f
                startTime = event.time
                isDown = true
                return listener.onDown(event)
            }
            MotionEvent.ACTION_MOVE -> {
                if (isDown) {
                    val deltaX = event.motionDX
                    val deltaY = event.motionDY
                    motionDistance += sqrt(deltaX * deltaX + deltaY * deltaY)
                    listener.onScroll(event, event, deltaX, deltaY)
                }
            }
            MotionEvent.ACTION_UP -> {
                isDown = false
                if (motionDistance < 30f) {
                    if (event.time - startTime > 0.3) listener.onLongPress(event)
                    return listener.onSingleTapUp(event)
                }
            }
            MotionEvent.ACTION_SCROLL -> {
                listener.onScroll(event, event, event.motionDX, event.motionDY)
            }
        }
        return false
    }
}