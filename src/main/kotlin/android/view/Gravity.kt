package android.view

/**
 * Standard constants and tools for placing an object within a potentially
 * larger container.
 */
object Gravity {

    const val MIN = 0
    const val CEN = 1
   const val MAX = 2
    const val FIL = 3

    private fun merge(x: Int, y: Int): Int {
        return x.shl(2) or y
    }

    fun getOffset(type: Int, parent: Int, self: Int): Int {
        return when (type) {
            CEN -> (parent - self) / 2
            MAX -> parent - self
            else -> 0
        }
    }

    fun getSize(type: Int, parent: Int, self: Int): Int {
        return when (type) {
            FIL -> parent
            else -> self
        }
    }

    fun getGravityX(gravity: Int) = gravity.shr(2).and(3)
    fun getGravityY(gravity: Int) = gravity.and(3)

    fun getOffsetX(gravity: Int, parent: Int, self: Int): Int {
        return getOffset(getGravityX(gravity), parent, self)
    }

    fun getOffsetY(gravity: Int, parent: Int, self: Int): Int {
        return getOffset(getGravityY(gravity), parent, self)
    }

    fun getSizeX(gravity: Int, parent: Int, self: Int): Int {
        return getSize(getGravityX(gravity), parent, self)
    }

    fun getSizeY(gravity: Int, parent: Int, self: Int): Int {
        return getSize(getGravityY(gravity), parent, self)
    }

    val NO_GRAVITY = merge(MIN, MIN)
    val TOP = NO_GRAVITY
    val BOTTOM = merge(MIN, MAX)
    val LEFT = NO_GRAVITY
    val RIGHT = merge(MAX, MIN)
    val CENTER_VERTICAL = merge(MIN, CEN)
    val CENTER_HORIZONTAL = merge(CEN, MIN)
    val CENTER = merge(CEN, CEN)
    val FILL = merge(FIL, FIL)

    val HORIZONTAL_GRAVITY_MASK = merge(FIL, 0)

    fun parseGravity(str: String): Int {
        return when (str.toUpperCase()) {
            "TOP" -> TOP
            "BOTTOM" -> BOTTOM
            "FILL" -> FILL
            "LEFT" -> LEFT
            "RIGHT" -> RIGHT
            "CENTER" -> CENTER
            "CENTER_VERTICAL" -> CENTER_VERTICAL
            "CENTER_HORIZONTAL" -> CENTER_HORIZONTAL
            else -> NO_GRAVITY
        }
    }
}
