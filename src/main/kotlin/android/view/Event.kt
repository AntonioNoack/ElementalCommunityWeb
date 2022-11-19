package android.view

import me.antonio.noack.webdroid.Runner.now

abstract class Event(var x: Float, var y: Float){

    var dx = 0
    var dy = 0

    val time = now()

    abstract fun call(view: View): Boolean

}