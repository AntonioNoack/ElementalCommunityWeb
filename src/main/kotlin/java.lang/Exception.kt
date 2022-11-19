package java.lang

open class Exception(msg: String? = null): RuntimeException(msg){
    fun printStackTrace(){
        console.log(message)
    }
}