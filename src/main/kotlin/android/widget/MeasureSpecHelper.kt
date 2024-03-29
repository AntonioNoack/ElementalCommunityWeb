package android.widget

import android.view.Gravity
import android.view.View
import kotlin.math.min

/**
 * Utility to return a default size. Uses the supplied size if the
 * MeasureSpec imposed no constraints. Will get larger if allowed
 * by the MeasureSpec.
 *
 * @param measureSpec Constraints imposed by the parent
 * @param size Default size for this view
 * @return The size this view should be.
 */
fun getDefaultSize(measureSpec: Int, size: Int, lp: Int): Int {
    val selfSize = View.MeasureSpec.getSize(measureSpec)
    return when (View.MeasureSpec.getMode(measureSpec)) {
        View.MeasureSpec.AT_MOST -> when (lp) {
            View.LayoutParams.MATCH_PARENT -> selfSize
            else -> min(selfSize, size) // wrap_content
        }
        View.MeasureSpec.EXACTLY -> selfSize
        else -> size // UNSPECIFIED
    }
}

fun View.measureByEmpty(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    val lp = layoutParams
    setMeasuredDimension(
        getDefaultSize(widthMeasureSpec, paddingX, lp.width),
        getDefaultSize(heightMeasureSpec, paddingY, lp.height)
    )
}

fun View.idealWidth(child: View, sizeX: Int = 1): Int {
    return paddingX + (child.layoutParams.marginX + child.measuredWidth) * sizeX
}

fun View.idealHeight(child: View, sizeY: Int = 1): Int {
    return paddingY + (child.layoutParams.marginY + child.measuredHeight) * sizeY
}

fun View.measureByChild(widthMeasureSpec: Int, heightMeasureSpec: Int, child: View) {
    measureByChild(widthMeasureSpec, heightMeasureSpec, child, 1, 1)
}

fun View.measureByChild(widthMeasureSpec: Int, heightMeasureSpec: Int, child: View, sizeX: Int, sizeY: Int) {
    val lp = layoutParams
    setMeasuredDimension(
        getDefaultSize(widthMeasureSpec, idealWidth(child, sizeX), lp.width),
        getDefaultSize(heightMeasureSpec, idealHeight(child, sizeY), lp.height)
    )
}

fun View.placeChild(child: View, dx: Int, dy: Int) {
    val lp = child.layoutParams
    val x = paddingLeft + lp.leftMargin + dx
    val y = paddingTop + lp.topMargin + dy
    child.layout(x, y, x + child.measuredWidth, y + child.measuredHeight)
}

fun View.placeChild(child: View, dx: Int, dy: Int, sx: Int, sy: Int) {
    val lp = child.layoutParams
    val x = Gravity.getOffsetX(lp.gravity, sx, child.measuredWidth) + paddingLeft + lp.leftMargin + dx
    val y = Gravity.getOffsetY(lp.gravity, sy, child.measuredHeight) + paddingTop + lp.topMargin + dy
    val w = Gravity.getSizeX(lp.gravity, sx, child.measuredWidth)
    val h = Gravity.getSizeY(lp.gravity, sy, child.measuredHeight)
    child.layout(x, y, x + w, y + h)
}