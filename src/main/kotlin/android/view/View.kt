package android.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.LongSparseLongArray
import me.antonio.noack.maths.MathsUtils.sq
import me.antonio.noack.webdroid.Runner
import me.antonio.noack.webdroid.setTimeout
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

external fun cloneView(function: JsClass<out View>, self: Context?, attributes: AttributeSet): View

// for every important property create a getDefault function, so we can customize buttons, image views, text views, etc

open class View(ctx: Context?, attributeSet: AttributeSet?){

    fun getGlobalPosition(): Pair<Int, Int>{
        val parent = mParent ?: return mLeft to mTop
        return if(mLeft == 0 && mTop == 0){
            parent.getGlobalPosition()
        } else {
            val (x, y) = parent.getGlobalPosition()
            (x+mLeft) to (y+mTop)
        }
    }

    fun destroy(){
        visibility = GONE
        onVisibilityChanged()
        if(this is ViewGroup){
            for(child in children){
                child.destroy()
            }
        }
    }

    val uuid = Companion.uuid++
    var hasFocus = false
    var alpha = 1f

    open fun onVisibilityChanged(){}

    fun runParentsThenSelf(runnable: (it: View) -> Unit){
        mParent?.runParentsThenSelf(runnable)
        runnable(this)
    }

    fun runOnUiThread(runnable: () -> Unit){
        setTimeout(runnable, 0)
    }

    override fun toString(): String {
        return "${getId()} (${this::class.simpleName}) " +
                "WH[${getWidth()}, ${getHeight()}] " +
                "MWH[${getMeasuredWidth()}, ${getMeasuredHeight()}] " +
                "Bounds[$mLeft, $mTop, $mRight, $mBottom] " +
                "Padding[$mPaddingLeft, $mPaddingTop, $mPaddingRight, $mPaddingBottom] " +
                "Margin[${layoutParams.leftMargin}, ${layoutParams.topMargin}, ${layoutParams.rightMargin}, ${layoutParams.bottomMargin}] " +
                "LayPar[$layoutParams]"
    }

    fun toStringWithVisibleChildren(depth: Int = 0): String = if(this is ViewGroup)
        "${toString()} [${
            children
                    .filter {
                        // it.isEffectivelyVisible()
                        it.visibility != View.GONE
                    }
                    .joinToString { "\n${tabs(depth)}${it.toStringWithVisibleChildren(depth+1)}" }
        }]" else toString()

    fun performClick(){
        processEvent(ClickEvent(null, false))
    }

    fun performLongClick(){
        processEvent(ClickEvent(null, true))
    }

    fun isEffectivelyVisible(): Boolean = visibility == View.VISIBLE && getWidth() > 0 && getHeight() > 0

    // clone the attributes from the style specified in the theme attribute
    fun cloneAttributesFromStyle(){
        val styleName = attributeSet.getString("theme", "")
        if(styleName.isEmpty()) return
        val style = R.allStyles.findElementById<View>(styleName) ?: return
        val attributes = style.attributeSet.values
        val myValues = attributeSet.values
        for((key, value) in attributes){
            if(!myValues.containsKey(key)){
                myValues[key] = value
            }
        }
    }

    open fun init(){
        cloneAttributesFromStyle()
        onInit()
    }

    /**
     * reads the values from the xml
     * */
    open fun onInit(){

        layoutParams = LayoutParams(context, attributeSet, this)

        // layout_gravity for the layout, gravity for the text
        // baseline aligned?

        val padding = attributeSet.getSize("padding", getDefaultPadding())
        mPaddingLeft = attributeSet.getSize("paddingLeft", padding)
        mPaddingTop = attributeSet.getSize("paddingTop", padding)
        mPaddingRight = attributeSet.getSize("paddingRight", padding)
        mPaddingBottom = attributeSet.getSize("paddingBottom", padding)

        mBackground = attributeSet.getDrawable("background", null)

        visibility = when(attributeSet.getString("visibility", "")){
            "invisible" -> View.INVISIBLE
            "gone" -> View.GONE
            else -> View.VISIBLE
        }

    }

    fun getId(): String = attributeSet.getString("id", uuid.toString())

    var mPrivateFlags = 0
    var mPrivateFlags3 = 0

    var mLayoutModeOptical = false
    fun isLayoutModeOptical(view: View?) = view?.mLayoutModeOptical ?: mLayoutModeOptical
    fun resolveRtlPropertiesIfNeeded(){ /* nah, not needed */}

    fun measure(parentWidthSpec: Int, parentHeightSpec: Int){

        var widthMeasureSpec = parentWidthSpec
        var heightMeasureSpec = parentHeightSpec

        // println("measure ${MeasureSpec.toString(widthMeasureSpec)} x ${MeasureSpec.toString(heightMeasureSpec)}")

        val optical = isLayoutModeOptical(this)
        if (optical != isLayoutModeOptical(mParent)) {
            val insets = getOpticalInsets()
            val oWidth = insets.left + insets.right
            val oHeight = insets.top + insets.bottom
            widthMeasureSpec = MeasureSpec.adjust(widthMeasureSpec, if (optical) -oWidth else oWidth)
            heightMeasureSpec = MeasureSpec.adjust(heightMeasureSpec, if (optical) -oHeight else oHeight)
        }

        // Suppress sign extension for the low bytes
        val key = widthMeasureSpec.toLong() shl 32 or (heightMeasureSpec.toLong() and 0xffffffffL)
        if (mMeasureCache == null) mMeasureCache = LongSparseLongArray(2)

        // println("cache: $mMeasureCache")

        val forceLayout = true//  || mPrivateFlags and PFLAG_FORCE_LAYOUT == PFLAG_FORCE_LAYOUT

        // Optimize layout by avoiding an extra EXACTLY pass when the view is
        // already measured as the correct size. In API 23 and below, this
        // extra pass is required to make LinearLayout re-distribute weight.
        val specChanged = widthMeasureSpec != mOldWidthMeasureSpec || heightMeasureSpec != mOldHeightMeasureSpec
        val isSpecExactly = MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY && MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY
        val matchesSpecSize = getMeasuredWidth() == MeasureSpec.getSize(widthMeasureSpec) && getMeasuredHeight() == MeasureSpec.getSize(heightMeasureSpec)
        val needsLayout = specChanged && (sAlwaysRemeasureExactly || !isSpecExactly || !matchesSpecSize)

        if (forceLayout || needsLayout) {
            // first clears the measured dimension flag
            mPrivateFlags = mPrivateFlags and PFLAG_MEASURED_DIMENSION_SET.inv()

            resolveRtlPropertiesIfNeeded()

            val cacheIndex = if (forceLayout) -1 else if(mMeasureCache!!.containsKey(key)) key else -1
            if (cacheIndex < 0 || sIgnoreMeasureCache) {
                // measure ourselves, this should set the measured dimension flag back
                onMeasure(widthMeasureSpec, heightMeasureSpec)
                mPrivateFlags3 = mPrivateFlags3 and PFLAG3_MEASURE_NEEDED_BEFORE_LAYOUT.inv()
            } else {
                val value = mMeasureCache!![cacheIndex]!!
                // Casting a long to int drops the high 32 bits, no mask needed
                setMeasuredDimensionRaw((value shr 32).toInt(), value.toInt())
                mPrivateFlags3 = mPrivateFlags3 or PFLAG3_MEASURE_NEEDED_BEFORE_LAYOUT
            }

            // flag not set, setMeasuredDimension() was not invoked, we raise
            // an exception to warn the developer
            if (mPrivateFlags and PFLAG_MEASURED_DIMENSION_SET != PFLAG_MEASURED_DIMENSION_SET) {
                throw IllegalStateException("View with id " + getId() + ": ${this::class.simpleName}"
                         + "#onMeasure() did not set the"
                        + " measured dimension by calling"
                        + " setMeasuredDimension()")
            }

            mPrivateFlags = mPrivateFlags or PFLAG_LAYOUT_REQUIRED
        }

        mOldWidthMeasureSpec = widthMeasureSpec
        mOldHeightMeasureSpec = heightMeasureSpec

        mMeasureCache!![key] = mMeasuredWidth.toLong() shl 32 or (mMeasuredHeight.toLong() and 0xffffffffL) // suppress sign extension
    }

    fun getOpticalInsets() = Rect.NONE

    /**
     *
     * This method must be called by [.onMeasure] to store the
     * measured width and measured height. Failing to do so will trigger an
     * exception at measurement time.
     *
     * @param theMeasuredWidth The measured width of this view.  May be a complex
     * bit mask as defined by [.MEASURED_SIZE_MASK] and
     * [.MEASURED_STATE_TOO_SMALL].
     * @param theMeasuredHeight The measured height of this view.  May be a complex
     * bit mask as defined by [.MEASURED_SIZE_MASK] and
     * [.MEASURED_STATE_TOO_SMALL].
     */
    fun setMeasuredDimension(theMeasuredWidth: Int, theMeasuredHeight: Int) {
        var measuredWidth = theMeasuredWidth
        var measuredHeight = theMeasuredHeight
        val optical = isLayoutModeOptical(this)
        if (optical != isLayoutModeOptical(mParent)) {
            val insets = getOpticalInsets()
            val opticalWidth = insets.left + insets.right
            val opticalHeight = insets.top + insets.bottom

            measuredWidth += if (optical) opticalWidth else -opticalWidth
            measuredHeight += if (optical) opticalHeight else -opticalHeight
        }
        setMeasuredDimensionRaw(measuredWidth, measuredHeight)
        // println("${getId()} ${this::class.simpleName} = $theMeasuredWidth x $theMeasuredHeight")
    }

    /**
     * Sets the measured dimension without extra processing for things like optical bounds.
     * Useful for reapplying consistent values that have already been cooked with adjustments
     * for optical bounds, etc. such as those from the measurement cache.
     *
     * @param measuredWidth The measured width of this view.  May be a complex
     * bit mask as defined by [.MEASURED_SIZE_MASK] and
     * [.MEASURED_STATE_TOO_SMALL].
     * @param measuredHeight The measured height of this view.  May be a complex
     * bit mask as defined by [.MEASURED_SIZE_MASK] and
     * [.MEASURED_STATE_TOO_SMALL].
     */
    private fun setMeasuredDimensionRaw(measuredWidth: Int, measuredHeight: Int) {
        mMeasuredWidth = measuredWidth
        mMeasuredHeight = measuredHeight

        mPrivateFlags = mPrivateFlags or PFLAG_MEASURED_DIMENSION_SET
    }

    var mLeft = 0
    var mTop = 0
    var mRight = 0
    var mBottom = 0

    fun layout(rect: Rect){
        mLeft = rect.left
        mTop = rect.top
        mRight = rect.right
        mBottom = rect.bottom
    }

    fun layout(l: Int, t: Int, r: Int, b: Int){
        mLeft = l
        mTop = t
        mRight = r
        mBottom = b
    }

    fun getLayoutDirection() = 0
    fun isLayoutRtl() = false

    fun getLeft() = mLeft
    fun getTop() = mTop
    fun getRight() = mRight
    fun getBottom() = mBottom
    fun getWidth() = mRight - mLeft
    fun getHeight() = mBottom - mTop

    // irrelevant for drawing
    val x = 0
    val y = 0

    val width: Int
        get() = mRight - mLeft

    val height: Int
        get() = mBottom - mTop

    var mBaseline = 0
    fun getBaseline() = mBaseline

    fun requestLayout(){
        invalidate()
    }

    open fun clone(): View {
        val ktClass = this::class
        val jsClass = ktClass.js
        val cloned = cloneView(jsClass, context, attributeSet)
        if(this is ViewGroup){
            cloned as ViewGroup
            for(child in children){
                cloned.addChild(child.clone())
            }
        }
        cloned.init()
        return cloned
    }

    val parent: View?
        get() = mParent

    open fun <V: View> findViewById(id: String): V? = findElementById(id)
    open fun <V: View> findElementById(id: String): V? {
        if(attributeSet.values["id"] == id) return this as V
        return null
    }

    fun attr(string: String) = attributeSet.values[string]

    fun first(predicate: (View) -> Boolean): View = (this as ViewGroup).children.first(predicate)

    var layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)

    var mParent: ViewGroup? = null

    val attributeSet = attributeSet ?: AttributeSet()

    var systemUiVisibility = 0

    val context = ctx

    var mBackground: Drawable? = null

    var mMinWidth = 0
    var minimumWidth
        get() = mMinWidth
        set(value){
            mMinWidth = value
        }

    var mMinHeight = 0
    var minimumHeight
        get() = mMinHeight
        set(value){
            mMinHeight = value
        }

    var mOldWidthMeasureSpec = 0
    var mOldHeightMeasureSpec = 0
    var mMeasuredWidth = 0
    var mMeasuredHeight = 0

    var mPaddingLeft = 0
    var mPaddingTop = 0
    var mPaddingRight = 0
    var mPaddingBottom = 0

    val paddingLeft get() = mPaddingLeft
    val paddingTop get() = mPaddingTop
    val paddingRight get() = mPaddingRight
    val paddingBottom get() = mPaddingBottom

    fun getPaddingLeft() = mPaddingLeft
    fun getPaddingTop() = mPaddingTop
    fun getPaddingRight() = mPaddingRight
    fun getPaddingBottom() = mPaddingBottom

    private var mMeasureCache: LongSparseLongArray? = null

    var mVisibility = View.VISIBLE
    var visibility
        get() = mVisibility
        set(value) {
            if(value != mVisibility){
                mVisibility = value
                onVisibilityChanged()
                invalidate()
            }
        }
    fun getVisibility() = visibility
    fun setVisibility(value: Int){
        visibility = value
    }

    fun getLayoutParams(): LayoutParams = layoutParams
    fun getMeasuredHeight() = mMeasuredHeight
    fun getMeasuredWidth() = mMeasuredWidth

    val measuredWidth: Int
        get() = mMeasuredWidth
    val measuredHeight: Int
        get() = mMeasuredHeight

    /**
     * Returns the suggested minimum height that the view should use. This
     * returns the maximum of the view's minimum height
     * and the background's minimum height
     * ([android.graphics.drawable.Drawable.getMinimumHeight]).
     *
     *
     * When being used in [.onMeasure], the caller should still
     * ensure the returned height is within the requirements of the parent.
     *
     * @return The suggested minimum height of the view.
     */
    open fun getSuggestedMinimumWidth(): Int =
            if(layoutParams.width > -1) layoutParams.width else if (mBackground == null) mMinWidth else max(mMinWidth, mBackground!!.getMinimumWidth())
    open fun getSuggestedMinimumHeight(): Int =
            if(layoutParams.height > -1) layoutParams.height else if (mBackground == null) mMinHeight else max(mMinHeight, mBackground!!.getMinimumHeight())


    fun addChild(view: View): View {
        if(this is ViewGroup){
            this.children.add(view)
            view.mParent = this
        } else println("$this is no ViewGroup")
        return this
    }

    fun attr(key: String, value: String): View {
        attributeSet.values[key] = value
        return this
    }

    fun attr(key: String, value: Int): View {
        attributeSet.values[key] = value.toString()
        return this
    }

    var isInvalid = -1
    open fun invalidate(){
        if(isInvalid < Runner.invalidTime){
            isInvalid = Runner.invalidTime
            mParent?.invalidate()
        }
    }

    var clickListener: ((view: View) -> Unit)? = null
    var longClickListener: ((view: View) -> Boolean)? = null
    var keyListener: ((view: View, event: KeyEvent) -> Boolean)? = null

    var lastX = 0f
    var lastY = 0f
    var distance = 0f
    var disabled = true
    var downTime = 0L
    var touchListener: ((view: View, event: MotionEvent) -> Boolean)? = { _, e ->
        if(clickListener == null && longClickListener == null) false else when(e.actionMasked){
            MotionEvent.ACTION_DOWN -> {
                disabled = false
                distance = 0f
                downTime = System.nanoTime()
                lastX = e.originalX
                lastY = e.originalY
                true
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                disabled = true
                true
            }
            MotionEvent.ACTION_UP -> {
                // println("click onto ${getId()} ${e.x}, ${e.y} in $measuredWidth x $measuredHeight, $distance, $disabled")
                if(!disabled){
                    val clickDuration = (System.nanoTime() - downTime) * 1e-9
                    if(clickDuration < 0.3f){
                        performClick()
                    } else {
                        performLongClick()
                    }
                }
                disabled
            }
            MotionEvent.ACTION_MOVE -> {
                if(!disabled){
                    distance += sqrt(sq(lastX - e.originalX, lastY - e.originalY))
                    lastX = e.originalX
                    lastY = e.originalY
                    disabled = distance > 30f
                }
                disabled
            }
            else -> false
        }
    }

    fun setOnClickListener(listener: (view: View) -> Unit): View {
        // println("set click listener on ${getId()}, $touchListener")
        // if(touchListener == null) setDefaultTouchListener()
        clickListener = listener
        return this
    }

    fun setOnLongClickListener(listener: (view: View) -> Boolean): View {
        // if(touchListener == null) setDefaultTouchListener()
        longClickListener = listener
        return this
    }

    var isButtonDown = false

    private fun sq(x: Float) = x*x

    fun setOnTouchListener(listener: (view: View, event: MotionEvent) -> Boolean): View {
        touchListener = listener
        return this
    }

    fun setOnKeyListener(listener: (view: View, event: KeyEvent) -> Boolean): View {
        keyListener = listener
        return this
    }

    fun postInvalidate(){
        invalidate()
    }

    open fun onDraw(canvas: Canvas){
        drawBackground(canvas)
    }

    open fun drawBackground(canvas: Canvas){
        // draw the background
        // inset the padding
        val mw = getMeasuredWidth()
        val mh = getMeasuredHeight()
        mBackground?.setBounds(0, 0, mw, mh)
        mBackground?.draw(canvas)
        // println("draw $mLeft, $mTop, $mRight, $mBottom")
        // canvas.lineRect(mLeft, mTop, mRight, mBottom, -1)
        // canvas.translate(mPaddingLeft, mPaddingTop)
        // canvas.setBounds(0, 0, mw - (mPaddingLeft + mPaddingRight), mh - (mPaddingTop + mPaddingBottom))
    }

    open fun draw(canvas: Canvas){
        onDraw(canvas)
    }

    open fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val lp = layoutParams
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec, lp.width),
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec, lp.height))
    }

    /**
     * Utility to return a default size. Uses the supplied size if the
     * MeasureSpec imposed no constraints. Will get larger if allowed
     * by the MeasureSpec.
     *
     * @param size Default size for this view
     * @param measureSpec Constraints imposed by the parent
     * @return The size this view should be.
     */
    fun getDefaultSize(size: Int, measureSpec: Int, lp: Int): Int {
        var result = size
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        when (specMode) {
            MeasureSpec.UNSPECIFIED -> result = size
            MeasureSpec.AT_MOST -> result = when(lp){
                LayoutParams.MATCH_PARENT -> specSize
                LayoutParams.WRAP_CONTENT -> min(specSize, size)
                else -> min(specSize, size)
            }
            MeasureSpec.EXACTLY -> result = specSize
        }
        return result
    }

    /**
     * Merge two states as returned by [.getMeasuredState].
     * @param curState The current state as returned from a view or the result
     * of combining multiple views.
     * @param newState The new view state to combine.
     * @return Returns a new integer reflecting the combination of the two
     * states.
     */
    fun combineMeasuredStates(curState: Int, newState: Int): Int {
        return curState or newState
    }

    /**
     * Version of [.resolveSizeAndState]
     * returning only the [.MEASURED_SIZE_MASK] bits of the result.
     */
    fun resolveSize(size: Int, measureSpec: Int): Int {
        return resolveSizeAndState(size, measureSpec, 0) and MEASURED_SIZE_MASK
    }

    /**
     * Utility to reconcile a desired size and state, with constraints imposed
     * by a MeasureSpec. Will take the desired size, unless a different size
     * is imposed by the constraints. The returned value is a compound integer,
     * with the resolved size in the [.MEASURED_SIZE_MASK] bits and
     * optionally the bit [.MEASURED_STATE_TOO_SMALL] set if the
     * resulting size is smaller than the size the view wants to be.
     *
     * @param size How big the view wants to be.
     * @param measureSpec Constraints imposed by the parent.
     * @param childMeasuredState Size information bit mask for the view's
     * children.
     * @return Size information bit mask as defined by
     * [.MEASURED_SIZE_MASK] and
     * [.MEASURED_STATE_TOO_SMALL].
     */
    fun resolveSizeAndState(size: Int, measureSpec: Int, childMeasuredState: Int): Int {
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        val result = when (specMode) {
            MeasureSpec.AT_MOST -> if (specSize < size) {
                specSize or MEASURED_STATE_TOO_SMALL
            } else {
                size
            }
            MeasureSpec.EXACTLY -> specSize
            MeasureSpec.UNSPECIFIED -> size
            else -> size
        }
        return result or (childMeasuredState and MEASURED_STATE_MASK)
    }

    /**
     * Return only the state bits of [.getMeasuredWidthAndState]
     * and [.getMeasuredHeightAndState], combined into one integer.
     * The width component is in the regular bits [.MEASURED_STATE_MASK]
     * and the height component is at the shifted bits
     * [.MEASURED_HEIGHT_STATE_SHIFT]>>[.MEASURED_STATE_MASK].
     */
    fun getMeasuredState(): Int {
        return mMeasuredWidth and MEASURED_STATE_MASK or (mMeasuredHeight shr MEASURED_HEIGHT_STATE_SHIFT and (MEASURED_STATE_MASK shr MEASURED_HEIGHT_STATE_SHIFT))
    }

    open class MarginLayoutParams {

        var leftMargin = 0
        var topMargin = 0
        var bottomMargin = 0
        var rightMargin = 0

    }

    open class LayoutParams: MarginLayoutParams {

        var gravity = Gravity.NO_GRAVITY

        var width = 0
        var height = 0

        var weight = 0f

        constructor()

        constructor(width: Int, height: Int){
            this.width = width
            this.height = height
        }

        constructor(context: Context? = null, attributeSet: AttributeSet, view: View? = null){

            width = attributeSet.getSize("layout_width", WRAP_CONTENT)
            height = attributeSet.getSize("layout_height", WRAP_CONTENT)

            gravity = Gravity.parseGravity(attributeSet.getString("gravity", ""))

            val margin = attributeSet.getSize("layout_margin", view?.getDefaultMargin() ?: 0)
            leftMargin = attributeSet.getSize("layout_marginLeft", margin)
            topMargin = attributeSet.getSize("layout_marginTop", margin)
            rightMargin = attributeSet.getSize("layout_marginRight", margin)
            bottomMargin = attributeSet.getSize("layout_marginBottom", margin)
            weight = attributeSet.getFloat("layout_weight", -1f)

        }

        override fun toString(): String = "[$width x $height ($weight)]"

        companion object {
            const val MATCH_PARENT = -1
            const val WRAP_CONTENT = -2
        }

    }


    /**
     * A MeasureSpec encapsulates the layout requirements passed from parent to child.
     * Each MeasureSpec represents a requirement for either the width or the height.
     * A MeasureSpec is comprised of a size and a mode. There are three possible
     * modes:
     * <dl>
     * <dt>UNSPECIFIED</dt>
     * <dd>
     * The parent has not imposed any constraint on the child. It can be whatever size
     * it wants.
    </dd> *
     *
     * <dt>EXACTLY</dt>
     * <dd>
     * The parent has determined an exact size for the child. The child is going to be
     * given those bounds regardless of how big it wants to be.
    </dd> *
     *
     * <dt>AT_MOST</dt>
     * <dd>
     * The child can be as large as it wants up to the specified size.
    </dd> *
    </dl> *
     *
     * MeasureSpecs are implemented as ints to reduce object allocation. This class
     * is provided to pack and unpack the &lt;size, mode&gt; tuple into the int.
     */
    object MeasureSpec {
        private val MODE_SHIFT = 30
        private val MODE_MASK = 0x3 shl MODE_SHIFT

        /**
         * Measure specification mode: The parent has not imposed any constraint
         * on the child. It can be whatever size it wants.
         */
        val UNSPECIFIED = 0 shl MODE_SHIFT

        /**
         * Measure specification mode: The parent has determined an exact size
         * for the child. The child is going to be given those bounds regardless
         * of how big it wants to be.
         */
        val EXACTLY = 1 shl MODE_SHIFT

        /**
         * Measure specification mode: The child can be as large as it wants up
         * to the specified size.
         */
        val AT_MOST = 2 shl MODE_SHIFT

        /**
         * Creates a measure specification based on the supplied size and mode.
         *
         * The mode must always be one of the following:
         *
         *  * [android.view.View.MeasureSpec.UNSPECIFIED]
         *  * [android.view.View.MeasureSpec.EXACTLY]
         *  * [android.view.View.MeasureSpec.AT_MOST]
         *
         *
         *
         * **Note:** On API level 17 and lower, makeMeasureSpec's
         * implementation was such that the order of arguments did not matter
         * and overflow in either value could impact the resulting MeasureSpec.
         * [android.widget.RelativeLayout] was affected by this bug.
         * Apps targeting API levels greater than 17 will get the fixed, more strict
         * behavior.
         *
         * @param size the size of the measure specification
         * @param mode the mode of the measure specification
         * @return the measure specification based on size and mode
         */
        fun makeMeasureSpec(size: Int,
                            mode: Int): Int {
            return if (sUseBrokenMakeMeasureSpec) {
                size + mode
            } else {
                size and MODE_MASK.inv() or (mode and MODE_MASK)
            }
        }

        /**
         * Like [.makeMeasureSpec], but any spec with a mode of UNSPECIFIED
         * will automatically get a size of 0. Older apps expect this.
         *
         * @hide internal use only for compatibility with system widgets and older apps
         */
        fun makeSafeMeasureSpec(size: Int, mode: Int): Int {
            return if (sUseZeroUnspecifiedMeasureSpec && mode == UNSPECIFIED) {
                0
            } else makeMeasureSpec(size, mode)
        }

        /**
         * Extracts the mode from the supplied measure specification.
         *
         * @param measureSpec the measure specification to extract the mode from
         * @return [android.view.View.MeasureSpec.UNSPECIFIED],
         * [android.view.View.MeasureSpec.AT_MOST] or
         * [android.view.View.MeasureSpec.EXACTLY]
         */
        fun getMode(measureSpec: Int): Int {

            return measureSpec and MODE_MASK
        }

        /**
         * Extracts the size from the supplied measure specification.
         *
         * @param measureSpec the measure specification to extract the size from
         * @return the size in pixels defined in the supplied measure specification
         */
        fun getSize(measureSpec: Int): Int {
            return measureSpec and MODE_MASK.inv()
        }

        internal fun adjust(measureSpec: Int, delta: Int): Int {
            val mode = getMode(measureSpec)
            var size = getSize(measureSpec)
            if (mode == UNSPECIFIED) {
                // No need to adjust size for UNSPECIFIED mode.
                return makeMeasureSpec(size, UNSPECIFIED)
            }
            size += delta
            if (size < 0) {
                println("MeasureSpec.adjust: new size would be negative! ($size) spec: ${toString(measureSpec)} delta: $delta")
                size = 0
            }
            return makeMeasureSpec(size, mode)
        }

        /**
         * Returns a String representation of the specified measure
         * specification.
         *
         * @param measureSpec the measure specification to convert to a String
         * @return a String with the following format: "MeasureSpec: MODE SIZE"
         */
        fun toString(measureSpec: Int): String {
            val mode = getMode(measureSpec)
            val size = getSize(measureSpec)
            return "${when(mode){
                UNSPECIFIED -> "UNSPECIFIED"
                EXACTLY -> "EXACTLY"
                AT_MOST -> "AT_MOST"
                else -> mode.toString()
            }} $size"
        }
    }

    fun setWillNotDraw(boolean: Boolean){} // optimizations, idc ;)

    fun contains(x: Float, y: Float): Boolean {
        return x >= 0f && y >= 0f && x <= mRight - mLeft && y <= mBottom - mTop
    }

    fun processEvent(event: Event): View? {
        val ox = event.x
        val oy = event.y
        val returnValue = if(event is MotionEvent){
            processMotionEvent(event, ox, oy, 0, 0)
        } else {
            processEvent(event, ox, oy, 0, 0)
            null
        }
        event.dx = 0
        event.dy = 0
        event.x = ox
        event.y = oy
        return returnValue
    }

    open fun processMotionEvent(event: MotionEvent, ox: Float, oy: Float, dx: Int, dy: Int): View? {
        // println("$this:$event")
        if(this is ViewGroup){
            for(child in children){
                if(child.visibility == View.VISIBLE){
                    val ndx = dx + child.mLeft
                    val ndy = dy + child.mTop
                    val nx = ox - ndx
                    val ny = oy - ndy
                    if(child.contains(nx, ny)){
                        event.x = nx
                        event.y = ny
                        event.dx = ndy
                        event.dy = ndy
                        val result = child.processMotionEvent(event, ox, oy, ndx, ndy)
                        if(result != null) return result
                    }
                }
            }
        }
        if(event.call(this)) return this
        return null
    }

    open fun processEvent(event: Event, ox: Float, oy: Float, dx: Int, dy: Int): Boolean {
        // println("$this:$event")
        if(this is ViewGroup){
            for(child in children){
                if(child.visibility == View.VISIBLE){
                    val ndx = dx + child.mLeft
                    val ndy = dy + child.mTop
                    val nx = ox - ndx
                    val ny = oy - ndy
                    if(child.contains(nx, ny)){
                        event.x = nx
                        event.y = ny
                        event.dx = ndy
                        event.dy = ndy
                        if(child.processEvent(event, ox, oy, ndx, ndy)) return true
                    }
                }
            }
        }
        if(event.call(this)) return true
        return false
    }

    companion object {

        var uuid = 0

        val System = java.util.System

        /**
         * Use the old (broken) way of building MeasureSpecs.
         */
        val sUseBrokenMakeMeasureSpec = false

        /**
         * Ignore any optimizations using the measure cache.
         */
        val sIgnoreMeasureCache = true

        /**
         * Always return a size of 0 for MeasureSpec values with a mode of UNSPECIFIED
         */
        var sUseZeroUnspecifiedMeasureSpec = false

        /**
         * Ignore an optimization that skips unnecessary EXACTLY layout passes.
         */
        val sAlwaysRemeasureExactly = false

        const val SYSTEM_UI_FLAG_IMMERSIVE = 1
        const val SYSTEM_UI_FLAG_FULLSCREEN = 2
        const val SYSTEM_UI_FLAG_HIDE_NAVIGATION = 3

        const val LAYOUT_DIRECTION_RTL = 1001

        const val VISIBLE = 5
        const val INVISIBLE = 6
        const val GONE = 7

        const val PFLAG_FORCE_LAYOUT = 1
        const val PFLAG_MEASURED_DIMENSION_SET = 2
        const val PFLAG_LAYOUT_REQUIRED = 4
        const val PFLAG_DRAWN = 8

        const val PFLAG3_MEASURE_NEEDED_BEFORE_LAYOUT = 1


        const val MEASURED_SIZE_MASK = 0x00ffffff
        const val MEASURED_STATE_MASK = -0x1000000
        const val MEASURED_HEIGHT_STATE_SHIFT = 16
        const val MEASURED_STATE_TOO_SMALL = 0x01000000

        fun tabs(depth: Int): String {
            val str = "\t\t\t\t\t\t\t\t\t\t\t\t"
            return str.substring(0, min(str.length, depth))
        }

    }

    open fun getDefaultMargin() = 0
    open fun getDefaultPadding() = 0

    open fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int){}

}