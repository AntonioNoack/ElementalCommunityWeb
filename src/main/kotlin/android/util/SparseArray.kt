package android.util

class SparseArray<V>: MutableMap<Int, V> {

    val map = HashMap<Int, V>()

    override val entries: MutableSet<MutableMap.MutableEntry<Int, V>>
        get() = map.entries

    override val keys: MutableSet<Int>
        get() = map.keys

    override val size: Int
        get() = map.size

    override val values: MutableCollection<V>
        get() = map.values

    override fun clear() {
        map.clear()
    }

    override fun containsKey(key: Int): Boolean = map.containsKey(key)

    override fun containsValue(value: V): Boolean = map.containsValue(value)

    override fun get(key: Int): V? = map[key]

    override fun isEmpty(): Boolean = map.isEmpty()

    override fun put(key: Int, value: V): V? {
        return map.put(key, value)
    }

    override fun putAll(from: Map<out Int, V>) = map.putAll(from)

    override fun remove(key: Int): V? = map.remove(key)

    override fun toString(): String {
        return map.entries.toString()
    }

}
// typealias SparseArray<V> = HashMap<Int, V>
typealias LongSparseLongArray = HashMap<Long, Long>