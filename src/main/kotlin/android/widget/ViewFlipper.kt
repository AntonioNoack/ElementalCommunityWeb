package android.widget

import android.content.Context
import android.util.AttributeSet

open class ViewFlipper(ctx: Context?, attributeSet: AttributeSet?) : FrameLayout(ctx, attributeSet) {

    var displayedChild: Int = 0
        set(value) {
            if (field != value) {
                field = value
                invalidate()
            }
        }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        for (i in children.indices) {
            children[i].visibility = if (i != displayedChild) GONE else VISIBLE
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}