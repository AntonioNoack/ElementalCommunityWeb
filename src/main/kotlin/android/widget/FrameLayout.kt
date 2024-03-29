package android.widget

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import kotlin.math.max

/**
 * stacking views on top of each other
 * */
open class FrameLayout(ctx: Context?, attributeSet: AttributeSet?) : ViewGroup(ctx, attributeSet) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var maxSizeX = 0
        var maxSizeY = 0
        for (child in children) {
            if (child.visibility == GONE) continue
            measureChildWithMargins(child, widthMeasureSpec, heightMeasureSpec)
            placeChild(child, 0, 0)
            val lp = child.layoutParams
            maxSizeX = max(maxSizeX, child.measuredWidth + lp.marginX)
            maxSizeY = max(maxSizeY, child.measuredHeight + lp.marginY)
        }
        maxSizeX += paddingX
        maxSizeY += paddingY
        val lp = layoutParams
        setMeasuredDimension(
            getDefaultSize(widthMeasureSpec, maxSizeX, lp.width),
            getDefaultSize(heightMeasureSpec, maxSizeY, lp.height)
        )
    }
}