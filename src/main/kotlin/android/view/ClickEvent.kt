package android.view

import org.w3c.dom.events.MouseEvent

class ClickEvent(event: MouseEvent?, val isLong: Boolean):
        Event(event?.clientX?.toFloat() ?: 0f, event?.clientY?.toFloat() ?: 0f){

    override fun call(view: View): Boolean {
        val isConsumed = if(isLong){
            view.longClickListener?.invoke(view) ?: false
        } else false
        if(isConsumed) return true
        view.clickListener?.invoke(view)
        return false
    }

    override fun toString(): String = "ClickEvent[$x $y $isLong]"

}