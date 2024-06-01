package me.antonio.noack.elementalcommunity

class ConcurrentHashSet<V>(capacity: Int) : HashMap<V, Unit>(capacity) {

    fun put(element: V) {
        this[element] = Unit
    }

    fun addAll(elements: Collection<V>) {
        for (e in elements) {
            put(e!!, Unit)
        }
    }

}