package android.graphics

import org.w3c.dom.CanvasRenderingContext2D
import java.util.System
import kotlin.math.PI

typealias System = System

class Canvas(val ctx: CanvasRenderingContext2D){

    var ctr = 0

    fun fillRect(left: Int, top: Int, right: Int, bottom: Int, color: Int){
        // println("draw $left, $top, $right, $bottom, ${colorString(color)}, #${colorString(color)}")
        ctx.fillStyle = rgbaString(color)
        ctx.fillRect(left.toDouble(), top.toDouble(), (right-left).toDouble(), (bottom-top).toDouble())
    }

    fun fillRect(left: Float, top: Float, right: Float, bottom: Float, color: Int){
        ctx.fillStyle = rgbaString(color)
        ctx.fillRect(left.toDouble(), top.toDouble(), (right-left).toDouble(), (bottom-top).toDouble())
    }

    private fun lineRect(left: Float, top: Float, right: Float, bottom: Float, color: Int){
        ctx.strokeStyle = rgbaString(color)
        ctx.lineWidth = 1.0
        ctx.beginPath()
        ctx.rect(left.toDouble(), top.toDouble(), (right-left).toDouble(), (bottom-top).toDouble())
        ctx.stroke()
    }

    fun lineRect(left: Int, top: Int, right: Int, bottom: Int, color: Int){
        ctx.strokeStyle = rgbaString(color)
        ctx.lineWidth = 1.0
        ctx.beginPath()
        ctx.rect(left.toDouble(), top.toDouble(), (right-left).toDouble(), (bottom-top).toDouble())
        ctx.stroke()
    }

    fun drawRect(left: Int, top: Int, right: Int, bottom: Int, paint: Paint){
        drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint)
    }

    fun drawRect(left: Float, top: Float, right: Float, bottom: Float, paint: Paint){
        val style = rgbaString(paint.color)
        when(paint.style){
            Paint.Style.FILL -> {
                ctx.fillStyle = style
                ctx.fillRect(left.toDouble(), top.toDouble(), (right-left).toDouble(), (bottom-top).toDouble())
            }
            Paint.Style.STROKE -> {
                lineRect(left, top, right, bottom, paint.color)
            }
            Paint.Style.FILL_AND_STROKE -> {
                ctx.fillStyle = style
                ctx.fillRect(left.toDouble(), top.toDouble(), (right-left).toDouble(), (bottom-top).toDouble())
                lineRect(left, top, right, bottom, paint.color)
            }
        }
    }

    fun drawLine(x0: Float, y0: Float, x1: Float, y1: Float, paint: Paint){
        ctx.strokeStyle = rgbaString(paint.color)
        ctx.lineWidth = paint.strokeWidth.toDouble()
        ctx.beginPath()
        ctx.moveTo(x0.toDouble(), y0.toDouble())
        ctx.lineTo(x1.toDouble(), y1.toDouble())
        ctx.stroke()
    }

    fun drawText(text: String, x: Float, y: Float, paint: Paint){
        ctx.font = fontString(paint)
        ctx.textAlign = paint.textAlign.jsName
        ctx.fillStyle = rgbaString(paint.color)
        ctx.fillText(text, x.toDouble(), y.toDouble())
    }

    fun drawPath(path: Path, paint: Paint){
        val style = rgbaString(paint.color)
        ctx.fillStyle = style
        ctx.strokeStyle = style
        ctx.lineWidth = paint.strokeWidth.toDouble()
        ctx.strokeStyle = rgbaString(paint.color)
        ctx.beginPath()
        for(op in path.operations){
            op.apply(ctx)
        }
        when(paint.style){
            Paint.Style.FILL -> {
                ctx.fill()
            }
            Paint.Style.STROKE -> {
                ctx.stroke()
            }
            Paint.Style.FILL_AND_STROKE -> {
                ctx.fill()
                ctx.stroke()
            }
        }
    }

    fun drawCircle(cx: Float, cy: Float, r: Float, color: Int,
                   style2: Paint.Style = Paint.Style.FILL, lineWidth: Double = 1.0) =
            drawCircle(cx.toDouble(), cy.toDouble(), r.toDouble(), color, style2, lineWidth)

    fun drawCircle(cx: Double, cy: Double, r: Double, color: Int, style2: Paint.Style = Paint.Style.FILL, lineWidth: Double = 1.0){
        val style = rgbaString(color)
        ctx.fillStyle = style
        ctx.strokeStyle = style
        ctx.lineWidth = lineWidth
        ctx.beginPath()
        ctx.arc(cx, cy, r, 0.0, 6.2831)
        ctx.stroke()
        when(style2){
            Paint.Style.FILL -> {
                ctx.fill()
            }
            Paint.Style.STROKE -> {
                ctx.stroke()
            }
            Paint.Style.FILL_AND_STROKE -> {
                ctx.fill()
                ctx.stroke()
            }
        }
    }

    fun drawRoundRect(rectF: RectF, rxF: Float, ryF: Float, paint: Paint) = drawRoundRect(rectF, rxF, ryF, paint.color, paint.style, paint.strokeWidth.toDouble())
    fun drawRoundRect(rectF: RectF, rxF: Float, ryF: Float, color: Int, style2: Paint.Style = Paint.Style.FILL, lineWidth: Double = 1.0){
        val style = rgbaString(color)
        ctx.fillStyle = style
        ctx.strokeStyle = style
        ctx.lineWidth = lineWidth
        val x = rectF.left.toDouble()
        val width = rectF.width().toDouble()
        val rx = rxF.toDouble()
        val ry = ryF.toDouble()
        val y = rectF.top.toDouble()
        val height = rectF.height().toDouble()
        ctx.beginPath()
        ctx.moveTo(x + rx, y)
        ctx.lineTo(x + width - rx, y)
        ctx.quadraticCurveTo(x + width, y, x + width, y + ry)
        ctx.lineTo(x + width, y + height - ry)
        ctx.quadraticCurveTo(x + width, y + height, x + width - rx, y + height)
        ctx.lineTo(x + rx, y + height)
        ctx.quadraticCurveTo(x, y + height, x, y + height - ry)
        ctx.lineTo(x, y + ry)
        ctx.quadraticCurveTo(x, y, x + rx, y)
        ctx.closePath()
        when(style2){
            Paint.Style.FILL -> {
                ctx.fill()
            }
            Paint.Style.STROKE -> {
                ctx.stroke()
            }
            Paint.Style.FILL_AND_STROKE -> {
                ctx.fill()
                ctx.stroke()
            }
        }
    }

    fun scale(sx: Float, sy: Float, dx: Float, dy: Float){
        translate(dx, dy)
        ctx.scale(sx.toDouble(), sy.toDouble())
        translate(-dx, -dy)
    }

    fun save(): Int {
        ctx.save()
        return ctr++
    }

    fun restoreToCount(count: Int){
        while(ctr > count){
            ctr--
            ctx.restore()
        }
    }

    fun setBounds(left: Int, top: Int, right: Int, bottom: Int){
        setBounds(left.toDouble(), top.toDouble(), right.toDouble(), bottom.toDouble())
    }

    fun setBounds(left: Double, top: Double, right: Double, bottom: Double){
        // println("bounds $ctr $left, $top, $right, $bottom")
        // lineRect(left, top, right, bottom, -1)
        ctx.beginPath()
        ctx.moveTo(left, top)
        ctx.lineTo(right, top)
        ctx.lineTo(right, bottom)
        ctx.lineTo(left, bottom)
        ctx.closePath()
        ctx.clip()
    }

    fun translate(dx: Int, dy: Int){
        if(dx != 0 || dy != 0){
            ctx.translate(dx.toDouble(), dy.toDouble())
        }
    }

    fun translate(dx: Float, dy: Float){
        ctx.translate(dx.toDouble(), dy.toDouble())
    }

    fun rotate(angle: Float, dx: Float, dy: Float){
        translate(dx, dy)
        ctx.rotate((angle.toDouble()) / 180 * PI)
        translate(-dx, -dy)
    }

    companion object {

        fun fontString(paint: Paint): String {
            return "${if(paint.isBold) "bold " else ""}${paint.textSize}px Verdana"
        }

        fun rgbaString(color: Int): String {
            return "rgba(${color.shr(16).and(255)}, ${color.shr(8).and(255)}, ${color.and(255)}, ${color.shr(24).and(255)/255f})"
        }

    }

}