package androidx.recyclerview.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import me.antonio.noack.elementalcommunity.itempedia.ItempediaAdapter

class RecyclerView(ctx: Context?, attributeSet: AttributeSet?) : View(ctx, attributeSet) {
    abstract class ViewHolder(view: View) {

    }

    abstract class Adapter<V : ViewHolder> {
        abstract fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItempediaAdapter.ViewHolder
        abstract fun onBindViewHolder(holder: ItempediaAdapter.ViewHolder, position: Int)
        abstract fun getItemCount(): Int

        fun notifyDataSetChanged() {
            // todo update all views...
        }
    }

    interface OnItemTouchListener {
        fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean
        fun onTouchEvent(rv: RecyclerView, e: MotionEvent)
        fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean)
    }

    fun setHasFixedSize(v: Boolean) {}
    var layoutManager: GridLayoutManager? = null
    var adapter: Any? = null

    private val listeners = ArrayList<OnItemTouchListener>()
    fun addOnItemTouchListener(listener: OnItemTouchListener) {
        listeners.add(listener)
    }

    fun smoothScrollToPosition(position: Int) {
        // todo scroll...
    }
}