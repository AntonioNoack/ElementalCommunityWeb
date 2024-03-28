package androidx.core.math

object MathUtils {
    fun clamp(x: Float, min: Float, max: Float) = if(x < min) min else if(x < max) x else max
    fun clamp(x: Int, min: Int, max: Int) = if(x < min) min else if(x < max) x else max
    fun clamp(x: Long, min: Long, max: Long) = if(x < min) min else if(x < max) x else max
    fun clamp(x: Double, min: Double, max: Double) = if(x < min) min else if(x < max) x else max
}