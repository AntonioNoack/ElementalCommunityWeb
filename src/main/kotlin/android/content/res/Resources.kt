package android.content.res

import android.util.DisplayMetrics

object Resources {
    fun getColor(int: Int) = int
    fun getString(str: String) = str
    val displayMetrics = DisplayMetrics()
}