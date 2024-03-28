package java.io

open class Reader(val str: String){

    var i = 0
    fun read(): Int {
        return if(i < str.length) str[i++].toInt()
        else -1
    }

}