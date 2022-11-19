package android.content.res

import android.util.DisplayMetrics

class Resources {

    fun getColor(int: Int) = int
    fun getColor(long: Long) = long.toInt()

    fun getString(str: String) = str

    val displayMetrics = DisplayMetrics()

}