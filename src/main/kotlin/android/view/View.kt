package android.view

import R
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.IntPair
import android.view.View.LayoutParams.Companion.MATCH_PARENT
import android.view.View.LayoutParams.Companion.WRAP_CONTENT
import android.widget.getDefaultSize
import me.antonio.noack.maths.MathsUtils.sq
import me.antonio.noack.webdroid.Runner
import me.antonio.noack.webdroid.setTimeout
import kotlin.math.max
import kotlin.math.sqrt

external fun cloneView(function: JsClass<out View>, self: Context?, attributes: AttributeSet): View

open class View(ctx: Context?, attributeSet: AttributeSet?) {

    fun getGlobalPosition(): IntPair {
        val parent = mParent ?: return IntPair(mLeft, mTop)
        return parent.getGlobalPosition().added(mLeft, mTop)
    }

    open fun destroy() {
        visibility = GONE
        onVisibilityChanged()
    }

    val uuid = Companion.uuid++
    var alpha = 1f

    open fun onVisibilityChanged() {}

    fun runParentsThenSelf(runnable: (it: View) -> Unit) {
        mParent?.runParentsThenSelf(runnable)
        runnable(this)
    }

    fun runOnUiThread(runnable: () -> Unit) {
        setTimeout(runnable, 0)
    }

    override fun toString(): String {
        return "${getId()} (${this::class.simpleName}) " +
                "WH[${getWidth()}, ${getHeight()}] " +
                "MWH[${this.measuredWidth}, ${this.measuredHeight}] " +
                "Bounds[$mLeft, $mTop, $mRight, $mBottom] " +
                "Padding[$paddingLeft, $paddingTop, $paddingRight, $paddingBottom] " +
                "Margin[${layoutParams.leftMargin}, ${layoutParams.topMargin}, ${layoutParams.rightMargin}, ${layoutParams.bottomMargin}] " +
                "LayPar[$layoutParams]"
    }

    fun toStringWithVisibleChildren(depth: Int = 0): String = if (this is ViewGroup)
        "${toString()} [${
            children
                .filter {
                    // it.isEffectivelyVisible()
                    it.visibility != GONE
                }
                .joinToString { "\n${tabs(depth)}${it.toStringWithVisibleChildren(depth + 1)}" }
        }]" else toString()

    fun performClick() {
        processEvent(ClickEvent(null, false))
    }

    fun performLongClick() {
        processEvent(ClickEvent(null, true))
    }

    fun isEffectivelyVisible(): Boolean = this.visibility == VISIBLE && getWidth() > 0 && getHeight() > 0

    // clone the attributes from the style specified in the theme attribute
    fun cloneAttributesFromStyle() {
        val styleName = attributeSet.getString("theme", "")
        if (styleName.isEmpty()) return
        val style = R.allStyles.findViewById<View>(styleName) ?: return
        val attributes = style.attributeSet.values
        val myValues = attributeSet.values
        for ((key, value) in attributes) {
            if (!myValues.containsKey(key)) {
                myValues[key] = value
            }
        }
    }

    open fun init() {
        cloneAttributesFromStyle()
        onInit()
    }

    /**
     * reads the values from the xml
     * */
    open fun onInit() {

        layoutParams = LayoutParams(attributeSet, this)

        // layout_gravity for the layout, gravity for the text
        // baseline aligned?

        val padding = attributeSet.getSize("padding", getDefaultPadding())
        paddingLeft = attributeSet.getSize("paddingLeft", padding)
        paddingTop = attributeSet.getSize("paddingTop", padding)
        paddingRight = attributeSet.getSize("paddingRight", padding)
        paddingBottom = attributeSet.getSize("paddingBottom", padding)

        mBackground = attributeSet.getDrawable("background", null)

        this.visibility = when (attributeSet.getString("visibility", "")) {
            "invisible" -> INVISIBLE
            "gone" -> GONE
            else -> VISIBLE
        }

    }

    fun getId(): String = attributeSet.getString("id", uuid.toString())

    private var hasCalledSetMeasuredDims = false

    val depth: Int get() = (mParent?.depth ?: -1) + 1

    fun measure(parentWidthSpec: Int, parentHeightSpec: Int) {
        /*println(
            "${tabs(depth)}[${this::class.simpleName}] measure(" +
                    "${MeasureSpec.toString(parentWidthSpec, layoutParams.width)}, " +
                    "${MeasureSpec.toString(parentHeightSpec, layoutParams.height)}, " +
                    "${layoutParams.weight})"
        )
        hasCalledSetMeasuredDims = false*/
        onMeasure(parentWidthSpec, parentHeightSpec)
        /*if (!hasCalledSetMeasuredDims) {
            throw IllegalStateException(
                "View with id " + getId() + ": ${this::class.simpleName}"
                        + "#onMeasure() did not set the"
                        + " measured dimension by calling"
                        + " setMeasuredDimension()"
            )
        }
        if (this is ViewGroup) {
            println("${tabs(depth)}[${this::class.simpleName}] measured: ($measuredWidth, $measuredHeight), children: ${
                children.map { "${it.measuredWidth} x ${it.measuredHeight}" }
            }")
        } else {
            println("${tabs(depth)}[${this::class.simpleName}] measured: ($measuredWidth, $measuredHeight)")
        }*/
    }

    fun setMeasuredDimension(theMeasuredWidth: Int, theMeasuredHeight: Int) {
        measuredWidth = theMeasuredWidth
        measuredHeight = theMeasuredHeight
        hasCalledSetMeasuredDims = true
    }

    var mLeft = 0
    var mTop = 0
    var mRight = 0
    var mBottom = 0

    fun layout(l: Int, t: Int, r: Int, b: Int) {
        mLeft = l
        mTop = t
        mRight = r
        mBottom = b
    }

    fun getLeft() = mLeft
    fun getTop() = mTop
    fun getRight() = mRight
    fun getBottom() = mBottom
    fun getWidth() = mRight - mLeft
    fun getHeight() = mBottom - mTop

    // irrelevant for drawing
    val x get() = 0
    val y get() = 0

    val width: Int
        get() = mRight - mLeft

    val height: Int
        get() = mBottom - mTop

    open fun clone(): View {
        val ktClass = this::class
        val jsClass = ktClass.js
        val cloned = cloneView(jsClass, context, attributeSet)
        if (this is ViewGroup) {
            cloned as ViewGroup
            for (child in children) {
                cloned.addView(child.clone())
            }
        }
        cloned.init()
        return cloned
    }

    val parent: ViewGroup?
        get() = mParent

    open fun <V : View> findViewById(id: String): V? {
        @Suppress("UNCHECKED_CAST")
        if (attributeSet.values["id"] == id) return this as V
        return null
    }

    var layoutParams = LayoutParams(MATCH_PARENT, MATCH_PARENT)

    var mParent: ViewGroup? = null

    val attributeSet = attributeSet ?: AttributeSet()

    val context = ctx

    var mBackground: Drawable? = null

    var minimumWidth = 0
    var minimumHeight = 0

    var measuredWidth = 0
    var measuredHeight = 0

    var paddingLeft = 0
    var paddingTop = 0
    var paddingRight = 0
    var paddingBottom = 0

    val paddingX get() = paddingLeft + paddingRight
    val paddingY get() = paddingTop + paddingBottom

    var visibility = VISIBLE
        set(value) {
            if (field != value) {
                field = value
                onVisibilityChanged()
                invalidate()
            }
        }

    fun getLayoutParams(): LayoutParams = layoutParams
    fun getMeasuredHeight() = this.measuredHeight
    fun getMeasuredWidth() = this.measuredWidth

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
        if (layoutParams.width > -1) layoutParams.width
        else if (mBackground == null) this.minimumWidth
        else max(this.minimumWidth, mBackground!!.getMinimumWidth())

    open fun getSuggestedMinimumHeight(): Int =
        if (layoutParams.height > -1) layoutParams.height
        else if (mBackground == null) this.minimumHeight
        else max(this.minimumHeight, mBackground!!.getMinimumHeight())

    fun attr(key: String, value: String): View {
        attributeSet.values[key] = value
        return this
    }

    fun attr(key: String, value: Int): View {
        attributeSet.values[key] = value.toString()
        return this
    }

    var isInvalid = -1
    open fun invalidate() {
        if (isInvalid < Runner.invalidTime) {
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
    var downTime = 0.0

    var touchListener: ((view: View, event: MotionEvent) -> Boolean)? = { _, e ->
        if (clickListener == null && longClickListener == null) false else when (e.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                disabled = false
                distance = 0f
                downTime = e.time
                lastX = e.originalX
                lastY = e.originalY
                true
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                disabled = true
                true
            }
            MotionEvent.ACTION_UP -> {
                if (!disabled) {
                    val clickDuration = e.time - downTime
                    if (clickDuration < 0.3f) {
                        performClick()
                    } else {
                        performLongClick()
                    }
                }
                false
            }
            MotionEvent.ACTION_MOVE -> {
                if (!disabled) {
                    distance += sqrt(sq(lastX - e.originalX, lastY - e.originalY))
                    lastX = e.originalX
                    lastY = e.originalY
                    disabled = distance > 30f
                }
                false
            }
            else -> false
        }
    }

    fun setOnClickListener(listener: (view: View) -> Unit): View {
        clickListener = listener
        return this
    }

    fun setOnLongClickListener(listener: (view: View) -> Boolean): View {
        longClickListener = listener
        return this
    }

    var isButtonDown = false

    private fun sq(x: Float) = x * x

    fun setOnTouchListener(listener: (view: View, event: MotionEvent) -> Boolean): View {
        touchListener = listener
        return this
    }

    fun setOnKeyListener(listener: (view: View, event: KeyEvent) -> Boolean): View {
        keyListener = listener
        return this
    }

    fun postInvalidate() {
        invalidate()
    }

    open fun onDraw(canvas: Canvas) {
        drawBackground(canvas)
    }

    open fun drawBackground(canvas: Canvas) {
        mBackground?.setBounds(0, 0, this.measuredWidth, this.measuredHeight)
        mBackground?.draw(canvas, alpha)
    }

    open fun draw(canvas: Canvas) {
        onDraw(canvas)
    }

    open fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val lp = layoutParams
        setMeasuredDimension(
            getDefaultSize(widthMeasureSpec, getSuggestedMinimumWidth(), lp.width),
            getDefaultSize(heightMeasureSpec, getSuggestedMinimumHeight(), lp.height)
        )
    }

    class LayoutParams {

        var leftMargin = 0
        var topMargin = 0
        var bottomMargin = 0
        var rightMargin = 0

        val marginX get() = leftMargin + rightMargin
        val marginY get() = topMargin + bottomMargin

        var gravity = Gravity.NO_GRAVITY

        var width = 0
        var height = 0

        var weight = 0f

        constructor(width: Int, height: Int) {
            this.width = width
            this.height = height
        }

        constructor(attributeSet: AttributeSet, view: View? = null) {

            width = attributeSet.getSize("layout_width", WRAP_CONTENT)
            height = attributeSet.getSize("layout_height", WRAP_CONTENT)

            gravity = Gravity.parseGravity(attributeSet.getString("gravity", ""))

            val margin = attributeSet.getSize("layout_margin", view?.getDefaultMargin() ?: 0)
            leftMargin = attributeSet.getSize("layout_marginLeft", margin)
            topMargin = attributeSet.getSize("layout_marginTop", margin)
            rightMargin = attributeSet.getSize("layout_marginRight", margin)
            bottomMargin = attributeSet.getSize("layout_marginBottom", margin)
            weight = attributeSet.getFloat("layout_weight", 0f)
        }

        override fun toString(): String = "[$width x $height ($weight)]"

        companion object {
            const val MATCH_PARENT = -1
            const val WRAP_CONTENT = -2
        }
    }

    object MeasureSpec {
        private const val MODE_SHIFT = 30
        private const val MODE_MASK = 0x3 shl MODE_SHIFT

        /**
         * Measure specification mode: The parent has not imposed any constraint
         * on the child. It can be whatever size it wants.
         */
        const val UNSPECIFIED = 0 shl MODE_SHIFT

        /**
         * Measure specification mode: The parent has determined an exact size
         * for the child. The child is going to be given those bounds regardless
         * of how big it wants to be.
         */
        const val EXACTLY = 1 shl MODE_SHIFT

        /**
         * Measure specification mode: The child can be as large as it wants up
         * to the specified size.
         */
        const val AT_MOST = 2 shl MODE_SHIFT

        fun makeMeasureSpec(size: Int, mode: Int): Int {
            if (mode == UNSPECIFIED) return UNSPECIFIED
            return max(size, 0).and(MODE_MASK.inv()) or (mode.and(MODE_MASK))
        }

        fun getMode(measureSpec: Int): Int {
            return measureSpec and MODE_MASK
        }

        fun getSize(measureSpec: Int): Int {
            return measureSpec and MODE_MASK.inv()
        }

        fun toString(measureSpec: Int): String {
            val size = getSize(measureSpec)
            return when (getMode(measureSpec)) {
                UNSPECIFIED -> "?"
                EXACTLY -> "==$size"
                AT_MOST -> "<=$size"
                else -> "!"
            }
        }

        fun toString(measureSpec: Int, lp: Int): String {
            return when (lp) {
                MATCH_PARENT -> "mp"
                WRAP_CONTENT -> "wc"
                else -> "$lp"
            } + "(" + toString(measureSpec) + ")"
        }
    }

    fun contains(x: Float, y: Float): Boolean {
        return x >= 0f && y >= 0f && x <= mRight - mLeft && y <= mBottom - mTop
    }

    fun processEvent(event: Event): View? {
        return processEvent(event, event.x, event.y, 0, 0)
    }

    open fun processEvent(event: Event, ox: Float, oy: Float, dx: Int, dy: Int): View? {
        return if (event.call(this)) this else null
    }

    open fun addView(view: View): ViewGroup {
        this as ViewGroup
        view.mParent = this
        children.add(view)
        invalidate()
        return this
    }

    companion object {

        var uuid = 0

        const val VISIBLE = 5
        const val INVISIBLE = 6
        const val GONE = 7

        fun tabs(depth: Int): String {
            return "  ".repeat(depth)
        }
    }

    open fun getDefaultMargin() = 0
    open fun getDefaultPadding() = 0

}