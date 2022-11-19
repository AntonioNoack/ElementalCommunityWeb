package java.math

external class bigInt(str: String){

    fun pow(i: Int): BigInteger
    fun modPow(x: BigInteger, y: BigInteger): BigInteger
    fun minus(x: BigInteger): BigInteger
    fun and(x: BigInteger): BigInteger
    fun toLong(): String

}
