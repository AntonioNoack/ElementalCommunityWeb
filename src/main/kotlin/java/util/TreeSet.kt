package java.util

class TreeSet<V : Comparable<V>> : MutableSet<V> {

    private val list = ArrayList<V>()
    private val set = HashSet<V>()

    override val size: Int
        get() = list.size

    private var isSorted = false

    override fun add(element: V): Boolean {
        return synchronized(this) {
            if (set.add(element)) {
                list.add(element)
                isSorted = false
                true
            } else false
        }
    }

    override fun addAll(elements: Collection<V>): Boolean {
        return synchronized(this) {
            if (set.addAll(elements)) {
                list.clear()
                list.addAll(set)
                isSorted = false
                true
            } else false
        }
    }

    override fun clear() {
        synchronized(this) {
            list.clear()
            set.clear()
            isSorted = true
        }
    }

    override fun contains(element: V): Boolean = element in set

    override fun containsAll(elements: Collection<V>) =
        set.containsAll(elements)

    override fun isEmpty(): Boolean = list.isEmpty()

    override fun iterator(): MutableIterator<V> {
        synchronized(this) {
            if (!isSorted) {
                list.sort()
                isSorted = true
            }
        }
        return list.iterator()
    }

    override fun remove(element: V): Boolean {
        return synchronized(this) {
            if (set.remove(element)) {
                list.remove(element)
                true
            } else false
        }
    }

    override fun removeAll(elements: Collection<V>): Boolean {
        val s = elements.toSet()
        return synchronized(this) {
            if (set.removeAll(s)) {
                list.removeAll(s)
                true
            } else false
        }
    }

    override fun retainAll(elements: Collection<V>): Boolean {
        val s = elements.toSet()
        return if (set.retainAll(s)) {
            list.retainAll(s)
            true
        } else false
    }

    fun filter(predicate: (V) -> Boolean): TreeSet<V> {
        synchronized(this) {
            val filtered = TreeSet<V>()
            for (element in list) {
                if (predicate(element)) {
                    filtered.add(element)
                }
            }
            return filtered
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other === this)
            return true
        if (other !is Set<*> || other.size != size)
            return false
        return try {
            containsAll(other)
        } catch (unused: ClassCastException) {
            false
        } catch (unused: NullPointerException) {
            false
        }
    }

}