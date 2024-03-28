package java.lang

import androidx.core.math.MathUtils.clamp
import kotlin.math.exp
import kotlin.math.ln

object StrictMath {

    fun pow(base: Float, exponent: Float) = exp(ln(base) * exponent)
    fun pow(base: Double, exponent: Double) = exp(ln(base) * exponent)
    fun colorWithOpacity(color: Int, opacity: Float): Int {
        return color.and(0xffffff) or clamp((color.shr(24).and(255) * opacity).toInt(), 0, 255).shl(24)
    }

}