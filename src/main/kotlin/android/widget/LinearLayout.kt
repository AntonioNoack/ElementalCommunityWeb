/*
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import android.view.ScaleGestureDetector
import android.view.View
import android.view.ViewGroup
import androidx.core.math.MathUtils.clamp
import me.antonio.noack.maths.MathsUtils.originalSpFactor
import me.antonio.noack.maths.MathsUtils.spFactor
import me.antonio.noack.webdroid.Runner
import org.w3c.dom.set
import kotlin.browser.localStorage
import kotlin.math.max
import kotlin.math.min

/**
 * A layout that arranges other views either horizontally in a single column
 * or vertically in a single row.
 *
 *
 * The following snippet shows how to include a linear layout in your layout XML file:
 *
 * <pre>&lt;LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
 * android:layout_width="match_parent"
 * android:layout_height="match_parent"
 * android:paddingLeft="16dp"
 * android:paddingRight="16dp"
 * android:orientation="horizontal"
 * android:gravity="center"&gt;
 *
 * &lt;!-- Include other widget or layout tags here. These are considered
 * "child views" or "children" of the linear layout --&gt;
 *
 * &lt;/LinearLayout&gt;</pre>
 *
 *
 * Set [android:orientation][android.R.styleable.LinearLayout_orientation] to specify
 * whether child views are displayed in a row or column.
 *
 *
 * To control how linear layout aligns all the views it contains, set a value for
 * [android:gravity][android.R.styleable.LinearLayout_gravity].  For example, the
 * snippet above sets android:gravity to "center".  The value you set affects
 * both horizontal and vertical alignment of all child views within the single row or column.
 *
 *
 * You can set
 * [android:layout_weight][android.R.styleable.LinearLayout_Layout_layout_weight]
 * on individual child views to specify how linear layout divides remaining space amongst
 * the views it contains. See the
 * [Linear Layout](https://developer.android.com/guide/topics/ui/layout/linear.html)
 * guide for an example.
 *
 *
 * See
 * [View.LayoutParams][android.widget.View.LayoutParams]
 * to learn about other attributes you can set on a child view to affect its
 * position and size in the containing linear layout.
 *
 * @attr ref android.R.styleable#LinearLayout_baselineAligned
 * @attr ref android.R.styleable#LinearLayout_baselineAlignedChildIndex
 * @attr ref android.R.styleable#LinearLayout_gravity
 * @attr ref android.R.styleable#LinearLayout_measureWithLargestChild
 * @attr ref android.R.styleable#LinearLayout_orientation
 * @attr ref android.R.styleable#LinearLayout_weightSum
 */
open class LinearLayout constructor(context: Context?, attrs: AttributeSet? = null) : ViewGroup(context, attrs) {

    /**
     * Compatibility check. Old versions of the platform would give different
     * results from measurement passes using EXACTLY and non-EXACTLY modes,
     * even when the resulting size was the same.
     */
    private var mAllowInconsistentMeasurement: Boolean = false

    var mOrientation = VERTICAL

    /**
     * Whether the children of this layout are baseline aligned.  Only applicable
     * if [.mOrientation] is horizontal.
     */
    /**
     *
     * Indicates whether widgets contained within this layout are aligned
     * on their baseline or not.
     *
     * @return true when widgets are baseline-aligned, false otherwise
     */
    /**
     *
     * Defines whether widgets contained in this layout are
     * baseline-aligned or not.
     *
     * @param baselineAligned true to align widgets on their baseline,
     * false otherwise
     *
     * @attr ref android.R.styleable#LinearLayout_baselineAligned
     */
    // @ViewDebug.ExportedProperty(category = "layout")
    // @set:android.view.RemotableViewMethod
    var isBaselineAligned = true

    /**
     * If this layout is part of another layout that is baseline aligned,
     * use the child at this index as the baseline.
     *
     * Note: this is orthogonal to [.mBaselineAligned], which is concerned
     * with whether the children of this layout are baseline aligned.
     */
    // @ViewDebug.ExportedProperty(category = "layout")
    private var mBaselineAlignedChildIndex = -1

    /**
     * The additional offset to the child's baseline.
     * We'll calculate the baseline of this layout as we measure vertically; for
     * horizontal linear layouts, the offset of 0 is appropriate.
     */
    // @ViewDebug.ExportedProperty(category = "measurement")
    private var mBaselineChildTop = 0

    /**
     * Returns the current orientation.
     *
     * @return either [.HORIZONTAL] or [.VERTICAL]
     */
    /**
     * Should the layout be a column or a row.
     * @param orientation Pass [.HORIZONTAL] or [.VERTICAL]. Default
     * value is [.HORIZONTAL].
     *
     * @attr ref android.R.styleable#LinearLayout_orientation
     */
    // @ViewDebug.ExportedProperty(category = "measurement")
    var orientation: Int = 0
        set(orientation) {
            if (this.orientation != orientation) {
                field = orientation
                requestLayout()
            }
        }
    
    private var mGravity = Gravity.START or Gravity.TOP

    // @ViewDebug.ExportedProperty(category = "measurement")
    private var mTotalLength: Int = 0

    // @ViewDebug.ExportedProperty(category = "layout")
    private var mWeightSum: Float = 0.toFloat()

    /**
     * When true, all children with a weight will be considered having
     * the minimum size of the largest child. If false, all children are
     * measured normally.
     *
     * @return True to measure children with a weight using the minimum
     * size of the largest child, false otherwise.
     *
     * @attr ref android.R.styleable#LinearLayout_measureWithLargestChild
     */
    /**
     * When set to true, all children with a weight will be considered having
     * the minimum size of the largest child. If false, all children are
     * measured normally.
     *
     * Disabled by default.
     *
     * @param enabled True to measure children with a weight using the
     * minimum size of the largest child, false otherwise.
     *
     * @attr ref android.R.styleable#LinearLayout_measureWithLargestChild
     */
    var isMeasureWithLargestChildEnabled: Boolean = false

    private var mMaxAscent: IntArray? = null
    private var mMaxDescent: IntArray? = null

    /**
     * @return the divider Drawable that will divide each item.
     *
     * @see .setDividerDrawable
     * @attr ref android.R.styleable#LinearLayout_divider
     */
    /**
     * Set a drawable to be used as a divider between items.
     *
     * @param divider Drawable that will divide each item.
     *
     * @see .setShowDividers
     * @attr ref android.R.styleable#LinearLayout_divider
     */
    var dividerDrawable: Drawable? = null
        set(divider) {
            if (divider == dividerDrawable) {
                return
            }
            field = divider
            if (divider != null) {
                dividerWidth = divider.getIntrinsicWidth()
                mDividerHeight = divider.getIntrinsicHeight()
            } else {
                dividerWidth = 0
                mDividerHeight = 0
            }

            setWillNotDraw(!isShowingDividers)
            requestLayout()
        }
    /**
     * Get the width of the current divider drawable.
     *
     * @hide Used internally by framework.
     */
    var dividerWidth: Int = 0
        private set
    private var mDividerHeight: Int = 0
    private var mShowDividers: Int = 0
    private var mDividerPadding: Int = 0

    private var mLayoutDirection = VERTICAL

    /**
     * Returns `true` if this layout is currently configured to show at least one
     * divider.
     */
    private val isShowingDividers: Boolean
        get() = mShowDividers != SHOW_DIVIDER_NONE && dividerDrawable != null

    /**
     * @return A flag set indicating how dividers should be shown around items.
     * @see .setShowDividers
     */
    /**
     * Set how dividers should be shown between items in this layout
     *
     * @param showDividers One or more of [.SHOW_DIVIDER_BEGINNING],
     * [.SHOW_DIVIDER_MIDDLE], or [.SHOW_DIVIDER_END]
     * to show dividers, or [.SHOW_DIVIDER_NONE] to show no dividers.
     */
    var showDividers: Int
        get() = mShowDividers
        set(showDividers) {
            if (showDividers == mShowDividers) {
                return
            }
            mShowDividers = showDividers

            setWillNotDraw(!isShowingDividers)
            requestLayout()
        }

    /**
     * Get the padding size used to inset dividers in pixels
     *
     * @see .setShowDividers
     * @see .setDividerDrawable
     * @see .setDividerPadding
     */
    /**
     * Set padding displayed on both ends of dividers. For a vertical layout, the padding is applied
     * to left and right end of dividers. For a horizontal layout, the padding is applied to top and
     * bottom end of dividers.
     *
     * @param padding Padding value in pixels that will be applied to each end
     *
     * @see .setShowDividers
     * @see .setDividerDrawable
     * @see .getDividerPadding
     */
    var dividerPadding: Int
        get() = mDividerPadding
        set(padding) {
            if (padding == mDividerPadding) {
                return
            }
            mDividerPadding = padding

            if (isShowingDividers) {
                requestLayout()
                invalidate()
            }
        }

    /**
     * Finds the last child that is not gone. The last child will be used as the reference for
     * where the end divider should be drawn.
     */
    private val lastNonGoneChild: View?
        get() {
            for (i in virtualChildCount - 1 downTo 0) {
                val child = getVirtualChildAt(i)
                if (child != null && child.getVisibility() != View.GONE) {
                    return child
                }
            }
            return null
        }

    // this is just the default case, safe to return -1
    // the user picked an index that points to something that doesn't
    // know how to calculate its baseline.
    // TODO: This should try to take into account the virtual offsets
    // (See getNextLocationOffset and getLocationOffset)
    // We should add to childTop:
    // sum([getNextLocationOffset(getChildAt(i)) / i < mBaselineAlignedChildIndex])
    // and also add:
    // getLocationOffset(child)
    val baseline: Int
        
        get() {
            if (mBaselineAlignedChildIndex < 0) {
                return super.getBaseline()
            }

            if (getChildCount() <= mBaselineAlignedChildIndex) {
                throw RuntimeException("mBaselineAlignedChildIndex of LinearLayout " + "set to an index that is out of bounds.")
            }

            val child = getChildAt(mBaselineAlignedChildIndex)
            val childBaseline = child.getBaseline()

            if (childBaseline == -1) {
                if (mBaselineAlignedChildIndex == 0) {
                    return -1
                }
                throw RuntimeException("mBaselineAlignedChildIndex of LinearLayout " + "points to a View that doesn't know how to get its baseline.")
            }
            var childTop = mBaselineChildTop

            if (orientation == VERTICAL) {
                val majorGravity = mGravity and Gravity.VERTICAL_GRAVITY_MASK
                if (majorGravity != Gravity.TOP) {
                    when (majorGravity) {
                        Gravity.BOTTOM -> childTop = mBottom - mTop - mPaddingBottom - mTotalLength

                        Gravity.CENTER_VERTICAL -> childTop += (mBottom - mTop - mPaddingTop - mPaddingBottom - mTotalLength) / 2
                    }
                }
            }

            val lp = child.getLayoutParams()
            return childTop + lp.topMargin + childBaseline
        }

    /**
     * @return The index of the child that will be used if this layout is
     * part of a larger layout that is baseline aligned, or -1 if none has
     * been set.
     */
    /**
     * @param i The index of the child that will be used if this layout is
     * part of a larger layout that is baseline aligned.
     *
     * @attr ref android.R.styleable#LinearLayout_baselineAlignedChildIndex
     */
    var baselineAlignedChildIndex: Int
        get() = mBaselineAlignedChildIndex
        set(i) {
            if (i < 0 || i >= getChildCount()) {
                throw IllegalArgumentException("base aligned child index out "
                        + "of range (0, " + getChildCount() + ")")
            }
            mBaselineAlignedChildIndex = i
        }

    /**
     *
     * Returns the virtual number of children. This number might be different
     * than the actual number of children if the layout can hold virtual
     * children. Refer to
     * [android.widget.TableLayout] and [android.widget.TableRow]
     * for an example.
     *
     * @return the virtual number of children
     */
    val virtualChildCount: Int
        get() = getChildCount()

    /**
     * Returns the desired weights sum.
     *
     * @return A number greater than 0.0f if the weight sum is defined, or
     * a number lower than or equals to 0.0f if not weight sum is
     * to be used.
     */
    /**
     * Defines the desired weights sum. If unspecified the weights sum is computed
     * at layout time by adding the layout_weight of each child.
     *
     * This can be used for instance to give a single child 50% of the total
     * available space by giving it a layout_weight of 0.5 and setting the
     * weightSum to 1.0.
     *
     * @param weightSum a number greater than 0.0f, or a number lower than or equals
     * to 0.0f if the weight sum should be computed from the children's
     * layout_weight
     */
    var weightSum: Float
        get() = mWeightSum
        set(weightSum) {
            mWeightSum = max(0.0f, weightSum)
        }

    /**
     * Returns the current gravity. See [android.view.Gravity]
     *
     * @return the current gravity.
     * @see .setGravity
     */
    /**
     * Describes how the child views are positioned. Defaults to GRAVITY_TOP. If
     * this layout has a VERTICAL orientation, this controls where all the child
     * views are placed if there is extra vertical space. If this layout has a
     * HORIZONTAL orientation, this controls the alignment of the children.
     *
     * @param gravity See [android.view.Gravity]
     *
     * @attr ref android.R.styleable#LinearLayout_gravity
     */
    var gravity: Int
        get() = mGravity
        
        set(gravity) {
            var gravity = gravity
            if (mGravity != gravity) {
                if (gravity and Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK == 0) {
                    gravity = gravity or Gravity.START
                }

                if (gravity and Gravity.VERTICAL_GRAVITY_MASK == 0) {
                    gravity = gravity or Gravity.TOP
                }

                mGravity = gravity
                requestLayout()
            }
        }


    var displayRect = Rect()
    var inoutRect = Rect()

    override fun onInit() {
        super.onInit()

        orientation = if(attributeSet.getString("orientation", "") == "horizontal") HORIZONTAL else VERTICAL
        mWeightSum = attributeSet.getFloat("weightSum", -1f)
        isMeasureWithLargestChildEnabled = attributeSet.getBoolean("measureWithLargestChild", false)
        mShowDividers = when(attributeSet.getString("showDividers", "none").toLowerCase()){
            "beginning" -> SHOW_DIVIDER_BEGINNING
            "middle" -> SHOW_DIVIDER_MIDDLE
            "end" -> SHOW_DIVIDER_END
            else -> SHOW_DIVIDER_NONE
        }
        mDividerPadding = attributeSet.getSize("dividerPadding", 0)
        dividerDrawable = attributeSet.getDrawable("divider", null)
        mAllowInconsistentMeasurement = Build.VERSION.SDK_INT <= Build.VERSION_CODES.M

        if(getId() == "appZoom"){
            println("Registered AppZoom")
            val scaleDetector = ScaleGestureDetector(context, object: ScaleGestureDetector.OnScaleGestureListener {
                override fun onScale(detector: ScaleGestureDetector?): Boolean {
                    detector ?: return false
                    val newScale = clamp(spFactor * detector.scaleFactor, 0.1f * originalSpFactor, 10f * originalSpFactor)
                    return if(newScale != spFactor){
                        localStorage["spMultiplier"] = "${newScale / originalSpFactor}"
                        println("new scale: $spFactor")
                        spFactor = newScale
                        Runner.invalidateScale()
                        true
                    } else false
                }
                override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean = false
                override fun onScaleEnd(detector: ScaleGestureDetector?) {}
            })
            setOnTouchListener { _, event -> scaleDetector.onTouchEvent(event) }
        }

    }

    init {
        if (!sCompatibilityDone && context != null) {
            val targetSdkVersion = Build.VERSION.SDK_INT

            // Older apps only remeasure non-zero children
            sRemeasureWeightedChildren = targetSdkVersion >= Build.VERSION_CODES.P

            sCompatibilityDone = true
        }
    }

    
    fun shouldDelayChildPressedState(): Boolean {
        return false
    }

    
    override fun onDraw(canvas: Canvas?) {
        if (dividerDrawable == null) {
            super.onDraw(canvas)
            return
        }

        if (orientation == VERTICAL) {
            drawDividersVertical(canvas)
        } else {
            drawDividersHorizontal(canvas)
        }

        super.onDraw(canvas)
    }

    fun drawDividersVertical(canvas: Canvas?) {
        val count = virtualChildCount
        for (i in 0 until count) {
            val child = getVirtualChildAt(i)
            if (child != null && child.getVisibility() != View.GONE) {
                if (hasDividerBeforeChildAt(i)) {
                    val lp = child.getLayoutParams() as LayoutParams
                    val top = child.getTop() - lp.topMargin - mDividerHeight
                    drawHorizontalDivider(canvas, top)
                }
            }
        }

        if (hasDividerBeforeChildAt(count)) {
            val child = lastNonGoneChild
            var bottom = 0
            if (child == null) {
                bottom = getHeight() - getPaddingBottom() - mDividerHeight
            } else {
                val lp = child.getLayoutParams() as LayoutParams
                bottom = child.getBottom() + lp.bottomMargin
            }
            drawHorizontalDivider(canvas, bottom)
        }
    }

    fun drawDividersHorizontal(canvas: Canvas?) {
        val count = virtualChildCount
        val isLayoutRtl = isLayoutRtl()
        for (i in 0 until count) {
            val child = getVirtualChildAt(i)
            if (child != null && child.getVisibility() != View.GONE) {
                if (hasDividerBeforeChildAt(i)) {
                    val lp = child.getLayoutParams() as LayoutParams
                    val position: Int
                    if (isLayoutRtl) {
                        position = child.getRight() + lp.rightMargin
                    } else {
                        position = child.getLeft() - lp.leftMargin - dividerWidth
                    }
                    drawVerticalDivider(canvas, position)
                }
            }
        }

        if (hasDividerBeforeChildAt(count)) {
            val child = lastNonGoneChild
            val position: Int
            if (child == null) {
                if (isLayoutRtl) {
                    position = getPaddingLeft()
                } else {
                    position = getWidth() - getPaddingRight() - dividerWidth
                }
            } else {
                val lp = child.getLayoutParams() as LayoutParams
                if (isLayoutRtl) {
                    position = child.getLeft() - lp.leftMargin - dividerWidth
                } else {
                    position = child.getRight() + lp.rightMargin
                }
            }
            drawVerticalDivider(canvas, position)
        }
    }

    fun drawHorizontalDivider(canvas: Canvas?, top: Int) {
        dividerDrawable!!.setBounds(getPaddingLeft() + mDividerPadding, top,
                getWidth() - getPaddingRight() - mDividerPadding, top + mDividerHeight)
        dividerDrawable!!.draw(canvas)
    }

    fun drawVerticalDivider(canvas: Canvas?, left: Int) {
        dividerDrawable!!.setBounds(left, getPaddingTop() + mDividerPadding,
                left + dividerWidth, getHeight() - getPaddingBottom() - mDividerPadding)
        dividerDrawable!!.draw(canvas)
    }

    /**
     *
     * Returns the view at the specified index. This method can be overriden
     * to take into account virtual children. Refer to
     * [android.widget.TableLayout] and [android.widget.TableRow]
     * for an example.
     *
     * @param index the child's index
     * @return the child at the specified index, may be `null`
     */
    fun getVirtualChildAt(index: Int): View? {
        return getChildAt(index)
    }

    
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        // println("ll.onMeasure ${if(orientation == VERTICAL) "v" else "h"}, ${MeasureSpec.toString(widthMeasureSpec)}, ${MeasureSpec.toString(heightMeasureSpec)} | ${this.toStringWithVisibleChildren()}")

        // println("measure, $orientation, $VERTICAL")
        if (orientation == VERTICAL) {
            measureVertical2(widthMeasureSpec, heightMeasureSpec)
        } else {
            measureHorizontal2(widthMeasureSpec, heightMeasureSpec)
            /*for(child in children){
                println("${child.visibility}: ${child.getId()} (${child::class.simpleName}) = [${child.mLeft}, ${child.mTop}, ${child.mRight}, ${child.mBottom}]")
            }*/
        }
    }

    fun measureVertical2(widthMeasureSpec: Int, heightMeasureSpec: Int){
        var weightSum = 0f
        var summedHeight = mPaddingTop + mPaddingBottom
        val contentHeight = MeasureSpec.getSize(heightMeasureSpec) - (mPaddingTop + mPaddingBottom)
        val contentWidth = MeasureSpec.getSize(widthMeasureSpec) - (mPaddingLeft + mPaddingRight)
        val contentWidthMS = MeasureSpec.makeMeasureSpec(contentWidth, MeasureSpec.getMode(widthMeasureSpec))
        val contentHeightMS = MeasureSpec.makeMeasureSpec(contentHeight, MeasureSpec.getMode(heightMeasureSpec))
        var maxChildWidth = 0
        val hasInfiniteSpace = MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.UNSPECIFIED
        for(child in children){
            if(child.visibility == View.GONE) continue
            val lp = child.layoutParams
            if(lp.weight > 0f && !hasInfiniteSpace){
                weightSum += lp.weight
                summedHeight += lp.topMargin + lp.bottomMargin
            } else {
                // adjust the width & height to the remaining space
                // val availableWidth = max(contentWidth - (lp.leftMargin + lp.rightMargin), 0)
                // val availableHeight = max(contentHeight - (lp.topMargin + lp.bottomMargin), 0)
                child.measure(
                        getChildMeasureSpec(contentWidthMS, lp.leftMargin + lp.rightMargin, lp.width),
                        getChildMeasureSpec(if(hasInfiniteSpace) contentHeightMS else MeasureSpec.makeMeasureSpec(contentHeight - summedHeight - (lp.topMargin + lp.bottomMargin), MeasureSpec.AT_MOST), lp.topMargin + lp.bottomMargin, lp.height))
                // child.measure(MeasureSpec.makeMeasureSpec(availableWidth, AT_MOST), MeasureSpec.makeMeasureSpec(availableHeight, AT_MOST))
                summedHeight += child.measuredHeight + lp.topMargin + lp.bottomMargin
                maxChildWidth = max(maxChildWidth, child.measuredWidth + lp.leftMargin + lp.rightMargin)
            }
        }
        val maxHeight = if(hasInfiniteSpace) summedHeight else MeasureSpec.getSize(heightMeasureSpec)
        val remainingSpace = max(0, maxHeight - summedHeight)
        // println("maxHeight: $maxHeight, summedHeight: $summedHeight, remaining: $remainingSpace, weights: $weightSum")
        if(remainingSpace > 0 && weightSum > 0f){
            val x0 = mPaddingLeft
            var y = mPaddingTop
            for(child in children){
                if(child.visibility == View.GONE) continue
                val lp = child.layoutParams
                if(lp.weight > 0f){
                    val height = (remainingSpace * lp.weight / weightSum).toInt() - (lp.topMargin + lp.bottomMargin)
                    val heightMS = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
                    child.measure(contentWidthMS, heightMS)
                    child.setMeasuredDimension(child.measuredWidth, height)
                }
                child.mLeft = x0 + lp.leftMargin
                child.mRight = child.mLeft + child.measuredWidth
                child.mTop = y + lp.topMargin
                child.mBottom = child.mTop + child.measuredHeight
                y = child.mBottom + lp.bottomMargin
                maxChildWidth = max(maxChildWidth, child.measuredWidth + lp.leftMargin + lp.rightMargin)
            }

            val maxWidth = min(MeasureSpec.getSize(widthMeasureSpec), maxChildWidth)
            setMeasuredDimension(maxWidth, maxHeight)

        } else {
            val x0 = mPaddingLeft
            var y = mPaddingTop
            for(child in children){
                if(child.visibility == View.GONE) continue
                val lp = child.layoutParams
                if(lp.weight > 0f && !hasInfiniteSpace){
                    child.setMeasuredDimension(0, 0)
                    child.mLeft = 0
                    child.mRight = 0
                    child.mTop = 0
                    child.mBottom = 0
                } else {
                    child.mLeft = x0 + lp.leftMargin
                    child.mRight = child.mLeft + child.measuredWidth
                    child.mTop = y + lp.topMargin
                    child.mBottom = child.mTop + child.measuredHeight
                    y = child.mBottom + lp.bottomMargin
                }
            }
            val width = when(layoutParams.width){
                LayoutParams.MATCH_PARENT -> MeasureSpec.getSize(widthMeasureSpec)
                LayoutParams.WRAP_CONTENT -> min(maxChildWidth, MeasureSpec.getSize(widthMeasureSpec))
                else -> max(0, layoutParams.width)
            }
            val height = when(layoutParams.height){
                LayoutParams.MATCH_PARENT -> maxHeight
                LayoutParams.WRAP_CONTENT -> summedHeight
                else -> max(0, layoutParams.height)
            }
            setMeasuredDimension(width, height)
        }

        for(child in children){
            if(child.visibility != View.GONE){
                val lp = child.layoutParams
                if(Gravity.isHorizontal(lp.gravity)){
                    // display: Rect, inoutObj: Rect
                    displayRect.set(mPaddingLeft + lp.leftMargin, child.mTop, measuredWidth - (mPaddingRight + lp.rightMargin), child.mBottom)
                    Gravity.apply(lp.gravity, child.width, child.height, displayRect, inoutRect)
                    child.layout(inoutRect)
                }
            }
        }

    }

    fun measureHorizontal2(widthMeasureSpec: Int, heightMeasureSpec: Int){

        var weightSum = 0f
        var summedWidth = mPaddingLeft + mPaddingRight
        val contentWidth = MeasureSpec.getSize(widthMeasureSpec) - (mPaddingLeft + mPaddingRight)
        val contentHeight = MeasureSpec.getSize(heightMeasureSpec) - (mPaddingTop + mPaddingRight)
        val hasInfiniteSpace = MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.UNSPECIFIED
        val contentWidthMS = MeasureSpec.makeMeasureSpec(contentWidth, MeasureSpec.getMode(widthMeasureSpec))
        val contentHeightMS = MeasureSpec.makeMeasureSpec(contentHeight, MeasureSpec.getMode(heightMeasureSpec))
        var maxChildHeight = 0
        for(child in children){
            if(child.visibility == View.GONE) continue
            val lp = child.layoutParams
            if(lp.weight > 0f && !hasInfiniteSpace){
                weightSum += lp.weight
                summedWidth += lp.topMargin + lp.bottomMargin
            } else {
                // adjust the width & height to the remaining space
                // val availableWidth = max(contentWidth - (lp.leftMargin + lp.rightMargin), 0)
                // val availableHeight = max(contentHeight - (lp.topMargin + lp.bottomMargin), 0)
                val wms = getChildMeasureSpec(
                        if(hasInfiniteSpace) contentWidthMS
                        else MeasureSpec.makeMeasureSpec(
                                contentWidth - summedWidth - (lp.leftMargin + lp.rightMargin), MeasureSpec.AT_MOST),
                        lp.leftMargin + lp.rightMargin, lp.width)
                val hms = getChildMeasureSpec(contentHeightMS, lp.topMargin + lp.bottomMargin, lp.height)
                child.measure(
                        wms,
                        hms)
                // child.measure(MeasureSpec.makeMeasureSpec(availableWidth, AT_MOST), MeasureSpec.makeMeasureSpec(availableHeight, AT_MOST))
                summedWidth += child.measuredWidth + lp.leftMargin + lp.rightMargin
                maxChildHeight = max(maxChildHeight, child.measuredHeight + lp.topMargin + lp.bottomMargin)
            }
        }

        val maxWidth = if(hasInfiniteSpace) summedWidth else MeasureSpec.getSize(widthMeasureSpec)
        val remainingSpace = max(0, maxWidth - summedWidth)

        // println("$maxWidth ($contentWidth $contentHeight), $children")
        if(remainingSpace > 0 && weightSum > 0f){
            var x = mPaddingLeft
            val y0 = mPaddingTop
            for(child in children){
                if(child.visibility == View.GONE) continue
                val lp = child.layoutParams
                if(lp.weight > 0f){
                    val width = (remainingSpace * lp.weight / weightSum).toInt() - (lp.leftMargin + lp.rightMargin)
                    val widthMS = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY)
                    child.measure(widthMS, contentHeightMS)
                    child.setMeasuredDimension(width, child.measuredHeight)
                }
                child.mLeft = x + lp.leftMargin
                child.mRight = child.mLeft + child.measuredWidth
                child.mTop = y0 + lp.topMargin
                child.mBottom = child.mTop + child.measuredHeight
                x = child.mRight + lp.rightMargin
                maxChildHeight = max(maxChildHeight, child.measuredHeight + lp.topMargin + lp.bottomMargin)
            }

            val maxHeight = min(MeasureSpec.getSize(heightMeasureSpec), maxChildHeight)
            setMeasuredDimension(maxWidth, maxHeight)

        } else {
            var x = mPaddingLeft
            val y0 = mPaddingTop
            for(child in children){
                if(child.visibility == View.GONE) continue
                val lp = child.layoutParams
                if(lp.weight > 0f && !hasInfiniteSpace){
                    child.setMeasuredDimension(0, 0)
                    child.mLeft = 0
                    child.mRight = 0
                    child.mTop = 0
                    child.mBottom = 0
                } else {
                    child.mLeft = x + lp.leftMargin
                    child.mRight = child.mLeft + child.measuredWidth
                    child.mTop = y0 + lp.topMargin
                    child.mBottom = child.mTop + child.measuredHeight
                    x = child.mRight + lp.rightMargin
                }
            }
            val width = when(layoutParams.width){
                LayoutParams.MATCH_PARENT -> maxWidth
                LayoutParams.WRAP_CONTENT -> summedWidth
                else -> max(0, layoutParams.width)
            }
            val height = when(layoutParams.height){
                LayoutParams.MATCH_PARENT -> MeasureSpec.getSize(heightMeasureSpec)
                LayoutParams.WRAP_CONTENT -> min(maxChildHeight, MeasureSpec.getSize(heightMeasureSpec))
                else -> max(0, layoutParams.height)
            }
            setMeasuredDimension(width, height)
        }

        for(child in children){
            if(child.visibility != View.GONE){
                val lp = child.layoutParams
                if(Gravity.isVertical(lp.gravity)){
                    // display: Rect, inoutObj: Rect
                    displayRect.set(child.mLeft, mPaddingTop + lp.topMargin, child.mRight, measuredHeight - (mPaddingBottom + lp.bottomMargin))
                    Gravity.apply(lp.gravity, child.width, child.height, displayRect, inoutRect)
                    child.layout(inoutRect)
                }
            }
        }

    }

    /**
     * Determines where to position dividers between children.
     *
     * @param childIndex Index of child to check for preceding divider
     * @return true if there should be a divider before the child at childIndex
     * @hide Pending API consideration. Currently only used internally by the system.
     */
    fun hasDividerBeforeChildAt(childIndex: Int): Boolean {
        if (childIndex == virtualChildCount) {
            // Check whether the end divider should draw.
            return mShowDividers and SHOW_DIVIDER_END != 0
        }
        val allViewsAreGoneBefore = allViewsAreGoneBefore(childIndex)
        return if (allViewsAreGoneBefore) {
            // This is the first view that's not gone, check if beginning divider is enabled.
            mShowDividers and SHOW_DIVIDER_BEGINNING != 0
        } else {
            mShowDividers and SHOW_DIVIDER_MIDDLE != 0
        }
    }

    /**
     * Checks whether all (virtual) child views before the given index are gone.
     */
    private fun allViewsAreGoneBefore(childIndex: Int): Boolean {
        for (i in childIndex - 1 downTo 0) {
            val child = getVirtualChildAt(i)
            if (child != null && child.getVisibility() != View.GONE) {
                return false
            }
        }
        return true
    }

    /**
     * Measures the children when the orientation of this LinearLayout is set
     * to [.VERTICAL].
     *
     * @param widthMeasureSpec Horizontal space requirements as imposed by the parent.
     * @param heightMeasureSpec Vertical space requirements as imposed by the parent.
     *
     * @see .getOrientation
     * @see .setOrientation
     * @see .onMeasure
     */
    fun measureVertical(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        mTotalLength = 0
        var maxWidth = 0
        var childState = 0
        var alternativeMaxWidth = 0
        var weightedMaxWidth = 0
        var allFillParent = true
        var totalWeight = 0f

        val count = virtualChildCount

        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)

        var matchWidth = false
        var skippedMeasure = false

        val baselineChildIndex = mBaselineAlignedChildIndex
        val useLargestChild = isMeasureWithLargestChildEnabled

        var largestChildHeight = Int.MIN_VALUE
        var consumedExcessSpace = 0

        var nonSkippedChildCount = 0

        // See how tall everyone is. Also remember max width.
        run {
            var i = 0
            while (i < count) {
                val child = getVirtualChildAt(i)
                if (child == null) {
                    mTotalLength += measureNullChild(i)
                    ++i
                    continue
                }

                if (child.getVisibility() == View.GONE) {
                    i += getChildrenSkipCount(child, i)
                    ++i
                    continue
                }

                nonSkippedChildCount++
                if (hasDividerBeforeChildAt(i)) {
                    mTotalLength += mDividerHeight
                }

                val lp = child.getLayoutParams() as LayoutParams

                totalWeight += lp.weight

                val useExcessSpace = lp.height == 0 && lp.weight > 0
                if (heightMode == View.MeasureSpec.EXACTLY && useExcessSpace) {
                    // Optimization: don't bother measuring children who are only
                    // laid out using excess space. These views will get measured
                    // later if we have space to distribute.
                    val totalLength = mTotalLength
                    mTotalLength = max(totalLength, totalLength + lp.topMargin + lp.bottomMargin)
                    skippedMeasure = true
                } else {
                    if (useExcessSpace) {
                        // The heightMode is either UNSPECIFIED or AT_MOST, and
                        // this child is only laid out using excess space. Measure
                        // using WRAP_CONTENT so that we can find out the view's
                        // optimal height. We'll restore the original height of 0
                        // after measurement.
                        lp.height = LayoutParams.WRAP_CONTENT
                    }

                    // Determine how big this child would like to be. If this or
                    // previous children have given a weight, then we allow it to
                    // use all available space (and we will shrink things later
                    // if needed).
                    val usedHeight = if (totalWeight == 0f) mTotalLength else 0
                    measureChildBeforeLayout(child, i, widthMeasureSpec, 0,
                            heightMeasureSpec, usedHeight)

                    val childHeight = child.measuredHeight
                    if (useExcessSpace) {
                        // Restore the original height and record how much space
                        // we've allocated to excess-only children so that we can
                        // match the behavior of EXACTLY measurement.
                        lp.height = 0
                        consumedExcessSpace += childHeight
                    }

                    val totalLength = mTotalLength
                    mTotalLength = max(totalLength, totalLength + childHeight + lp.topMargin +
                            lp.bottomMargin + getNextLocationOffset(child))

                    if (useLargestChild) {
                        largestChildHeight = max(childHeight, largestChildHeight)
                    }
                }

                /**
                 * If applicable, compute the additional offset to the child's baseline
                 * we'll need later when asked [.getBaseline].
                 */
                if (baselineChildIndex >= 0 && baselineChildIndex == i + 1) {
                    mBaselineChildTop = mTotalLength
                }

                // if we are trying to use a child index for our baseline, the above
                // book keeping only works if there are no children above it with
                // weight.  fail fast to aid the developer.
                if (i < baselineChildIndex && lp.weight > 0) {
                    throw RuntimeException("A child of LinearLayout with index "
                            + "less than mBaselineAlignedChildIndex has weight > 0, which "
                            + "won't work.  Either remove the weight, or don't set "
                            + "mBaselineAlignedChildIndex.")
                }

                var matchWidthLocally = false
                if (widthMode != View.MeasureSpec.EXACTLY && lp.width == LayoutParams.MATCH_PARENT) {
                    // The width of the linear layout will scale, and at least one
                    // child said it wanted to match our width. Set a flag
                    // indicating that we need to remeasure at least that view when
                    // we know our width.
                    matchWidth = true
                    matchWidthLocally = true
                }

                val margin = lp.leftMargin + lp.rightMargin
                val measuredWidth = child.measuredWidth + margin
                maxWidth = max(maxWidth, measuredWidth)
                childState = combineMeasuredStates(childState, child.getMeasuredState())

                allFillParent = allFillParent && lp.width == LayoutParams.MATCH_PARENT
                if (lp.weight > 0) {
                    /*
                 * Widths of weighted Views are bogus if we end up
                 * remeasuring, so keep them separate.
                 */
                    weightedMaxWidth = max(weightedMaxWidth,
                            if (matchWidthLocally) margin else measuredWidth)
                } else {
                    alternativeMaxWidth = max(alternativeMaxWidth,
                            if (matchWidthLocally) margin else measuredWidth)
                }

                i += getChildrenSkipCount(child, i)
                ++i
            }
        }

        if (nonSkippedChildCount > 0 && hasDividerBeforeChildAt(count)) {
            mTotalLength += mDividerHeight
        }

        if (useLargestChild && (heightMode == View.MeasureSpec.AT_MOST || heightMode == View.MeasureSpec.UNSPECIFIED)) {
            mTotalLength = 0

            var i = 0
            while (i < count) {
                val child = getVirtualChildAt(i)
                if (child == null) {
                    mTotalLength += measureNullChild(i)
                    ++i
                    continue
                }

                if (child.getVisibility() == View.GONE) {
                    i += getChildrenSkipCount(child, i)
                    ++i
                    continue
                }

                val lp = child.getLayoutParams()
                // Account for negative margins
                val totalLength = mTotalLength
                mTotalLength = max(totalLength, totalLength + largestChildHeight +
                        lp.topMargin + lp.bottomMargin + getNextLocationOffset(child))
                ++i
            }
        }

        // Add in our padding
        mTotalLength += mPaddingTop + mPaddingBottom

        var heightSize = mTotalLength

        // Check against our minimum height
        heightSize = max(heightSize, getSuggestedMinimumHeight())

        // Reconcile our calculated size with the heightMeasureSpec
        val heightSizeAndState = resolveSizeAndState(heightSize, heightMeasureSpec, 0)
        heightSize = heightSizeAndState and MEASURED_SIZE_MASK
        // Either expand children with weight to take up available space or
        // shrink them if they extend beyond our current bounds. If we skipped
        // measurement on any children, we need to measure them now.
        var remainingExcess = heightSize - mTotalLength + if (mAllowInconsistentMeasurement) 0 else consumedExcessSpace
        if (skippedMeasure || ((sRemeasureWeightedChildren || remainingExcess != 0) && totalWeight > 0.0f)) {
            var remainingWeightSum = if (mWeightSum > 0.0f) mWeightSum else totalWeight

            mTotalLength = 0

            for (i in 0 until count) {
                val child = getVirtualChildAt(i)
                if (child == null || child.getVisibility() == View.GONE) {
                    continue
                }

                val lp = child.getLayoutParams()
                val childWeight = lp.weight
                if (childWeight > 0) {
                    val share = (childWeight * remainingExcess / remainingWeightSum).toInt()
                    remainingExcess -= share
                    remainingWeightSum -= childWeight

                    val childHeight: Int
                    if (isMeasureWithLargestChildEnabled && heightMode != View.MeasureSpec.EXACTLY) {
                        childHeight = largestChildHeight
                    } else if (lp.height == 0 && (!mAllowInconsistentMeasurement || heightMode == View.MeasureSpec.EXACTLY)) {
                        // This child needs to be laid out from scratch using
                        // only its share of excess space.
                        childHeight = share
                    } else {
                        // This child had some intrinsic height to which we
                        // need to add its share of excess space.
                        childHeight = child.measuredHeight + share
                    }

                    val childHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                            max(0, childHeight), View.MeasureSpec.EXACTLY)
                    val childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec,
                            mPaddingLeft + mPaddingRight + lp.leftMargin + lp.rightMargin,
                            lp.width)
                    child.measure(childWidthMeasureSpec, childHeightMeasureSpec)

                    // Child may now not fit in vertical dimension.
                    childState = combineMeasuredStates(childState, child.getMeasuredState() and (MEASURED_STATE_MASK shr MEASURED_HEIGHT_STATE_SHIFT))
                }

                val margin = lp.leftMargin + lp.rightMargin
                val measuredWidth = child.measuredWidth + margin
                maxWidth = max(maxWidth, measuredWidth)

                val matchWidthLocally = widthMode != View.MeasureSpec.EXACTLY && lp.width == LayoutParams.MATCH_PARENT

                alternativeMaxWidth = max(alternativeMaxWidth,
                        if (matchWidthLocally) margin else measuredWidth)

                allFillParent = allFillParent && lp.width == LayoutParams.MATCH_PARENT

                val totalLength = mTotalLength
                mTotalLength = max(totalLength, totalLength + child.measuredHeight +
                        lp.topMargin + lp.bottomMargin + getNextLocationOffset(child))
            }

            // Add in our padding
            mTotalLength += mPaddingTop + mPaddingBottom

        } else {
            alternativeMaxWidth = max(alternativeMaxWidth, weightedMaxWidth)


            // We have no limit, so make all weighted views as tall as the largest child.
            // Children will have already been measured once.
            if (useLargestChild && heightMode != View.MeasureSpec.EXACTLY) {
                for (i in 0 until count) {
                    val child = getVirtualChildAt(i)
                    if (child == null || child.getVisibility() == View.GONE) {
                        continue
                    }

                    val lp = child.getLayoutParams()

                    val childExtra = lp.weight
                    if (childExtra > 0) {
                        child.measure(
                                View.MeasureSpec.makeMeasureSpec(child.measuredWidth,
                                        View.MeasureSpec.EXACTLY),
                                View.MeasureSpec.makeMeasureSpec(largestChildHeight,
                                        View.MeasureSpec.EXACTLY))
                    }
                }
            }
        }

        if (!allFillParent && widthMode != View.MeasureSpec.EXACTLY) {
            maxWidth = alternativeMaxWidth
        }

        maxWidth += mPaddingLeft + mPaddingRight

        // Check against our minimum width
        maxWidth = max(maxWidth, getSuggestedMinimumWidth())

        setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, childState), heightSizeAndState)

        if (matchWidth) {
            forceUniformWidth(count, heightMeasureSpec)
        }
    }

    private fun forceUniformWidth(count: Int, heightMeasureSpec: Int) {
        // Pretend that the linear layout has an exact size.
        val uniformMeasureSpec = View.MeasureSpec.makeMeasureSpec(measuredWidth,
                View.MeasureSpec.EXACTLY)
        for (i in 0 until count) {
            val child = getVirtualChildAt(i)
            if (child != null && child.getVisibility() != View.GONE) {
                val lp = child.getLayoutParams()

                if (lp.width == LayoutParams.MATCH_PARENT) {
                    // Temporarily force children to reuse their old measured height
                    // FIXME: this may not be right for something like wrapping text?
                    val oldHeight = lp.height
                    lp.height = child.measuredHeight

                    // Remeasue with new dimensions
                    measureChildWithMargins(child, uniformMeasureSpec, 0, heightMeasureSpec, 0)
                    lp.height = oldHeight
                }
            }
        }
    }

    /**
     * Measures the children when the orientation of this LinearLayout is set
     * to [.HORIZONTAL].
     *
     * @param widthMeasureSpec Horizontal space requirements as imposed by the parent.
     * @param heightMeasureSpec Vertical space requirements as imposed by the parent.
     *
     * @see .getOrientation
     * @see .setOrientation
     * @see .onMeasure
     */
    fun measureHorizontal(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        mTotalLength = 0
        var maxHeight = 0
        var childState = 0
        var alternativeMaxHeight = 0
        var weightedMaxHeight = 0
        var allFillParent = true
        var totalWeight = 0f

        val count = virtualChildCount

        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)

        var matchHeight = false
        var skippedMeasure = false

        if (mMaxAscent == null || mMaxDescent == null) {
            mMaxAscent = IntArray(VERTICAL_GRAVITY_COUNT)
            mMaxDescent = IntArray(VERTICAL_GRAVITY_COUNT)
        }

        val maxAscent = mMaxAscent!!
        val maxDescent = mMaxDescent!!

        maxAscent[3] = -1
        maxAscent[2] = maxAscent[3]
        maxAscent[1] = maxAscent[2]
        maxAscent[0] = maxAscent[1]
        maxDescent[3] = -1
        maxDescent[2] = maxDescent[3]
        maxDescent[1] = maxDescent[2]
        maxDescent[0] = maxDescent[1]

        val baselineAligned = isBaselineAligned
        val useLargestChild = isMeasureWithLargestChildEnabled

        val isExactly = widthMode == View.MeasureSpec.EXACTLY

        var largestChildWidth = Int.MIN_VALUE
        var usedExcessSpace = 0

        var nonSkippedChildCount = 0

        // See how wide everyone is. Also remember max height.
        run {
            var i = 0
            while (i < count) {
                val child = getVirtualChildAt(i)
                if (child == null) {
                    mTotalLength += measureNullChild(i)
                    ++i
                    continue
                }

                if (child.getVisibility() == View.GONE) {
                    i += getChildrenSkipCount(child, i)
                    ++i
                    continue
                }

                nonSkippedChildCount++
                if (hasDividerBeforeChildAt(i)) {
                    mTotalLength += dividerWidth
                }

                val lp = child.getLayoutParams() as LayoutParams

                totalWeight += lp.weight

                val useExcessSpace = lp.width == 0 && lp.weight > 0
                if (widthMode == View.MeasureSpec.EXACTLY && useExcessSpace) {
                    // Optimization: don't bother measuring children who are only
                    // laid out using excess space. These views will get measured
                    // later if we have space to distribute.
                    if (isExactly) {
                        mTotalLength += lp.leftMargin + lp.rightMargin
                    } else {
                        val totalLength = mTotalLength
                        mTotalLength = max(totalLength, totalLength +
                                lp.leftMargin + lp.rightMargin)
                    }

                    // Baseline alignment requires to measure widgets to obtain the
                    // baseline offset (in particular for TextViews). The following
                    // defeats the optimization mentioned above. Allow the child to
                    // use as much space as it wants because we can shrink things
                    // later (and re-measure).
                    if (baselineAligned) {
                        val freeWidthSpec = View.MeasureSpec.makeSafeMeasureSpec(
                                View.MeasureSpec.getSize(widthMeasureSpec), View.MeasureSpec.UNSPECIFIED)
                        val freeHeightSpec = View.MeasureSpec.makeSafeMeasureSpec(
                                View.MeasureSpec.getSize(heightMeasureSpec), View.MeasureSpec.UNSPECIFIED)
                        child.measure(freeWidthSpec, freeHeightSpec)
                    } else {
                        skippedMeasure = true
                    }
                } else {
                    if (useExcessSpace) {
                        // The widthMode is either UNSPECIFIED or AT_MOST, and
                        // this child is only laid out using excess space. Measure
                        // using WRAP_CONTENT so that we can find out the view's
                        // optimal width. We'll restore the original width of 0
                        // after measurement.
                        lp.width = LayoutParams.WRAP_CONTENT
                    }

                    // Determine how big this child would like to be. If this or
                    // previous children have given a weight, then we allow it to
                    // use all available space (and we will shrink things later
                    // if needed).
                    val usedWidth = if (totalWeight == 0f) mTotalLength else 0
                    measureChildBeforeLayout(child, i, widthMeasureSpec, usedWidth,
                            heightMeasureSpec, 0)

                    val childWidth = child.measuredWidth
                    if (useExcessSpace) {
                        // Restore the original width and record how much space
                        // we've allocated to excess-only children so that we can
                        // match the behavior of EXACTLY measurement.
                        lp.width = 0
                        usedExcessSpace += childWidth
                    }

                    if (isExactly) {
                        mTotalLength += (childWidth + lp.leftMargin + lp.rightMargin
                                + getNextLocationOffset(child))
                    } else {
                        val totalLength = mTotalLength
                        mTotalLength = max(totalLength, totalLength + childWidth + lp.leftMargin
                                + lp.rightMargin + getNextLocationOffset(child))
                    }

                    if (useLargestChild) {
                        largestChildWidth = max(childWidth, largestChildWidth)
                    }
                }

                var matchHeightLocally = false
                if (heightMode != View.MeasureSpec.EXACTLY && lp.height == LayoutParams.MATCH_PARENT) {
                    // The height of the linear layout will scale, and at least one
                    // child said it wanted to match our height. Set a flag indicating that
                    // we need to remeasure at least that view when we know our height.
                    matchHeight = true
                    matchHeightLocally = true
                }

                val margin = lp.topMargin + lp.bottomMargin
                val childHeight = child.measuredHeight + margin
                childState = combineMeasuredStates(childState, child.getMeasuredState())

                if (baselineAligned) {
                    val childBaseline = child.getBaseline()
                    if (childBaseline != -1) {
                        // Translates the child's vertical gravity into an index
                        // in the range 0..VERTICAL_GRAVITY_COUNT
                        val gravity = (if (lp.gravity < 0) mGravity else lp.gravity) and Gravity.VERTICAL_GRAVITY_MASK
                        val index = gravity shr Gravity.AXIS_Y_SHIFT and Gravity.AXIS_SPECIFIED.inv() shr 1

                        maxAscent[index] = max(maxAscent[index], childBaseline)
                        maxDescent[index] = max(maxDescent[index], childHeight - childBaseline)
                    }
                }

                maxHeight = max(maxHeight, childHeight)

                allFillParent = allFillParent && lp.height == LayoutParams.MATCH_PARENT
                if (lp.weight > 0) {
                    /*
                 * Heights of weighted Views are bogus if we end up
                 * remeasuring, so keep them separate.
                 */
                    weightedMaxHeight = max(weightedMaxHeight,
                            if (matchHeightLocally) margin else childHeight)
                } else {
                    alternativeMaxHeight = max(alternativeMaxHeight,
                            if (matchHeightLocally) margin else childHeight)
                }

                i += getChildrenSkipCount(child, i)
                ++i
            }
        }

        if (nonSkippedChildCount > 0 && hasDividerBeforeChildAt(count)) {
            mTotalLength += dividerWidth
        }

        // Check mMaxAscent[INDEX_TOP] first because it maps to Gravity.TOP,
        // the most common case
        if (maxAscent[INDEX_TOP] != -1 ||
                maxAscent[INDEX_CENTER_VERTICAL] != -1 ||
                maxAscent[INDEX_BOTTOM] != -1 ||
                maxAscent[INDEX_FILL] != -1) {
            val ascent = max(maxAscent[INDEX_FILL],
                    max(maxAscent[INDEX_CENTER_VERTICAL],
                            max(maxAscent[INDEX_TOP], maxAscent[INDEX_BOTTOM])))
            val descent = max(maxDescent[INDEX_FILL],
                    max(maxDescent[INDEX_CENTER_VERTICAL],
                            max(maxDescent[INDEX_TOP], maxDescent[INDEX_BOTTOM])))
            maxHeight = max(maxHeight, ascent + descent)
        }

        if (useLargestChild && (widthMode == View.MeasureSpec.AT_MOST || widthMode == View.MeasureSpec.UNSPECIFIED)) {
            mTotalLength = 0

            var i = 0
            while (i < count) {
                val child = getVirtualChildAt(i)
                if (child == null) {
                    mTotalLength += measureNullChild(i)
                    ++i
                    continue
                }

                if (child.getVisibility() == View.GONE) {
                    i += getChildrenSkipCount(child, i)
                    ++i
                    continue
                }

                val lp = child.getLayoutParams()
                if (isExactly) {
                    mTotalLength += largestChildWidth + lp.leftMargin + lp.rightMargin +
                            getNextLocationOffset(child)
                } else {
                    val totalLength = mTotalLength
                    mTotalLength = max(totalLength, totalLength + largestChildWidth +
                            lp.leftMargin + lp.rightMargin + getNextLocationOffset(child))
                }
                ++i
            }
        }

        // Add in our padding
        mTotalLength += mPaddingLeft + mPaddingRight

        var widthSize = mTotalLength

        // Check against our minimum width
        widthSize = max(widthSize, getSuggestedMinimumWidth())

        // Reconcile our calculated size with the widthMeasureSpec
        val widthSizeAndState = resolveSizeAndState(widthSize, widthMeasureSpec, 0)
        widthSize = widthSizeAndState and MEASURED_SIZE_MASK

        // Either expand children with weight to take up available space or
        // shrink them if they extend beyond our current bounds. If we skipped
        // measurement on any children, we need to measure them now.
        var remainingExcess = widthSize - mTotalLength + if (mAllowInconsistentMeasurement) 0 else usedExcessSpace
        if (skippedMeasure || (sRemeasureWeightedChildren || remainingExcess != 0) && totalWeight > 0.0f) {
            var remainingWeightSum = if (mWeightSum > 0.0f) mWeightSum else totalWeight

            maxAscent[3] = -1
            maxAscent[2] = maxAscent[3]
            maxAscent[1] = maxAscent[2]
            maxAscent[0] = maxAscent[1]
            maxDescent[3] = -1
            maxDescent[2] = maxDescent[3]
            maxDescent[1] = maxDescent[2]
            maxDescent[0] = maxDescent[1]
            maxHeight = -1

            mTotalLength = 0

            for (i in 0 until count) {
                val child = getVirtualChildAt(i)
                if (child == null || child.getVisibility() == View.GONE) {
                    continue
                }

                val lp = child.getLayoutParams() as LayoutParams
                val childWeight = lp.weight
                if (childWeight > 0) {
                    val share = (childWeight * remainingExcess / remainingWeightSum).toInt()
                    remainingExcess -= share
                    remainingWeightSum -= childWeight

                    val childWidth: Int
                    if (isMeasureWithLargestChildEnabled && widthMode != View.MeasureSpec.EXACTLY) {
                        childWidth = largestChildWidth
                    } else if (lp.width == 0 && (!mAllowInconsistentMeasurement || widthMode == View.MeasureSpec.EXACTLY)) {
                        // This child needs to be laid out from scratch using
                        // only its share of excess space.
                        childWidth = share
                    } else {
                        // This child had some intrinsic width to which we
                        // need to add its share of excess space.
                        childWidth = child.measuredWidth + share
                    }

                    val childWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                            max(0, childWidth), View.MeasureSpec.EXACTLY)
                    val childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec,
                            mPaddingTop + mPaddingBottom + lp.topMargin + lp.bottomMargin,
                            lp.height)
                    child.measure(childWidthMeasureSpec, childHeightMeasureSpec)

                    // Child may now not fit in horizontal dimension.
                    childState = combineMeasuredStates(childState,
                            child.getMeasuredState() and MEASURED_STATE_MASK)
                }

                if (isExactly) {
                    mTotalLength += child.measuredWidth + lp.leftMargin + lp.rightMargin +
                            getNextLocationOffset(child)
                } else {
                    val totalLength = mTotalLength
                    mTotalLength = max(totalLength, totalLength + child.measuredWidth +
                            lp.leftMargin + lp.rightMargin + getNextLocationOffset(child))
                }

                val matchHeightLocally = heightMode != View.MeasureSpec.EXACTLY && lp.height == LayoutParams.MATCH_PARENT

                val margin = lp.topMargin + lp.bottomMargin
                val childHeight = child.measuredHeight + margin
                maxHeight = max(maxHeight, childHeight)
                alternativeMaxHeight = max(alternativeMaxHeight,
                        if (matchHeightLocally) margin else childHeight)

                allFillParent = allFillParent && lp.height == LayoutParams.MATCH_PARENT

                if (baselineAligned) {
                    val childBaseline = child.getBaseline()
                    if (childBaseline != -1) {
                        // Translates the child's vertical gravity into an index in the range 0..2
                        val gravity = (if (lp.gravity < 0) mGravity else lp.gravity) and Gravity.VERTICAL_GRAVITY_MASK
                        val index = gravity shr Gravity.AXIS_Y_SHIFT and Gravity.AXIS_SPECIFIED.inv() shr 1

                        maxAscent[index] = max(maxAscent[index], childBaseline)
                        maxDescent[index] = max(maxDescent!![index],
                                childHeight - childBaseline)
                    }
                }
            }

            // Add in our padding
            mTotalLength += mPaddingLeft + mPaddingRight
            // TODO: Should we update widthSize with the new total length?

            // Check mMaxAscent[INDEX_TOP] first because it maps to Gravity.TOP,
            // the most common case
            if (maxAscent[INDEX_TOP] != -1 ||
                    maxAscent[INDEX_CENTER_VERTICAL] != -1 ||
                    maxAscent[INDEX_BOTTOM] != -1 ||
                    maxAscent[INDEX_FILL] != -1) {
                val ascent = max(maxAscent[INDEX_FILL],
                        max(maxAscent[INDEX_CENTER_VERTICAL],
                                max(maxAscent[INDEX_TOP], maxAscent[INDEX_BOTTOM])))
                val descent = max(maxDescent[INDEX_FILL],
                        max(maxDescent[INDEX_CENTER_VERTICAL],
                                max(maxDescent[INDEX_TOP], maxDescent[INDEX_BOTTOM])))
                maxHeight = max(maxHeight, ascent + descent)
            }
        } else {
            alternativeMaxHeight = max(alternativeMaxHeight, weightedMaxHeight)

            // We have no limit, so make all weighted views as wide as the largest child.
            // Children will have already been measured once.
            if (useLargestChild && widthMode != View.MeasureSpec.EXACTLY) {
                for (i in 0 until count) {
                    val child = getVirtualChildAt(i)
                    if (child == null || child.getVisibility() == View.GONE) {
                        continue
                    }

                    val lp = child.getLayoutParams()

                    val childExtra = lp.weight
                    if (childExtra > 0) {
                        child.measure(
                                View.MeasureSpec.makeMeasureSpec(largestChildWidth, View.MeasureSpec.EXACTLY),
                                View.MeasureSpec.makeMeasureSpec(child.measuredHeight,
                                        View.MeasureSpec.EXACTLY))
                    }
                }
            }
        }

        if (!allFillParent && heightMode != View.MeasureSpec.EXACTLY) {
            maxHeight = alternativeMaxHeight
        }

        maxHeight += mPaddingTop + mPaddingBottom

        // Check against our minimum height
        maxHeight = max(maxHeight, getSuggestedMinimumHeight())

        setMeasuredDimension(widthSizeAndState or (childState and MEASURED_STATE_MASK),
                resolveSizeAndState(maxHeight, heightMeasureSpec,
                        childState shl MEASURED_HEIGHT_STATE_SHIFT))

        if (matchHeight) {
            forceUniformHeight(count, widthMeasureSpec)
        }
    }

    private fun forceUniformHeight(count: Int, widthMeasureSpec: Int) {
        // Pretend that the linear layout has an exact size. This is the measured height of
        // ourselves. The measured height should be the max height of the children, changed
        // to accommodate the heightMeasureSpec from the parent
        val uniformMeasureSpec = View.MeasureSpec.makeMeasureSpec(measuredHeight,
                View.MeasureSpec.EXACTLY)
        for (i in 0 until count) {
            val child = getVirtualChildAt(i)
            if (child != null && child.getVisibility() != View.GONE) {
                val lp = child.getLayoutParams()

                if (lp.height == LayoutParams.MATCH_PARENT) {
                    // Temporarily force children to reuse their old measured width
                    // FIXME: this may not be right for something like wrapping text?
                    val oldWidth = lp.width
                    lp.width = child.measuredWidth

                    // Remeasure with new dimensions
                    measureChildWithMargins(child, widthMeasureSpec, 0, uniformMeasureSpec, 0)
                    lp.width = oldWidth
                }
            }
        }
    }

    /**
     *
     * Returns the number of children to skip after measuring/laying out
     * the specified child.
     *
     * @param child the child after which we want to skip children
     * @param index the index of the child after which we want to skip children
     * @return the number of children to skip, 0 by default
     */
    fun getChildrenSkipCount(child: View, index: Int): Int {
        return 0
    }

    /**
     *
     * Returns the size (width or height) that should be occupied by a null
     * child.
     *
     * @param childIndex the index of the null child
     * @return the width or height of the child depending on the orientation
     */
    fun measureNullChild(childIndex: Int): Int {
        return 0
    }

    /**
     *
     * Measure the child according to the parent's measure specs. This
     * method should be overriden by subclasses to force the sizing of
     * children. This method is called by [.measureVertical] and
     * [.measureHorizontal].
     *
     * @param child the child to measure
     * @param childIndex the index of the child in this view
     * @param widthMeasureSpec horizontal space requirements as imposed by the parent
     * @param totalWidth extra space that has been used up by the parent horizontally
     * @param heightMeasureSpec vertical space requirements as imposed by the parent
     * @param totalHeight extra space that has been used up by the parent vertically
     */
    fun measureChildBeforeLayout(child: View, childIndex: Int,
                                          widthMeasureSpec: Int, totalWidth: Int, heightMeasureSpec: Int,
                                          totalHeight: Int) {
        measureChildWithMargins(child, widthMeasureSpec, totalWidth,
                heightMeasureSpec, totalHeight)
    }

    /**
     *
     * Return the location offset of the specified child. This can be used
     * by subclasses to change the location of a given widget.
     *
     * @param child the child for which to obtain the location offset
     * @return the location offset in pixels
     */
    fun getLocationOffset(child: View): Int {
        return 0
    }

    /**
     *
     * Return the size offset of the next sibling of the specified child.
     * This can be used by subclasses to change the location of the widget
     * following `child`.
     *
     * @param child the child whose next sibling will be moved
     * @return the location offset of the next child in pixels
     */
    fun getNextLocationOffset(child: View): Int {
        return 0
    }

    
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (orientation == VERTICAL) {
            layoutVertical(l, t, r, b)
        } else {
            layoutHorizontal(l, t, r, b)
        }
    }

    /**
     * Position the children during a layout pass if the orientation of this
     * LinearLayout is set to [.VERTICAL].
     *
     * @see .getOrientation
     * @see .setOrientation
     * @see .onLayout
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    fun layoutVertical(left: Int, top: Int, right: Int, bottom: Int) {
        val paddingLeft = mPaddingLeft

        var childTop: Int
        var childLeft: Int

        // Where right end of child should go
        val width = right - left
        val childRight = width - mPaddingRight

        // Space available for child
        val childSpace = width - paddingLeft - mPaddingRight

        val count = virtualChildCount

        val majorGravity = mGravity and Gravity.VERTICAL_GRAVITY_MASK
        val minorGravity = mGravity and Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK

        when (majorGravity) {
            Gravity.BOTTOM ->
                // mTotalLength contains the padding already
                childTop = mPaddingTop + bottom - top - mTotalLength

            // mTotalLength contains the padding already
            Gravity.CENTER_VERTICAL -> childTop = mPaddingTop + (bottom - top - mTotalLength) / 2

            Gravity.TOP -> childTop = mPaddingTop
            else -> childTop = mPaddingTop
        }

        var i = 0
        while (i < count) {
            val child = getVirtualChildAt(i)
            if (child == null) {
                childTop += measureNullChild(i)
            } else if (child.getVisibility() != View.GONE) {
                val childWidth = child.measuredWidth
                val childHeight = child.measuredHeight

                val lp = child.getLayoutParams()

                var gravity = lp.gravity
                if (gravity < 0) {
                    gravity = minorGravity
                }
                val layoutDirection = getLayoutDirection()
                val absoluteGravity = Gravity.getAbsoluteGravity(gravity, layoutDirection)
                when (absoluteGravity and Gravity.HORIZONTAL_GRAVITY_MASK) {
                    Gravity.CENTER_HORIZONTAL -> childLeft = (paddingLeft + (childSpace - childWidth) / 2
                            + lp.leftMargin) - lp.rightMargin

                    Gravity.RIGHT -> childLeft = childRight - childWidth - lp.rightMargin

                    Gravity.LEFT -> childLeft = paddingLeft + lp.leftMargin
                    else -> childLeft = paddingLeft + lp.leftMargin
                }

                if (hasDividerBeforeChildAt(i)) {
                    childTop += mDividerHeight
                }

                childTop += lp.topMargin
                setChildFrame(child, childLeft, childTop + getLocationOffset(child),
                        childWidth, childHeight)
                childTop += childHeight + lp.bottomMargin + getNextLocationOffset(child)

                i += getChildrenSkipCount(child, i)
            }
            i++
        }
    }

    /**
     * Position the children during a layout pass if the orientation of this
     * LinearLayout is set to [.HORIZONTAL].
     *
     * @see .getOrientation
     * @see .setOrientation
     * @see .onLayout
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    fun layoutHorizontal(left: Int, top: Int, right: Int, bottom: Int) {
        val isLayoutRtl = isLayoutRtl()
        val paddingTop = mPaddingTop

        var childTop: Int
        var childLeft: Int

        // Where bottom of child should go
        val height = bottom - top
        val childBottom = height - mPaddingBottom

        // Space available for child
        val childSpace = height - paddingTop - mPaddingBottom

        val count = virtualChildCount

        val majorGravity = mGravity and Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK
        val minorGravity = mGravity and Gravity.VERTICAL_GRAVITY_MASK

        val baselineAligned = isBaselineAligned

        val maxAscent = mMaxAscent
        val maxDescent = mMaxDescent

        val layoutDirection = getLayoutDirection()
        when (Gravity.getAbsoluteGravity(majorGravity, layoutDirection)) {
            Gravity.RIGHT ->
                // mTotalLength contains the padding already
                childLeft = mPaddingLeft + right - left - mTotalLength

            Gravity.CENTER_HORIZONTAL ->
                // mTotalLength contains the padding already
                childLeft = mPaddingLeft + (right - left - mTotalLength) / 2

            Gravity.LEFT -> childLeft = mPaddingLeft
            else -> childLeft = mPaddingLeft
        }

        var start = 0
        var dir = 1
        //In case of RTL, start drawing from the last child.
        if (isLayoutRtl) {
            start = count - 1
            dir = -1
        }

        var i = 0
        while (i < count) {
            val childIndex = start + dir * i
            val child = getVirtualChildAt(childIndex)
            if (child == null) {
                childLeft += measureNullChild(childIndex)
            } else if (child.getVisibility() != View.GONE) {
                val childWidth = child.measuredWidth
                val childHeight = child.measuredHeight
                var childBaseline = -1

                val lp = child.getLayoutParams()

                if (baselineAligned && lp.height != LayoutParams.MATCH_PARENT) {
                    childBaseline = child.getBaseline()
                }

                var gravity = lp.gravity
                if (gravity < 0) {
                    gravity = minorGravity
                }

                when (gravity and Gravity.VERTICAL_GRAVITY_MASK) {
                    Gravity.TOP -> {
                        childTop = paddingTop + lp.topMargin
                        if (childBaseline != -1) {
                            childTop += maxAscent!![INDEX_TOP] - childBaseline
                        }
                    }

                    Gravity.CENTER_VERTICAL ->
                        // Removed support for baseline alignment when layout_gravity or
                        // gravity == center_vertical. See bug #1038483.
                        // Keep the code around if we need to re-enable this feature
                        // if (childBaseline != -1) {
                        //     // Align baselines vertically only if the child is smaller than us
                        //     if (childSpace - childHeight > 0) {
                        //         childTop = paddingTop + (childSpace / 2) - childBaseline;
                        //     } else {
                        //         childTop = paddingTop + (childSpace - childHeight) / 2;
                        //     }
                        // } else {
                        childTop = (paddingTop + (childSpace - childHeight) / 2
                                + lp.topMargin) - lp.bottomMargin

                    Gravity.BOTTOM -> {
                        childTop = childBottom - childHeight - lp.bottomMargin
                        if (childBaseline != -1) {
                            val descent = child.measuredHeight - childBaseline
                            childTop -= maxDescent!![INDEX_BOTTOM] - descent
                        }
                    }
                    else -> childTop = paddingTop
                }

                if (hasDividerBeforeChildAt(childIndex)) {
                    childLeft += dividerWidth
                }

                childLeft += lp.leftMargin
                setChildFrame(child, childLeft + getLocationOffset(child), childTop,
                        childWidth, childHeight)
                childLeft += childWidth + lp.rightMargin +
                        getNextLocationOffset(child)

                i += getChildrenSkipCount(child, childIndex)
            }
            i++
        }
    }

    private fun setChildFrame(child: View, left: Int, top: Int, width: Int, height: Int) {
        child.layout(left, top, left + width, top + height)
    }

    
    fun setHorizontalGravity(horizontalGravity: Int) {
        val gravity = horizontalGravity and Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK
        if (mGravity and Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK != gravity) {
            mGravity = mGravity and Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK.inv() or gravity
            requestLayout()
        }
    }

    
    fun setVerticalGravity(verticalGravity: Int) {
        val gravity = verticalGravity and Gravity.VERTICAL_GRAVITY_MASK
        if (mGravity and Gravity.VERTICAL_GRAVITY_MASK != gravity) {
            mGravity = mGravity and Gravity.VERTICAL_GRAVITY_MASK.inv() or gravity
            requestLayout()
        }
    }

    
    fun generateLayoutParams(attrs: AttributeSet): View.LayoutParams {
        return View.LayoutParams(context, attrs)
    }

    /**
     * Returns a set of layout parameters with a width of
     * [android.view.View.LayoutParams.MATCH_PARENT]
     * and a height of [android.view.View.LayoutParams.WRAP_CONTENT]
     * when the layout's orientation is [.VERTICAL]. When the orientation is
     * [.HORIZONTAL], the width is set to [LayoutParams.WRAP_CONTENT]
     * and the height to [LayoutParams.WRAP_CONTENT].
     */
    
    fun generateDefaultLayoutParams(): View.LayoutParams? {
        if (mOrientation == HORIZONTAL) {
            return View.LayoutParams(View.LayoutParams.WRAP_CONTENT, View.LayoutParams.WRAP_CONTENT)
        } else if (orientation == VERTICAL) {
            return View.LayoutParams(View.LayoutParams.MATCH_PARENT, View.LayoutParams.WRAP_CONTENT)
        }
        return null
    }

    
    fun generateLayoutParams(lp: View.LayoutParams): View.LayoutParams = lp

    // Override to allow type-checking of LayoutParams.

    fun checkLayoutParams(p: View.LayoutParams): Boolean = true

    companion object {

        val HORIZONTAL = 0
        val VERTICAL = 1

        /**
         * Don't show any dividers.
         */
        const val SHOW_DIVIDER_NONE = 0
        /**
         * Show a divider at the beginning of the group.
         */
        const val SHOW_DIVIDER_BEGINNING = 1
        /**
         * Show dividers between each item in the group.
         */
        const val SHOW_DIVIDER_MIDDLE = 2
        /**
         * Show a divider at the end of the group.
         */
        const val SHOW_DIVIDER_END = 4

        const val VERTICAL_GRAVITY_COUNT = 4

        const val INDEX_CENTER_VERTICAL = 0
        const val INDEX_TOP = 1
        const val INDEX_BOTTOM = 2
        const val INDEX_FILL = 3

        /**
         * Signals that compatibility booleans have been initialized according to
         * target SDK versions.
         */
        private var sCompatibilityDone = false

        /**
         * Behavior change in P; always remeasure weighted children, regardless of excess space.
         */
        private var sRemeasureWeightedChildren = true
    }
}
