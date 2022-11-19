package android.widget

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import kotlin.math.max

/**
 * stacking views on top of each other
 * */
// improved onMeasure [y]
open class FrameLayout(ctx: Context?, attributeSet: AttributeSet?): ViewGroup(ctx, attributeSet){

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val modeX = MeasureSpec.getMode(widthMeasureSpec)
        val modeY = MeasureSpec.getMode(heightMeasureSpec)
        val exX = modeX == MeasureSpec.EXACTLY
        val exY = modeY == MeasureSpec.EXACTLY
        // margin and padding...
        if(exX && exY){
            for(child in children){
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0)
                child.mLeft = mPaddingLeft
                child.mTop = mPaddingTop
                child.mRight = child.mLeft + child.getMeasuredWidth()
                child.mBottom = child.mTop + child.getMeasuredHeight()
            }
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec))
        } else {
            var maxSizeX = 0
            var maxSizeY = 0
            for(child in children){
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0)
                child.mLeft = mPaddingLeft
                child.mTop = mPaddingTop
                child.mRight = child.mLeft + child.getMeasuredWidth()
                child.mBottom = child.mTop + child.getMeasuredHeight()
                val lp = child.layoutParams
                maxSizeX = max(maxSizeX, child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin)
                maxSizeY = max(maxSizeY, child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin)
            }
            maxSizeX += mPaddingLeft + mPaddingRight
            maxSizeY += mPaddingTop + mPaddingBottom
            setMeasuredDimension(
                    if(exX) MeasureSpec.getSize(widthMeasureSpec) else maxSizeX,
                    if(exY) MeasureSpec.getSize(heightMeasureSpec) else maxSizeY)
        }
    }

}