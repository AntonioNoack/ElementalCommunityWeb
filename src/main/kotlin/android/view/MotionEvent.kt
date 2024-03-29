package android.view

import me.antonio.noack.webdroid.Runner.touches

open class MotionEvent(
    x: Float, y: Float,
    val motionDX: Float, val motionDY: Float,
    val zoom: Float, val actionMasked: Int
) : Event(x, y) {

    constructor(x: Float, y: Float, action: Int) : this(x, y, 0f, 0f, 0f, action)
    constructor(x: Float, y: Float, zoom: Float, action: Int) : this(x, y, 0f, 0f, zoom, action)
    constructor(x: Float, y: Float, dx: Float, dy: Float, action: Int) : this(x, y, dx, dy, 0f, action)

    val originalX = x
    val originalY = y

    fun getX(index: Int) = touches[index]?.currentX ?: x
    fun getY(index: Int) = touches[index]?.currentY ?: y

    val pointerCount
        get() = touches.size

    fun getPointerCount() = touches.size

    override fun call(view: View): Boolean {
        return view.touchListener?.invoke(view, this) ?: false
    }

    override fun toString(): String = "MotionEvent[$x $y $actionMasked]"

    companion object {
        const val ACTION_DOWN = 0
        const val ACTION_UP = 1
        const val ACTION_MOVE = 2
        const val ACTION_POINTER_DOWN = 3
        const val ACTION_POINTER_UP = 4
        const val ACTION_HOVER_MOVE = 5
        const val ACTION_SCROLL = 6
        const val ACTION_ZOOM = 7
    }
}