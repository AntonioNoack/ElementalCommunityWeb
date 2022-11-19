package android.text

open class Editable(var internal: String): CharSequence {

    override val length: Int
        get() = internal.length

    override fun get(index: Int): Char = internal[index]

    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence = internal.subSequence(startIndex, endIndex)

    override fun toString(): String = internal

}