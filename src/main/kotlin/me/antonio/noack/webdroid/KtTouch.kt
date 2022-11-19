package me.antonio.noack.webdroid

class KtTouch(val id: dynamic, var targetX: Float, var targetY: Float){
    var currentX = targetX
    var currentY = targetY
    var isValid = false
    override fun toString(): String = "[$currentX -> $targetX]"
}