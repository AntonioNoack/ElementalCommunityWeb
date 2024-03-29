package android.util

data class IntPair(var x: Int, var y: Int) {
    fun added(dx: Int, dy: Int): IntPair {
        x += dx
        y += dy
        return this
    }
}