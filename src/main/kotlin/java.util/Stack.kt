package java.util

class Stack<V>: Iterable<V> {

    val internal = ArrayList<V>()

    fun size(): Int = internal.size

    fun peek(): V? {
        return internal.getOrNull(internal.lastIndex)
    }

    fun pop(): V {
        return internal.removeAt(internal.lastIndex)
    }

    fun push(v: V) = internal.add(v)

    fun remove(v: V) = internal.remove(v)

    override fun iterator(): Iterator<V> = internal.iterator()

}