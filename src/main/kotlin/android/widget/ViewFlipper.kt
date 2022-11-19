package android.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.math.MathUtils.clamp
import kotlin.math.max
import kotlin.math.min

// improved onMeasure [y]
open class ViewFlipper(ctx: Context?, attributeSet: AttributeSet?): ViewGroup(ctx, attributeSet){

    private var mDisplayedChild = 0
    var displayedChild: Int
        get() = mDisplayedChild
        set(value){
            if(value != mDisplayedChild){
                children[mDisplayedChild].visibility = View.GONE
                children[value].visibility = View.VISIBLE
                mDisplayedChild = value
                invalidate()
            }
        }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if(children.isEmpty()) return super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val modeX = MeasureSpec.getMode(widthMeasureSpec)
        val modeY = MeasureSpec.getMode(heightMeasureSpec)
        val displayedChild = clamp(displayedChild, 0, children.lastIndex)
        val child = children[displayedChild]
        val sizeX: Int
        val sizeY: Int
        if(child.visibility != View.GONE){
            val lp = child.layoutParams
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0)
            child.mLeft = 0
            child.mTop = 0
            child.mRight = max(0, child.getMeasuredWidth())
            child.mBottom = max(0, child.getMeasuredHeight())
            sizeX = max(0, when(modeX){
                MeasureSpec.EXACTLY -> MeasureSpec.getSize(widthMeasureSpec)
                MeasureSpec.AT_MOST -> min(MeasureSpec.getSize(widthMeasureSpec), child.getMeasuredWidth() + mPaddingLeft + mPaddingRight + lp.leftMargin + lp.rightMargin)
                else -> child.getMeasuredWidth() + mPaddingLeft + mPaddingRight + lp.leftMargin + lp.rightMargin
            })
            sizeY = max(0, when(modeY){
                MeasureSpec.EXACTLY -> MeasureSpec.getSize(heightMeasureSpec)
                MeasureSpec.AT_MOST -> min(MeasureSpec.getSize(heightMeasureSpec), child.getMeasuredHeight() + mPaddingTop + mPaddingBottom + lp.topMargin + lp.bottomMargin)
                else -> child.getMeasuredHeight() + mPaddingTop + mPaddingBottom + lp.topMargin + lp.bottomMargin
            })
        } else {
            sizeX = when(modeX){
                MeasureSpec.EXACTLY -> max(0, MeasureSpec.getSize(widthMeasureSpec))
                else -> 0
            }
            sizeY = when(modeY){
                MeasureSpec.EXACTLY -> max(0, MeasureSpec.getSize(heightMeasureSpec))
                else -> 0
            }
        }
        for((index, otherChild) in children.withIndex()){
            if(index != displayedChild){
                otherChild.mLeft = 0
                otherChild.mTop = 0
                otherChild.mRight = 0
                otherChild.mBottom = 0
            }
        }

        setMeasuredDimension(sizeX, sizeY)
    }

}