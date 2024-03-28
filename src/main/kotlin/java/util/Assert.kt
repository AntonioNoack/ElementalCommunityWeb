package java.util

fun assert(x: Boolean, text: String = "Assertion failed!"){
    if(!x) throw RuntimeException(text)
}