package android.graphics

open class Rect(var left: Int, var top: Int, var right: Int, var bottom: Int){

    constructor(): this(0,0,0,0)

    fun set(l: Int, t: Int, r: Int, b: Int){
        left = l
        top = t
        right = r
        bottom = b
    }

    fun height() = bottom - top
    fun width() = right - left

    fun setBounds(left: Int, top: Int, right: Int, bottom: Int){
        this.left = left
        this.top = top
        this.right = right
        this.bottom = bottom
    }

    companion object {
        val NONE = Rect()
    }

    override fun toString(): String = "[$left, $top, $right, $bottom]"

}