package java.util.concurrent.atomic

class AtomicInteger(var value: Int = 0) {

    fun incrementAndGet(): Int {
        return ++value
    }

    fun get() = value

}