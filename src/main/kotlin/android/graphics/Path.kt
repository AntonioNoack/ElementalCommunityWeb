package android.graphics

import org.w3c.dom.CanvasRenderingContext2D

class Path {

    var operations = ArrayList<Operation>()

    fun reset(){
        operations.clear()
    }

    fun moveTo(x: Float, y: Float): Path {
        operations.add(MoveTo(x, y))
        return this
    }

    fun lineTo(x: Float, y: Float): Path {
        operations.add(LineTo(x, y))
        return this
    }

    class LineTo(val x: Float, val y: Float): Operation {
        override fun apply(ctx: CanvasRenderingContext2D) {
            ctx.lineTo(x.toDouble(), y.toDouble())
        }
    }

    class MoveTo(val x: Float, val y: Float): Operation {
        override fun apply(ctx: CanvasRenderingContext2D) {
            ctx.moveTo(x.toDouble(), y.toDouble())
        }
    }

    interface Operation {
        fun apply(ctx: CanvasRenderingContext2D)
    }

}