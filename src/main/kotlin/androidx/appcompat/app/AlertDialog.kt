package androidx.appcompat.app

import android.app.Dialog
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.widget.Button
import android.widget.LinearLayout
import kotlinx.browser.document
import me.antonio.noack.maths.MathsUtils.dpToPx
import me.antonio.noack.webdroid.Runner
import kotlin.math.max
import kotlin.math.min

class AlertDialog(val child: View): Dialog(child.context, null){

    var title = ""
    val window: Window = Window.instance

    override fun onInit() {
        super.onInit()
        children.clear()
        addChild(child)

        val padding = dpToPx(10f).toInt()
        mPaddingLeft = padding
        mPaddingTop = padding
        mPaddingRight = padding
        mPaddingBottom = padding

        mBackground = ColorDrawable(0x77777777)

    }

    override fun dismiss() {
        Runner.dialogStack.remove(this)
        Runner.invalidate()
        destroy()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val lp = child.layoutParams

        val offsetX = mPaddingLeft + mPaddingRight + lp.leftMargin + lp.rightMargin
        val offsetY = mPaddingTop + mPaddingBottom + lp.topMargin + lp.bottomMargin

        // todo try to predict the size linearly, measure with at most

        val width0 = MeasureSpec.getSize(widthMeasureSpec) - offsetX
        val height0 = MeasureSpec.getSize(heightMeasureSpec) - offsetY

        child.measure(MeasureSpec.makeMeasureSpec(width0, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(height0, MeasureSpec.AT_MOST))

        val min = min(width0, height0)
        val ms1 = MeasureSpec.makeMeasureSpec(min, MeasureSpec.AT_MOST)
        val ms2 = MeasureSpec.makeMeasureSpec(min, MeasureSpec.AT_MOST)

        // get the ideal screen ratio
        measureChildWithMargins(child, ms1, 0, ms2, 0)

        // get the ratio...
        val scale = 1f / max(0.01f, max(child.measuredWidth.toFloat() / width0, child.measuredHeight.toFloat() / height0))

        // println("${child.measuredWidth.toFloat()} $width0")
        // println("${child.measuredHeight.toFloat()} $height0")

        val better = (scale * min).toInt()

        println("$scale -> $better")

        val ms3 = MeasureSpec.makeMeasureSpec(better - offsetX, MeasureSpec.AT_MOST)
        val ms4 = MeasureSpec.makeMeasureSpec(better, MeasureSpec.AT_MOST)

        // val widthMeasureSpec2 = MeasureSpec.makeMeasureSpec(availableWidth, MeasureSpec.getMode(widthMeasureSpec))

        measureChildWithMargins(child, ms3, 0, ms4, 0)
        // measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0)

        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        alignChild(child, width, height)


    }

    fun alignChild(child: View, width: Int, height: Int){
        // set this view exactly in the center
        val shiftX = max(0, (width - child.getMeasuredWidth()) / 2)
        val shiftY = max(0, (height - child.getMeasuredHeight()) / 2)
        child.layout(shiftX, shiftY, shiftX + child.getMeasuredWidth(), shiftY + child.getMeasuredHeight())
        // println("child [$shiftX, $shiftY] $mPaddingLeft $mPaddingTop $mPaddingRight $mPaddingBottom ${child.measuredWidth}, ${child.measuredHeight} | self $width, $height")
        setMeasuredDimension(width, height)
        layout(0, 0, width, height)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if(Runner.dialogStack.peek() == this){
            document.title = title
        }
    }

    class Builder(val context: Context){

        var title = ""
        var view: View? = null
        var cancelable = true
        var dialog: AlertDialog? = null

        fun setCancelable(boolean: Boolean): Builder {
            cancelable = boolean
            return this
        }

        fun setView(view: View): Builder {
            this.view = view.clone()
            return this
        }

        fun setTitle(string: String): Builder {
            title = string
            return this
        }

        fun setView(id: String): Builder {
            view = R.allLayouts.findElementById(id)
            return this
        }

        fun setPositiveButton(text: String, listener: ((it: Dialog, which: Int) -> Unit)?): Builder {
            if(view == null) view = getDefaultView()
            view?.addChild(
                    Button(context, null)
                            .attr("text", text)
                            .attr("width", 0)
                            .attr("height", LayoutParams.WRAP_CONTENT)
                            .attr("weight", 1)
                            .setOnClickListener {
                                println("yes")
                                listener?.invoke(dialog!!, 0)
                                dialog?.dismiss()
                            }
            )
            return this
        }

        fun setNegativeButton(text: String, listener: ((it: Dialog, which: Int) -> Unit)?): Builder {
            if(view == null) view = getDefaultView()
            view?.addChild(
                    Button(context, null)
                            .attr("text", text)
                            .attr("width", 0)
                            .attr("height", LayoutParams.WRAP_CONTENT)
                            .attr("weight", 1)
                            .setOnClickListener {
                                println("no")
                                listener?.invoke(dialog!!, 0)
                                dialog?.dismiss()
                            }
            )
            return this
        }

        fun show(): AlertDialog {
            val view = view ?: getDefaultView()
            val dialog = AlertDialog(view)
            dialog.isCancelable = cancelable
            dialog.title = title
            this.dialog = dialog
            Runner.dialogStack.push(dialog)
            R.all.invalidate()
            dialog.init()
            dialog.invalidate()
            // println(dialog.toStringWithVisibleChildren())
            return dialog
        }

        fun getDefaultView(): View {
            return LinearLayout(context, null)
                    .attr("background", 0xff000000.toInt())
                    .attr("orientation", "horizontal")
                    .attr("width", LayoutParams.MATCH_PARENT)
                    .attr("height", LayoutParams.WRAP_CONTENT)
        }

    }

    override fun processMotionEvent(event: MotionEvent, ox: Float, oy: Float, dx: Int, dy: Int): View? {
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
        } else {
            if(isCancelable && event.actionMasked == MotionEvent.ACTION_UP){
                dismiss()
                return this
            }
        }
        if(event.call(this)) return this
        return null
    }

    override fun processEvent(event: Event, ox: Float, oy: Float, dx: Int, dy: Int): Boolean {
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
        } else {
            if(isCancelable && event is MotionEvent && event.actionMasked == MotionEvent.ACTION_UP){
                dismiss()
                return true
            }
        }
        return event.call(this)
    }
}