package me.antonio.noack.webdroid

class KtTouch(val id: dynamic, var lastX: Float, var lastY: Float) {
    var currentX = lastX
    var currentY = lastY
    var isValid = false
    override fun toString(): String = "[$currentX -> $lastX]"
}