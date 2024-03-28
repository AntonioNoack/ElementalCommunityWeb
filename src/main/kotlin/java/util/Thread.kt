package java.util

import me.antonio.noack.webdroid.setTimeout

fun thread(start: Boolean, func: () -> Unit){
    setTimeout(func, 0)
}

fun thread(func: () -> Unit){
    setTimeout(func, 0)
}
