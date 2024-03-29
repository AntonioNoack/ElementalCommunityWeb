package android.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import kotlin.math.max

open class ViewGroup(ctx: Context?, attributeSet: AttributeSet?) : View(ctx, attributeSet) {

    val childCount get() = children.size

    fun removeAllViews() {
        children.clear()
        invalidate()
    }

    fun getChildCount() = children.size
    fun getChildAt(index: Int) = children[index]

    val children = ArrayList<View>()

    override fun init() {
        cloneAttributesFromStyle()
        onInit()
        for (child in children) {
            child.init()
        }
    }

    override fun destroy() {
        super.destroy()
        for (child in children) {
            child.destroy()
        }
    }

    override fun <V : View> findViewById(id: String): V? {
        val self = super.findViewById<V>(id)
        if (self != null) return self
        for (child in children) {
            val found = child.findViewById<V>(id)
            if (found != null) return found
        }
        return null
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (child in children) {
            if (child.isEffectivelyVisible()) {
                val save = canvas.save()
                canvas.translate(child.getLeft(), child.getTop())
                canvas.setBounds(0, 0, child.getWidth(), child.getHeight())
                child.draw(canvas)
                canvas.restoreToCount(save)
                // showDebugLines(canvas, child)
            }
        }
    }

    /*private fun showDebugLines(canvas: Canvas, child: View) {
        canvas.lineRect(
            child.mLeft + 1,
            child.mTop + 1,
            child.mRight - 1,
            child.mBottom - 1,
            0x77ff0000
        )
        canvas.lineRect(
            child.mLeft + child.paddingLeft,
            child.mTop + child.paddingTop,
            child.mRight - child.paddingRight,
            child.mBottom - child.paddingBottom,
            0x7700ff00
        )
        canvas.lineRect(
            child.mLeft,
            child.mTop,
            child.mLeft + child.measuredWidth,
            child.mTop + child.measuredHeight,
            0x77ffff00
        )
    }*/

    /**
     * Ask one of the children of this view to measure itself, taking into
     * account both the MeasureSpec requirements for this view and its padding
     * and margins. The child must have MarginLayoutParams The heavy lifting is
     * done in getChildMeasureSpec.
     *
     * @param child The child to measure
     * @param parentWidthMeasureSpec The width requirements for this view
     * @param parentHeightMeasureSpec The height requirements for this view
     */
    fun measureChildWithMargins(child: View, parentWidthMeasureSpec: Int, parentHeightMeasureSpec: Int) {
        val lp = child.layoutParams
        val childWidthMeasureSpec = getChildMeasureSpec(
            parentWidthMeasureSpec, paddingX + lp.marginX, lp.width
        )
        val childHeightMeasureSpec = getChildMeasureSpec(
            parentHeightMeasureSpec, paddingY + lp.marginY, lp.height
        )
        child.measure(childWidthMeasureSpec, childHeightMeasureSpec)
    }

    /**
     * Does the hard part of measureChildren: figuring out the MeasureSpec to
     * pass to a particular child. This method figures out the right MeasureSpec
     * for one dimension (height or width) of one child view.
     *
     * The goal is to combine information from our MeasureSpec with the
     * LayoutParams of the child to get the best possible results. For example,
     * if the this view knows its size (because its MeasureSpec has a mode of
     * EXACTLY), and the child has indicated in its LayoutParams that it wants
     * to be the same size as the parent, the parent should ask the child to
     * layout given an exact size.
     *
     * @param spec The requirements for this view
     * @param padding The padding of this view for the current dimension and
     * margins, if applicable
     * @param childDimension How big the child wants to be in the current
     * dimension
     * @return a MeasureSpec integer for the child
     */
    private fun getChildMeasureSpec(spec: Int, padding: Int, childDimension: Int): Int {
        val specMode = MeasureSpec.getMode(spec)
        val specSize = MeasureSpec.getSize(spec)

        val size = max(0, specSize - padding)

        var resultSize = 0
        var resultMode = 0

        when (specMode) {
            // Parent has imposed an exact size on us
            MeasureSpec.EXACTLY -> if (childDimension >= 0) {
                resultSize = childDimension
                resultMode = MeasureSpec.EXACTLY
            } else if (childDimension == LayoutParams.MATCH_PARENT) {
                // Child wants to be our size. So be it.
                resultSize = size
                resultMode = MeasureSpec.EXACTLY
            } else if (childDimension == LayoutParams.WRAP_CONTENT) {
                // Child wants to determine its own size. It can't be
                // bigger than us.
                resultSize = size
                resultMode = MeasureSpec.AT_MOST
            }

            // Parent has imposed a maximum size on us
            MeasureSpec.AT_MOST -> if (childDimension >= 0) {
                // Child wants a specific size... so be it
                resultSize = childDimension
                resultMode = MeasureSpec.EXACTLY
            } else if (childDimension == LayoutParams.MATCH_PARENT) {
                // Child wants to be our size, but our size is not fixed.
                // Constrain child to not be bigger than us.
                resultSize = size
                resultMode = MeasureSpec.AT_MOST
            } else if (childDimension == LayoutParams.WRAP_CONTENT) {
                // Child wants to determine its own size. It can't be
                // bigger than us.
                resultSize = size
                resultMode = MeasureSpec.AT_MOST
            }

            // Parent asked to see how big we want to be
            MeasureSpec.UNSPECIFIED -> if (childDimension >= 0) {
                // Child wants a specific size... let him have it
                resultSize = childDimension
                resultMode = MeasureSpec.EXACTLY
            } else if (childDimension == LayoutParams.MATCH_PARENT) {
                // Child wants to be our size... find out how big it should
                // be
                resultSize = size
                resultMode = MeasureSpec.UNSPECIFIED
            } else if (childDimension == LayoutParams.WRAP_CONTENT) {
                // Child wants to determine its own size.... find out how
                // big it should be
                resultSize = size
                resultMode = MeasureSpec.UNSPECIFIED
            }
        }

        return MeasureSpec.makeMeasureSpec(resultSize, resultMode)
    }

    override fun processEvent(event: Event, ox: Float, oy: Float, dx: Int, dy: Int): View? {
        for (ci in children.lastIndex downTo 0) {
            val child = children[ci]
            if (child.visibility != VISIBLE) continue
            val ndx = dx + child.mLeft
            val ndy = dy + child.mTop
            val nx = ox - ndx
            val ny = oy - ndy
            if (child.contains(nx, ny)) {
                event.x = nx
                event.y = ny
                val result = child.processEvent(event, ox, oy, ndx, ndy)
                if (result != null) {
                    return result
                }
            }
        }
        return super.processEvent(event, ox, oy, dx, dy)
    }

}