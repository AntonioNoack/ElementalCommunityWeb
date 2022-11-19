package android.view

import org.w3c.dom.events.KeyboardEvent

class KeyEvent(x: Float, y: Float, event: KeyboardEvent): Event(x, y){

    override fun call(view: View): Boolean {
        // todo implement the key called function...
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toString(): String = "KeyEvent[...]"

}