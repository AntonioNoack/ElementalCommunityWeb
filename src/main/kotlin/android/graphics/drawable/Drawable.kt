package android.graphics.drawable

import android.graphics.Canvas
import android.graphics.RectF

abstract class Drawable: RectF(){
    fun getIntrinsicWidth() = width().toInt()
    fun getIntrinsicHeight() = height().toInt()
    abstract fun getMinimumWidth(): Int
    abstract fun getMinimumHeight(): Int
    abstract fun draw(canvas: Canvas)
}