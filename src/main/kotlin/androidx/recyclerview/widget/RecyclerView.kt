package androidx.recyclerview.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import me.antonio.noack.elementalcommunity.itempedia.ItempediaAdapter
import kotlin.math.max

class RecyclerView(ctx: Context?, attributeSet: AttributeSet?) : ScrollView(ctx, attributeSet) {

    abstract class ViewHolder(val itemView: View)

    abstract class Adapter<V : ViewHolder> {
        abstract fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItempediaAdapter.ViewHolder
        abstract fun onBindViewHolder(holder: ViewHolder, position: Int)
        abstract fun getItemCount(): Int
        fun notifyDataSetChanged() {}
    }

    interface OnItemTouchListener {
        fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean
        fun onTouchEvent(rv: RecyclerView, e: MotionEvent)
        fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean)
    }

    @Suppress("UNUSED_PARAMETER")
    fun setHasFixedSize(v: Boolean) {}

    var layoutManager: GridLayoutManager? = null
    var adapter: Adapter<*>? = null
    private val numColumns = 10

    private val listeners = ArrayList<OnItemTouchListener>()
    fun addOnItemTouchListener(listener: OnItemTouchListener) {
        listeners.add(listener)
    }

    private val holders = ArrayList<ViewHolder>()

    fun smoothScrollToPosition(position: Int) {
        scroll = position.toFloat()
        invalidate()
        // this is only called, when the children need to be updated
        initializeAllChildren()
    }

    private fun initializeAllChildren() {
        validateChildren()
        val adapter = adapter ?: return
        for (i in holders.indices) {
            adapter.onBindViewHolder(holders[i], i)
        }
    }

    private fun validateChildren() {
        val adapter = adapter ?: return
        val numChildren = adapter.getItemCount()
        for (i in childCount until numChildren) {
            val holder = adapter.onCreateViewHolder(this, 0)
            holders.add(holder)
            addView(holder.itemView)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        validateChildren()
        val child = children.firstOrNull()
        if (child == null) {
            measureByEmpty(widthMeasureSpec, heightMeasureSpec)
        } else {

            val sizeX = max(numColumns, 1)
            val sizeY = (children.size + sizeX - 1) / sizeX

            val lp = child.layoutParams
            val availableW = MeasureSpec.getSize(widthMeasureSpec) - paddingX
            val availableWPerChild = availableW / sizeX - lp.marginX

            child.measure(
                MeasureSpec.makeMeasureSpec(availableWPerChild, MeasureSpec.AT_MOST),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
            )

            shownScroll = scroll.toInt()

            var i = 0
            val availableHPerChild = lp.marginY + child.measuredHeight
            for (y in 0 until sizeY) {
                var dx = 0
                val dy = availableHPerChild * y - shownScroll
                for (x in 0 until sizeX) {
                    val childI = children.getOrNull(i++) ?: break
                    childI.measuredWidth = child.measuredWidth
                    childI.measuredHeight = child.measuredHeight
                    placeChild(childI, dx, dy)
                    dx += availableWPerChild
                }
            }

            contentLength = idealHeight(child, sizeY)
            measureByChild(widthMeasureSpec, heightMeasureSpec, child, sizeX, sizeY)
        }
    }
}