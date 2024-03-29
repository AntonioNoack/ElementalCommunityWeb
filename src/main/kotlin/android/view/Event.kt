package android.view

import me.antonio.noack.webdroid.Runner.currentTimeSeconds

abstract class Event(var x: Float, var y: Float) {
    val time = currentTimeSeconds()
    abstract fun call(view: View): Boolean
}