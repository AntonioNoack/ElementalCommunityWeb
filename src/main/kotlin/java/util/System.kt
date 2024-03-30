package java.util

import kotlin.js.Date

object System {
    // Date().getTime() is in millis since 1970
    fun nanoTime(): Long = (Date().getTime() * 1e6).toLong()
    fun currentTimeMillis(): Long = Date().getTime().toLong()
}