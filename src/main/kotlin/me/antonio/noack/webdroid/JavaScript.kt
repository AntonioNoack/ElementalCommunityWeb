package me.antonio.noack.webdroid

import org.w3c.dom.Element
import org.w3c.dom.events.MouseEvent
import org.w3c.dom.events.WheelEventInit

open external class TouchList {
    val length: Int
    fun item(index: Int): Touch
}

operator fun TouchList.iterator(): Iterator<Touch> {
    var index = 0
    return object: Iterator<Touch> {
        override fun hasNext(): Boolean = index < length
        override fun next(): Touch = item(index++)
    }
}

open external class Touch {
    var identifier: dynamic
    var screenX: Double
    var screenY: Double
    var clientX: Double
    var clientY: Double
    var pageX: Double
    var pageY: Double
    var target: Element
    // experimental:
    var radiusX: Double
    var radiusY: Double
    var rotationAngle: Double // degrees
    var force: Float // 0 .. 1
}

open external class TouchEvent(type: String, eventInitDict: WheelEventInit = definedExternally) : MouseEvent {
    var touches: TouchList
    var originalEvent: TouchEvent?
}

external fun setTimeout(runnable: () -> Unit, time: Int)
external fun requestAnimationFrame(runnable: () -> Unit)