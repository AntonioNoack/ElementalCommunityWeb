package java.util

import me.antonio.noack.elementalcommunity.utils.Maths.fract
import me.antonio.noack.webdroid.Runner.now
import kotlin.math.sin

class Random(var seed: Long = now().times(541513213L).toLong()){

    fun setSeed(seed: Long){
        this.seed = seed
    }

    fun nextFloat(): Float {
        val nextSeed = seed xor (seed * 13215617L)
        val value = fract(sin(seed.toInt().toFloat()) * 1516313)
        seed = nextSeed
        return value
    }

    fun nextDouble(): Double {
        val nextSeed = seed xor (seed * 13215617L)
        val value = fract(sin(seed.toInt().toDouble()) * 1516313)
        seed = nextSeed
        return value
    }

    fun nextLong(): Long {
        val nextSeed = seed xor (seed * 13215617L)
        val value = seed xor (47544181 * seed)
        seed = nextSeed
        return value
    }
}