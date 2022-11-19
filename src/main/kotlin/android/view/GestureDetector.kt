package android.view

import android.content.Context
import me.antonio.noack.webdroid.Runner.now
import kotlin.math.sqrt

class GestureDetector(val ctx: Context?, val listener: OnGestureListener){

    constructor(listener: OnGestureListener): this(null, listener)

    interface OnGestureListener {
        fun onShowPress(e: MotionEvent?)
        fun onDown(event: MotionEvent?): Boolean
        fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean
        fun onScroll(event: MotionEvent?, e2: MotionEvent?, dx: Float, dy: Float): Boolean
        fun onLongPress(e: MotionEvent?)
        fun onSingleTapUp(event: MotionEvent?): Boolean
    }

    var lastTime = 0.0
    var motionDistance = 0f
    var lastX = -1f
    var lastY = 0f
    var startTime = 0.0

    fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return false
        lastTime = now()
        when(event.actionMasked){
            MotionEvent.ACTION_DOWN -> {
                motionDistance = 0f
                lastX = event.originalX
                lastY = event.originalY
                startTime = event.time
                return listener.onDown(event)
            }
            MotionEvent.ACTION_MOVE -> {
                val returnValue = if(lastX > -1f){
                    val deltaX = lastX - event.originalX
                    val deltaY = lastY - event.originalY
                    motionDistance += sqrt(sq(deltaX, deltaY))
                    listener.onScroll(event, null, deltaX, deltaY)
                } else true
                lastX = event.originalX
                lastY = event.originalY
                return returnValue
            }
            MotionEvent.ACTION_UP -> {
                if(motionDistance < 30f){
                    if(event.time - startTime > 0.3) listener.onLongPress(event)
                    return listener.onSingleTapUp(event)
                }
            }
            MotionEvent.ACTION_SCROLL -> {
                listener.onScroll(event, null, event.motionDX, event.motionDY)
            }
        }
        return false
    }

    fun sq(x: Float, y: Float) = x*x+y*y

}