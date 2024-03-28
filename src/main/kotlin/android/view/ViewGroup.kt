package android.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import kotlin.math.max

open class ViewGroup(ctx: Context?, attributeSet: AttributeSet?): View(ctx, attributeSet){

    val childCount
        get() = getChildCount()

    fun removeAllViews(){
        children.clear()
        invalidate()
    }

    fun getChildCount() = children.size
    fun getChildAt(index: Int) = children[index]

    var children = ArrayList<View>()

    fun addView(view: View){
        view.mParent = this
        children.add(view)
    }

    override fun init(){
        cloneAttributesFromStyle()
        onInit()
        for(child in children){
            child.init()
        }
    }

    override fun <V: View> findElementById(id: String): V? {
        if(attributeSet.values["id"] == id) return this as V
        for(child in children){
            val found = child.findElementById<V>(id)
            if(found != null) return found
        }
        return null
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for(child in children){
            if(child.isEffectivelyVisible()){
                val save = canvas.save()
                canvas.translate(child.getLeft(), child.getTop())
                canvas.setBounds(0, 0, child.getWidth(), child.getHeight())
                child.draw(canvas)
                canvas.restoreToCount(save)
                // canvas.lineRect(child.mLeft+1, child.mTop+1, child.mRight-1, child.mBottom-1, 0xffff0000.toInt())
                // canvas.lineRect(child.mLeft + child.mPaddingLeft, child.mTop + child.mPaddingTop, child.mRight - child.mPaddingRight, child.mBottom - child.mPaddingBottom, 0xff00ff00.toInt())
                // canvas.lineRect(child.mLeft, child.mTop, child.mLeft + child.measuredWidth, child.mTop + child.measuredHeight, 0xffffff00.toInt())

            }
        }
    }

    /**
     * Ask one of the children of this view to measure itself, taking into
     * account both the MeasureSpec requirements for this view and its padding
     * and margins. The child must have MarginLayoutParams The heavy lifting is
     * done in getChildMeasureSpec.
     *
     * @param child The child to measure
     * @param parentWidthMeasureSpec The width requirements for this view
     * @param widthUsed Extra space that has been used up by the parent
     * horizontally (possibly by other children of the parent)
     * @param parentHeightMeasureSpec The height requirements for this view
     * @param heightUsed Extra space that has been used up by the parent
     * vertically (possibly by other children of the parent)
     */
    fun measureChildWithMargins(child:View,
        parentWidthMeasureSpec:Int, widthUsed:Int,
        parentHeightMeasureSpec:Int, heightUsed:Int) {
        val lp = child.getLayoutParams()

        val childWidthMeasureSpec = getChildMeasureSpec(parentWidthMeasureSpec,
                mPaddingLeft + mPaddingRight + lp.leftMargin + lp.rightMargin
                + widthUsed, lp.width)
        val childHeightMeasureSpec = getChildMeasureSpec(parentHeightMeasureSpec,
        (mPaddingTop + mPaddingBottom + lp.topMargin + lp.bottomMargin
        + heightUsed), lp.height)

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
    fun getChildMeasureSpec(spec: Int, padding: Int, childDimension: Int): Int {
        val specMode = View.MeasureSpec.getMode(spec)
        val specSize = View.MeasureSpec.getSize(spec)

        val size = max(0, specSize - padding)

        var resultSize = 0
        var resultMode = 0

        when (specMode) {
            // Parent has imposed an exact size on us
            View.MeasureSpec.EXACTLY -> if (childDimension >= 0) {
                resultSize = childDimension
                resultMode = View.MeasureSpec.EXACTLY
            } else if (childDimension == View.LayoutParams.MATCH_PARENT) {
                // Child wants to be our size. So be it.
                resultSize = size
                resultMode = View.MeasureSpec.EXACTLY
            } else if (childDimension == View.LayoutParams.WRAP_CONTENT) {
                // Child wants to determine its own size. It can't be
                // bigger than us.
                resultSize = size
                resultMode = View.MeasureSpec.AT_MOST
            }

            // Parent has imposed a maximum size on us
            View.MeasureSpec.AT_MOST -> if (childDimension >= 0) {
                // Child wants a specific size... so be it
                resultSize = childDimension
                resultMode = View.MeasureSpec.EXACTLY
            } else if (childDimension == View.LayoutParams.MATCH_PARENT) {
                // Child wants to be our size, but our size is not fixed.
                // Constrain child to not be bigger than us.
                resultSize = size
                resultMode = View.MeasureSpec.AT_MOST
            } else if (childDimension == View.LayoutParams.WRAP_CONTENT) {
                // Child wants to determine its own size. It can't be
                // bigger than us.
                resultSize = size
                resultMode = View.MeasureSpec.AT_MOST
            }

            // Parent asked to see how big we want to be
            View.MeasureSpec.UNSPECIFIED -> if (childDimension >= 0) {
                // Child wants a specific size... let him have it
                resultSize = childDimension
                resultMode = View.MeasureSpec.EXACTLY
            } else if (childDimension == View.LayoutParams.MATCH_PARENT) {
                // Child wants to be our size... find out how big it should
                // be
                resultSize = if (View.sUseZeroUnspecifiedMeasureSpec) 0 else size
                resultMode = View.MeasureSpec.UNSPECIFIED
            } else if (childDimension == View.LayoutParams.WRAP_CONTENT) {
                // Child wants to determine its own size.... find out how
                // big it should be
                resultSize = if (View.sUseZeroUnspecifiedMeasureSpec) 0 else size
                resultMode = View.MeasureSpec.UNSPECIFIED
            }
        }

        return View.MeasureSpec.makeMeasureSpec(resultSize, resultMode)
    }

}