package androidx.appcompat.app

import R
import R.all
import android.app.Dialog
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.widget.Button
import android.widget.LinearLayout
import android.widget.placeChild
import kotlinx.browser.document
import me.antonio.noack.maths.MathsUtils.dpToPx
import kotlin.math.min

class AlertDialog(val child: View) : Dialog(child.context, null) {

    var title = ""
    val window: Window = Window.instance

    override fun onInit() {
        super.onInit()
        children.clear()
        addView(child)

        val padding = dpToPx(10f).toInt()
        paddingLeft = padding
        paddingTop = padding
        paddingRight = padding
        paddingBottom = padding

        mBackground = ColorDrawable(0x77777777)
    }

    override fun dismiss() {
        parent?.children?.remove(this)
        parent?.invalidate()
        destroy()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val lp = child.layoutParams
        val offsetX = paddingX + lp.marginX
        val offsetY = paddingY + lp.marginY

        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)

        // make it no longer than a square
        val allowedSize = min(width - offsetX, height - offsetY)
        measureChildWithMargins(
            child,
            MeasureSpec.makeMeasureSpec(allowedSize, MeasureSpec.AT_MOST),
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        )

        val minHeight = child.measuredHeight
        measureChildWithMargins( // todo why +20???
            child,
            MeasureSpec.makeMeasureSpec(allowedSize, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(minHeight + 20, MeasureSpec.EXACTLY)
        )

        // center child
        val posX = (width - child.measuredWidth) / 2
        val posY = (height - child.measuredHeight) / 2
        placeChild(child, posX, posY)

        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (parent?.children?.lastOrNull() == this) {
            document.title = title
        }
    }

    class Builder(val context: Context) {

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
            return setView(
                R.layout.allLayouts.first {
                    it.attributeSet.values["layout_id"] == id
                }
            )
        }

        fun setPositiveButton(text: String, listener: ((it: Dialog, which: Int) -> Unit)?): Builder {
            if (view == null) view = getDefaultView()
            (view as? ViewGroup)?.addView(
                Button(context, null)
                    .attr("text", text)
                    .attr("width", 0)
                    .attr("height", LayoutParams.WRAP_CONTENT)
                    .attr("weight", 1)
                    .setOnClickListener {
                        listener?.invoke(dialog!!, 0)
                        dialog?.dismiss()
                    }
            )
            return this
        }

        fun setNegativeButton(text: String, listener: ((it: Dialog, which: Int) -> Unit)?): Builder {
            if (view == null) view = getDefaultView()
            (view as? ViewGroup)?.addView(
                Button(context, null)
                    .attr("text", text)
                    .attr("width", 0)
                    .attr("height", LayoutParams.WRAP_CONTENT)
                    .attr("weight", 1)
                    .setOnClickListener {
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
            all.addView(dialog)
            dialog.init()
            dialog.invalidate()
            return dialog
        }

        private fun getDefaultView(): View {
            return LinearLayout(context, null)
                .attr("background", 0xff000000.toInt())
                .attr("orientation", "horizontal")
        }

    }

    override fun processEvent(event: Event, ox: Float, oy: Float, dx: Int, dy: Int): View? {
        val ndx = dx + child.mLeft
        val ndy = dy + child.mTop
        val nx = ox - ndx
        val ny = oy - ndy
        return if (isCancelable && event is MotionEvent &&
            event.actionMasked == MotionEvent.ACTION_UP &&
            !child.contains(nx, ny)
        ) {
            dismiss()
            this
        } else super.processEvent(event, ox, oy, dx, dy)
    }
}