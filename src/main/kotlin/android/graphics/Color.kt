package android.graphics

import androidx.core.math.MathUtils.clamp
import me.antonio.noack.elementalcommunity.utils.Maths.fract
import kotlin.math.abs
import kotlin.math.floor

object Color {

    fun HSVToColor(input: FloatArray): Int {
        val H = fract(input[0]/360)*6
        val S = input[1]
        val V = input[2]
        val C = V * S
        val X = C * (1 - abs(H.rem(2)-1))
        val m = V - C
        var r = 0f
        var g = 0f
        var b = 0f
        when {
            H < 1 -> {
                r = C
                g = X
            }
            H < 2 -> {
                r = X
                g = C
            }
            H < 3 -> {
                g = C
                b = X
            }
            H < 4 -> {
                g = X
                b = C
            }
            H < 5 -> {
                r = X
                b = C
            }
            else -> {
                r = C
                b = X
            }
        }
        return rgb1(r+m, g+m, b+m)
    }

    fun rgb1(r: Float, g: Float, b: Float) = rgb255((r*255).toInt(), (g*255).toInt(), (b*255).toInt())
    fun rgb255(r: Int, g: Int, b: Int) = 0xff000000.toInt() or clamp(r, 0, 255).shl(16) or clamp(g, 0, 255).shl(8) or clamp(b,0,255)

}