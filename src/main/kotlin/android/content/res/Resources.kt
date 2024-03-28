package android.content.res

import android.util.DisplayMetrics

object Resources {

    fun getColor(int: Int, theme: Int) = int
    fun getColor(int: Int) = int
    fun getColor(long: Long) = long.toInt()

    fun getString(str: String) = str

    val displayMetrics = DisplayMetrics()

}