package android.graphics

open class RectF(var left: Float, var top: Float, var right: Float, var bottom: Float){

    constructor(): this(0f,0f,0f,0f)

    fun height() = bottom - top
    fun width() = right - left

    fun setBounds(left: Int, top: Int, right: Int, bottom: Int){
        this.left = left.toFloat()
        this.top = top.toFloat()
        this.right = right.toFloat()
        this.bottom = bottom.toFloat()
    }

    fun setBounds(left: Float, top: Float, right: Float, bottom: Float){
        this.left = left
        this.top = top
        this.right = right
        this.bottom = bottom
    }

}