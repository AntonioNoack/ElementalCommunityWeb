package java.net

// wtf, why do I have to do that?
external fun encodeURIComponent(str: String): String

object URLEncoder {

    fun encode(token: String, charset: String) = encodeURIComponent(token)

}

fun URL(str: String) = str