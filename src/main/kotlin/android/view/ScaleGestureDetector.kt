package android.view

import android.content.Context
import android.widget.Toast
import me.antonio.noack.maths.MathsUtils.sq
import kotlin.math.sqrt

class ScaleGestureDetector(val ctx: Context?, val listener: OnScaleGestureListener){

    var scaleFactor = 1f

    interface OnScaleGestureListener {
        fun onScale(detector: ScaleGestureDetector): Boolean
        fun onScaleBegin(detector: ScaleGestureDetector): Boolean
        fun onScaleEnd(detector: ScaleGestureDetector)
    }

    var d1 = -1f

    // idc about scaling with fingers here, but when we get multiple touches... on a phone itself
    fun onTouchEvent(event: MotionEvent): Boolean {
        return when(event.actionMasked){
            MotionEvent.ACTION_ZOOM -> {
                scaleFactor = event.zoom
                listener.onScale(this)
                true
            }
            MotionEvent.ACTION_MOVE -> {
                if(event.getPointerCount() == 2){
                    if(d1 > -1f){
                        // has data :)
                        val d2 = sq(event.getX(0) - event.getX(1), event.getY(0) - event.getY(1))
                        val factor = sqrt(d2/d1)
                        scaleFactor = factor
                        listener.onScale(this)
                        d1 = d2
                    } else {
                        d1 = sq(event.getX(0) - event.getX(1), event.getY(0) - event.getY(1))
                    }
                    true
                } else {
                    d1 = -1f
                    false
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_UP, MotionEvent.ACTION_POINTER_DOWN -> {
                d1 = -1f
                false
            }
            else -> false
        }
    }

    // fun dsq(event: MotionEvent, index: Int, x0: Double, y0: Double) = sq(event.getX(index)-x0, event.getY(index))

}